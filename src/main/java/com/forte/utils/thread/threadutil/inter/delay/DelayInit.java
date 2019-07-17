package com.forte.utils.thread.threadutil.inter.delay;

/**
 *
 * 在实现{@link DelayFunc}的同时实现此接口，定义初始化时的方法（开始循环之前）
 *
 * @author ForteScarlet
 */
@FunctionalInterface
public interface DelayInit {

    /** 初始方法 */
    void init();

}
