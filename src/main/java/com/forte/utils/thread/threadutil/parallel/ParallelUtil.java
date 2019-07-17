package com.forte.utils.thread.threadutil.parallel;


import com.forte.utils.thread.BaseLocalThreadPool;
import com.forte.utils.thread.threadutil.inter.parallel.ParallelTaskFunc;
import com.forte.utils.thread.threadutil.parallel.listener.DefaultParallelListener;
import com.forte.utils.thread.threadutil.parallel.listener.ParallelGlobalListener;
import com.forte.utils.thread.threadutil.parallel.listener.ParallelSingleListener;

import java.util.Arrays;


/**
 * 提供方法创建并行任务<br>
 *
 * @author ForteScarlet
 */
public class ParallelUtil extends BaseLocalThreadPool {

    /** 定时任务全局监听器，如果用户没有进行设置的话则使用默认监听器 */
    static ParallelGlobalListener listener = new DefaultParallelListener();


    /**
     * 创建一个同步任务
     * @param taskName
     * 任务名称
     * @param singleListener
     * 任务单独的监听器
     * @param fs
     * 任务函数列表
     * @return
     */
    public static Parallel parallel(String taskName , ParallelSingleListener singleListener , ParallelTaskFunc[] fs){
        return new BaseParallel(taskName , singleListener , fs);
    }

    /**
     * 创建一个同步任务
     * @param taskName
     * 任务名称
     * @param fs
     * 任务函数列表
     * @return
     */
    public static Parallel parallel(String taskName , ParallelTaskFunc[] fs){
        return new BaseParallel(taskName , fs);
    }

    /**
     * 创建一个同步任务
     * @param singleListener
     * 此任务单独的监听器
     * @param fs
     * 任务函数列表
     * @return
     */
    public static Parallel parallel(ParallelSingleListener singleListener , ParallelTaskFunc[] fs){
        return new BaseParallel(singleListener , fs);
    }

    /**
     * 创建一个同步任务
     * @param fs
     * 任务函数列表
     * @return
     */
    public static Parallel parallel(ParallelTaskFunc[] fs){
        return new BaseParallel(fs);
    }


    /**
     * 获取一个多个同步任务同时进行的同步任务
     * @param taskName
     * 任务名称
     * @param singleListener
     * 同步任务单独监听器
     * @param parallels
     * 同步任务列表
     * @return
     */
    public static Parallel parallelList(String taskName , ParallelSingleListener singleListener , Parallel... parallels){
            //返回结果
            return parallel(taskName ,
                            singleListener ,
                            //讲parallels转化为ParallelTaskFunc数组
                            Arrays.stream(parallels).map(p -> (ParallelTaskFunc) p::start).toArray(ParallelTaskFunc[]::new)
                     );

    }

    /**
     * 获取一个多个同步任务同时进行的同步任务
     * @param singleListener
     * 同步任务单独监听器
     * @param parallels
     * 同步任务列表
     * @return
     */
    public static Parallel parallelList(ParallelSingleListener singleListener , Parallel... parallels){
        return parallelList(null , singleListener , parallels);
    }

    /**
     * 获取一个多个同步任务同时进行的同步任务
     * @param taskName
     * 任务名称
     * @param parallels
     * 同步任务列表
     * @return
     */
    public static Parallel parallelList(String taskName , Parallel... parallels){
        return parallelList(taskName , null , parallels);
    }

    /**
     * 获取一个多个同步任务同时进行的同步任务
     * @param parallels
     * 同步任务列表
     * @return
     */
    public static Parallel parallelList(Parallel... parallels){
        return parallelList(null , null , parallels);
    }




    /**
     * 设置全局监听器
     * @param listener
     * 全局监听器的实现对象
     */
    public static void setGlobalListener(ParallelGlobalListener listener){
        ParallelUtil.listener = listener;
    }

    /**
     * 设置全局监听器
     * @param listenerClass
     * 全局监听器的class对象
     */
    public static void setGlobalListener(Class<? extends ParallelGlobalListener> listenerClass){
        //创建实例
        ParallelGlobalListener listener = null;
        try {
            listener = listenerClass.newInstance();
            ParallelUtil.listener = listener;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }



}
