package com.forte.utils.thread.threadutil.parallel;


import com.forte.utils.thread.BaseLocalThreadPool;
import com.forte.utils.thread.threadutil.Exception.NoThreadForParallelException;
import com.forte.utils.thread.threadutil.Exception.ParallelHadStartedException;
import com.forte.utils.thread.threadutil.inter.parallel.ParallelTaskFunc;
import com.forte.utils.thread.threadutil.parallel.listener.ListenerReporter;
import com.forte.utils.thread.threadutil.parallel.listener.ParallelGlobalListener;
import com.forte.utils.thread.threadutil.parallel.listener.ParallelSingleListener;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 并行任务的抽象执行者<br>
 * 并行任务需要的传入参数：('[]'中的参数为可有可无的)<br>
 *     <ul>
 *         <li>[任务名称( 如果不填写则会自动补充 - 自增形式 )]</li>
 *         <li>{@link ParallelTaskFunc}对象若干，作为任务列表</li>
 *         <li>[此任务的独立监听器，即{@link ParallelSingleListener}的实现类]</li>
 *     </ul>
 *
 * 除此之外还需要的内部参数：<br>
 *     <ul>
 *         <li>线程池标记自增数</li>
 *         <li>全局监听器</li>
 *         <li>线程池及线程池名称</li>
 *         <li>并行计数器->根据任务数量决定计数数量</li>
 *         <li>任务列表开始执行标记</li>
 *         <li>任务列表执行结束标记</li>
 *     </ul>
 *
 *
 *
 * @author ForteScarlet
 */
public abstract class Parallel {

    /**———— 任务名称计数-自增用 ————*/
    /**
     * AtomicInteger是一个线程安全的int
     */
    private static AtomicInteger count = new AtomicInteger(0);


    /** 全局监听器 */
    private ParallelGlobalListener listener = ParallelUtil.listener;

    /**
     * 线程池的名称
     */
    private final String poolName = "parallel-task-pool";

    /**
     * 获取线程池
     */
    private final Executor threadPool = BaseLocalThreadPool.getThreadPool(poolName);

    /*————参数 全部不可变更————*/
    /** 任务名称 */
    private final String parallelName;

    /** 需要进行并行执行的任务数组 */
    private final ParallelTask[] fs;
    /** 此任务的单独监听器 */
    private final ParallelSingleListener singleListener;
    /** 并行计数器 */
    private final CountDownLatch countDownLatch;

    /** 监听器汇报器 */
    protected final ListenerReporter reporter;

    /** 开始过了 */
    private boolean start = false;

    /** 开始的时间毫秒值，如果没开始则返回0 */
    private long startTime = 0;

    /** 已经结束了 */
    private boolean over = false;

    /** 结束的时间毫秒值，如果尚未结束则返回0 */
    private long overTime = 0;

    /**
     * 开始执行 - 抽象方法 - 不对外开放
     * 内部只需要写明：
     * 所有线程的执行逻辑
     */
    abstract void run();

    /**
     * 为实现类提供的执行任务的方法
     */
    protected void run(ParallelTask task){
        getThreadPool().execute(task);
    }

    /**
     * 对外开放的方法
     */
    public Parallel start(){
        //判断是否已经开始执行或已经执行结束
        if(start){
            //如果已经开始过了，抛出异常
            throw new ParallelHadStartedException();
        }
        //变更状态
        start = true;

        try{
            //遍历并行任务对象
            reporter.start(this);//向监听器汇报
            //计时
            startTime = System.currentTimeMillis();

            //全部执行
            run();
            //等待线程全部执行完成
            countDownLatch.await();

            //计时
            overTime = System.currentTimeMillis();

            //执行完成
            reporter.over(this);
        }catch (Exception e){
            reporter.exception(this , e);//向监听器汇报异常
            //异常后，清空计数器
            while(countDownLatch.getCount() > 0){
                countDownLatch.countDown();
            }
        }finally {
            //变更状态
            over = true;
            //返回自身
            return this;
        }

    }

    /**
     * 恢复为可执行状态
     * @return
     */
    public Parallel restore(){
        this.start = false;
        this.over  = false;
        this.startTime = 0;
        this.overTime  = 0;
        return this;
    }



    /**
     * 构造方法 - 全部参数
     */
    public Parallel(String parallelName , ParallelSingleListener singleListener , ParallelTaskFunc[] fs){
        //如果没有并行任务线程，抛出一个异常
        if(fs.length <= 0) throw new NoThreadForParallelException();

        // 参数赋值
        this.parallelName = parallelName == null?
                "[parallel]-task_group-" + count.getAndAdd(1)
                :
                parallelName;

        //本类的独立监听器
        this.singleListener = singleListener;

        //创建并行计数器
        countDownLatch = new CountDownLatch(fs.length);

        //创建监听器汇报器
        reporter = singleListener == null ?
                new ListenerReporter(listener)
                :
                new ListenerReporter(listener , singleListener);

        //创建任务对象数组
        this.fs = Arrays.stream(fs).map(f -> new ParallelTask(countDownLatch , f , reporter , this)).toArray(ParallelTask[]::new);

        //向监听器汇报
        reporter.created(this);
    }




    /**
     * 没有名称
     */
    public Parallel(ParallelSingleListener singleListener , ParallelTaskFunc[] fs){
        this(null , singleListener , fs);
     }

    /**
     * 没有单独监听器
     */
    public Parallel(String parallelName , ParallelTaskFunc[] fs){
        this(parallelName , null , fs);
    }

    /**
     * 没有名称与单独监听器
     */
    public Parallel(ParallelTaskFunc[] fs){
       this(null , null , fs);
    }





    /* ——————————————————getter—————————————————————— */

    public String getParallelName() {
        return parallelName;
    }

    public ParallelTask[] getParallelTask() {
        return fs;
    }

    protected Executor getThreadPool(){
        return threadPool;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isOver() {
        return over;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getOverTime() {
        return overTime;
    }
}
