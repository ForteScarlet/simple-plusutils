package com.forte.utils.thread.threadutil.Exception;

/**
 *
 * 一般情况下，一个平行任务只能启动一次
 *
 * @author ForteScarlet
 */
public class ParallelHadStartedException extends RuntimeException{

    public ParallelHadStartedException(){
        super("平行任务每次只能执行一次！");
    }

}
