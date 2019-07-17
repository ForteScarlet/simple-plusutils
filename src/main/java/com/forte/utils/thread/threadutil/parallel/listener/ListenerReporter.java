package com.forte.utils.thread.threadutil.parallel.listener;


import com.forte.utils.thread.threadutil.parallel.Parallel;
import com.forte.utils.thread.threadutil.parallel.ParallelTask;

/**
 *
 * 监听器汇报器
 * 汇报器的方法需要与监听器中的方法同步更新
 * 为了保障方法同步，此汇报器也实现了根监听器
 * 但却不用来作为监听器使用
 *
 * @author ForteScarlet
 */
public class ListenerReporter implements ParallelListener {

    /** 监听器数组，数组形式内存占用较小，且数量一般不会超过2个 */
    private ParallelListener[] listeners;


    /**
     * 创建汇报
     * @param bean
     */
    @Override
    public void created(Parallel bean) {
        for(ParallelListener l : listeners){
            l.created(bean);
        }
    }

    /**
     * 启动汇报
     * @param bean
     */
    @Override
    public void start(Parallel bean) {
        for(ParallelListener l : listeners){
            l.start(bean);
        }
    }


    /**
     * 任务开始汇报
     * @param task
     */
    @Override
    public void taskStart(ParallelTask task) {
        for(ParallelListener l : listeners){
            l.taskStart(task);
        }
    }

    /**
     * 任务结束汇报
     * @param task
     */
    @Override
    public void taskOver(ParallelTask task) {
        for(ParallelListener l : listeners){
            l.taskOver(task);
        }
    }

    /**
     * 任务异常汇报
     * @param task
     * @param e
     */
    @Override
    public void taskException(ParallelTask task, Exception e) {
        for(ParallelListener l : listeners){
            l.taskException(task , e);
        }
    }

    /**
     * 结束汇报
     * @param bean
     */
    @Override
    public void over(Parallel bean) {
        for(ParallelListener l : listeners){
            l.over(bean);
        }
    }

    /**
     * 异常汇报
     * @param bean
     * @param e
     */
    @Override
    public void exception(Parallel bean, Exception e) {
        for(ParallelListener l : listeners){
            l.exception(bean , e);
        }
    }

    /**
     * 重置汇报
     * @param bean
     */
    @Override
    public void restore(Parallel bean) {
        for(ParallelListener l : listeners){
            l.restore(bean);
        }
    }


    /**
     * 构造方法
     * @param listeners
     */
    public ListenerReporter(ParallelListener... listeners){
        this.listeners = listeners;
    }

}
