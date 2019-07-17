package com.forte.utils.thread.threadutil.parallel.listener;


import com.forte.utils.thread.threadutil.parallel.Parallel;
import com.forte.utils.thread.threadutil.parallel.ParallelTask;

/**
 *
 * 并行任务的默认监听器
 * 所有继承方法都为空方法
 *
 * @author ForteScarlet
 */
public class DefaultParallelListener implements ParallelGlobalListener {
    @Override
    public void created(Parallel bean) {}

    @Override
    public void start(Parallel bean) {}

    @Override
    public void taskStart(ParallelTask task) {}

    @Override
    public void taskOver(ParallelTask task) {}

    @Override
    public void taskException(ParallelTask task, Exception e) {}

    @Override
    public void over(Parallel bean) {}

    @Override
    public void exception(Parallel bean, Exception e) {}

    @Override
    public void restore(Parallel bean) {}
}
