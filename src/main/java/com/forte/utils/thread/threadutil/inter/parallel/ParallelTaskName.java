package com.forte.utils.thread.threadutil.inter.parallel;

/**
 *
 * 一个用处比较小的接口，用来定义一个并行任务中一个任务的名称
 * 请将此接口实现在{@link ParallelTaskFunc}的实现类上
 *
 * @author ForteScarlet
 */
@FunctionalInterface
public interface ParallelTaskName {

    String getName(String parallelName);

}
