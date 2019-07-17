package com.forte.utils.thread.threadutil.delay;


import com.forte.utils.thread.BaseLocalThreadPool;
import com.forte.utils.thread.threadutil.inter.delay.DelayFunc;

import java.util.concurrent.Executor;

/**
 * 方法循环，类似js里面的 setInterval、setTimeout 和 clearInterval、clearTimeout<br>
 * <br>
 * setInterval(Object object, String method, long m, Object...args) ， return
 * ForteTimeBean<br>
 * setTimeout(Object object, String method, long m, Object...args) ， return
 * ForteTimeBean<br>
 * 这两个方法中，object为要执行方法的对象，method为方法名，m为延时的时间（毫秒值），args为此方法的参数<br>
 * <br>
 * 加入线程池<br>
 * 
 * @author ForteScarlet
 *
 */
public class DelayUtil extends BaseLocalThreadPool {

	/** 线程池 */
	private static ThreadLocal<Executor> LocalExecutor;


	/**
	 * 循环执行方法
	 * 
	 * @param clazz
	 *            执行方法的class对象
	 * @param method
	 *            要执行的方法
	 * @param m
	 *            循环间隔
	 * @param args
	 *            方法参数
	 * @return ForteTimeBean对象，用于终止
	 */
	public static <T> DelayBean<T> interval(Class<T> clazz, String method, long m, Object... args) {
		// 创建线程循环对象
		Interval<T> interval = new Interval<>(clazz, method, m, args);
		// 线程池中线程执行
		BaseLocalThreadPool.getThreadPool().execute(interval);
		// 返回此对象
		return interval;
	}

	/**
	 * 循环执行方法 - 函数接口
	 * 
	 * @param f
	 *            函数式接口，在当需要循环调用一些并非特定对象的方法的时候可以使用此函数式接口
	 * @param m
	 *            延时时长
	 * @param args
	 *            方法参数，若没有则不填
	 * @return 延时对象，用于结束延时
	 */
	public static DelayBean interval(DelayFunc f, long m, Object... args) {
		// 创建线程循环对象
		Interval interval = new Interval<>(f, m, args);
		// 线程执行
		BaseLocalThreadPool.getThreadPool().execute(interval);
		// 返回此对象
		return interval;
	}

	/**
	 * 设置方法延迟执行
	 * 
	 * @param object
	 *            执行方法的对象
	 * @param method
	 *            要执行的方法
	 * @param m
	 *            循环间隔
	 * @param args
	 *            方法参数
	 * @return ForteTimeBean对象，用于终止
	 */
	public static <T> DelayBean<T> timeout(Class<T> object, String method, long m, Object... args) {
		// 创建线程延时对象
		Timeout<T> timeout = new Timeout<T>(object, method, m, args);
		// 线程执行
		BaseLocalThreadPool.getThreadPool().execute(timeout);
		// 返回此对象
		return timeout;
	}

	/**
	 * 循环执行方法 - 函数接口
	 * 
	 * @param f
	 *            函数式接口，在当需要循环调用一些并非特定对象的方法的时候可以使用此函数式接口
	 * @param m
	 *            延时时长
	 * @param args
	 *            方法参数，若没有则不填
	 * @return 延时对象，用于结束延时
	 */
	@SafeVarargs
	public static DelayBean timeout(DelayFunc f, long m, Object... args) {
		// 创建线程延时对象
		Timeout timeout = new Timeout(f, m, args);
		// 线程执行
		BaseLocalThreadPool.getThreadPool().execute(timeout);
		// 返回此对象
		return timeout;
	}

	/**
	 * 移除延时执行
	 * 
	 * @param timeBean
	 *            ForteTimeBean对象
	 */
	public static void stop(DelayBean timeBean) {
		timeBean.stop();
	}

	/**
	 * 移除延时执行 - 延时millis后执行
	 * 
	 * @param TimeBean
	 * @param millis
	 */
	public static void stop(DelayBean TimeBean, long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TimeBean.stop();
	}

	/**
	 * 清除全部任务
	 */
	public static void clear(){
		LocalExecutor.remove();
	}


	/**
	 * 构造私有化 构造方法
	 */
	private DelayUtil() {
		super();
	}

}
