package com.forte.utils.thread.threadutil.parallel;


import com.forte.utils.thread.threadutil.inter.parallel.ParallelTaskFunc;
import com.forte.utils.thread.threadutil.parallel.listener.ParallelSingleListener;

/**
 * 基础的并行任务执行者，实现了{@link Parallel}中的唯一抽象方法：run()<br>
 * 其他的工作如 线程计数、向监听器汇报 等全部在父抽象类中完成。
 * run()方法仅用来对任务列表进行执行，本类的run方法实现使所有任务遍历，做到同时执行<br>
 *
 * @author ForteScarlet
 */
public class BaseParallel extends Parallel {

    /**
     * 执行并行任务
     * 遍历
     */
    @Override
    void run() {
            //遍历并执行全部任务
            for(ParallelTask task : getParallelTask()){
                //遍历并执行
                run(task);
            }
    }

    /**  继承构造方法  **/

    public BaseParallel(String taskName, ParallelSingleListener singleListener, ParallelTaskFunc[] fs) {
        super(taskName, singleListener, fs);
    }

    public BaseParallel(ParallelSingleListener singleListener, ParallelTaskFunc[] fs) {
        super(singleListener, fs);
    }

    public BaseParallel(String taskName, ParallelTaskFunc[] fs) {
        super(taskName, fs);
    }

    public BaseParallel(ParallelTaskFunc[] fs) {
        super(fs);
    }
}
