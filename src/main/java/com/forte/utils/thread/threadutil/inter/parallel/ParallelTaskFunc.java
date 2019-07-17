package com.forte.utils.thread.threadutil.inter.parallel;

/**
 * 并行任务的函数接口
 *
 * @author ForteScarlet
 */
@FunctionalInterface
public interface ParallelTaskFunc {

    /** 需要并行的任务 */
    public void parallel();

}
