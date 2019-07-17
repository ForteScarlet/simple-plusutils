package com.forte.utils.thread.threadutil.parallel.listener;

import com.forte.utils.thread.threadutil.parallel.Parallel;
import com.forte.utils.thread.threadutil.parallel.ParallelTask;

/**
 * parallel各个监听器的根接口
 *
 * @author ForteScarlet
 */
public interface ParallelListener {

    /**有并行任务被创建了*/
    void created(Parallel bean);

    /**有并行任务被启动了*/
    void start(Parallel bean);

    /** 有任务被执行了 */
    void taskStart(ParallelTask task);

    /** 有任务结束了 */
    void taskOver(ParallelTask task);

    /** 有异常出现了异常 */
    void taskException(ParallelTask task, Exception e);

    /**有并行任务结束了*/
    void over(Parallel bean);

    /** 有并行任务异常了 */
    void exception(Parallel bean, Exception e);

    /** 有并行任务重置了 */
    void restore(Parallel bean);

}
