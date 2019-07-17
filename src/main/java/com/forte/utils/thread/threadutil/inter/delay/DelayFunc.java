package com.forte.utils.thread.threadutil.inter.delay;


/**
 * Detention : 延时
 *
 * @author ForteScarlet 执行延时方法的函数接口，在需要执行一些不需要特定对象的时候用到。
 */
@FunctionalInterface
public interface DelayFunc {

	/**
	 * 延时函数的方法，函数接口中的唯一函数，代表了需要延时执行的方法
	 * @param args
	 */
	void delay(Object... args);
}
