package com.forte.utils.thread.threadutil.inter.delay;

/**
 *
 * 在实现{@link DelayFunc}的同时实现此接口，定义销毁时的方法（定时/循环结束之后）
 *
 * @author ForteScarlet
 */
@FunctionalInterface
public interface DelayDestory {

    /** 销毁方法 */
    void destroy();

}
