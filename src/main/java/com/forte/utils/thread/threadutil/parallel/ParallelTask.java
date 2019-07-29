package com.forte.utils.thread.threadutil.parallel;


import com.forte.utils.thread.threadutil.inter.parallel.ParallelTaskFunc;
import com.forte.utils.thread.threadutil.inter.parallel.ParallelTaskName;
import com.forte.utils.thread.threadutil.parallel.listener.ListenerReporter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 单个并行任务的单个线程
 *
 * @author ForteScarlet
 */
public class ParallelTask implements Runnable {

    /** 当前线程中的自增数字，用于为取名计数 */
    private static ThreadLocal<AtomicInteger> nameCount = new ThreadLocal<>();

    /** 任务名称 */
    private String taskName;

    /** 计数器 */
    private CountDownLatch count;

    /** 需要执行的函数 */
    private ParallelTaskFunc f;

    /** 监听器汇报器 */
    private ListenerReporter reporter;

    /** 自身处在的并行任务 */
    private final Parallel parallel;

    /** 判断是否已经开始 */
    private boolean start = false;

    /** 开始时间，如果尚未开始则返回0 */
    private long sratTime = 0;

    /** 判断是否结束 */
    private boolean over = false;

    /** 结束时间，如果尚未结束则返回0 */
    private long overTime = 0;

    /**
     * 任务的执行方法
     * 线程接口{@link Runnable}的接口
     */
    @Override
    public void run() {
        try{
            //变更状态，变为启动状态
            start = true;
            //计时
            sratTime = System.currentTimeMillis();
            //向监听器汇报
            reporter.taskStart(this);

            //执行任务
            f.parallel();

            //向监听器汇报
            reporter.taskOver(this);
        }catch (Exception e){
            //向监听器汇报异常
            reporter.taskException(this , e);
        }finally {
            //变更状态为已经结束
            over = true;
            //减计数
            count.countDown();
            //计时
            overTime = System.currentTimeMillis();
        }
    }

    /**
     * 判断是否结束
     * @return
     */
    public boolean isOver(){
        return over;
    }

    /**
     * 判断是否已经开始了
     * @return
     */
    public Boolean isStart(){
        return start;
    }

    /**
     * 获取任务名称
     * @return
     */
    public String getTaskName(){
        return this.taskName;
    }

    /**
     * 移除LocalThread的值
     */
    public static void removeCount(){
        nameCount.remove();
    }



    /**
     * 构造函数
     */
    public ParallelTask(CountDownLatch count , ParallelTaskFunc f , ListenerReporter reporter , Parallel parallel){
        this.count = count;
        this.f = f;
        this.reporter = reporter;
        this.parallel = parallel;
        if(f instanceof ParallelTaskName){
            //如果有设定名称，获取名称
            this.taskName = ((ParallelTaskName) f).getName(parallel.getParallelName());
        }else{
            //如果没有名称
            if(nameCount.get() == null) nameCount.set(new AtomicInteger(1));
            this.taskName = "["+ parallel.getParallelName() +"]-task-" + nameCount.get().getAndAdd(1);


        }


    }

}
