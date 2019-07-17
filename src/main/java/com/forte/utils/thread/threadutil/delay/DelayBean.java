package com.forte.utils.thread.threadutil.delay;


import com.forte.utils.thread.threadutil.inter.delay.DelayFunc;

import java.lang.reflect.Method;

/**
 * timeBean抽象类
 * @param <R>
 * @author ForteScarlet
 */
public abstract class DelayBean<R> implements Runnable,java.io.Serializable {

	/** 终止线程 */
	protected boolean stop = true;

	/* —— 参数 —— */

	protected Class<R> rClass;
	protected R object;
	protected String method;
	protected Object[] args;
	protected long m;
	protected Method me;
	protected DelayFunc fd;



	/**
	 *
	 * 构造方法
	 *
	 * @param clazz
	 * @param method
	 * @param m
	 * @param args
	 */
	DelayBean(Class<R> clazz, String method, long m, Object... args) {
		super();
		this.rClass = clazz;
		this.method = method;
		this.args = args;
		this.m = m;
	}

	/**
	 *
	 * 构造方法
	 *
	 * @param fd
	 * @param m
	 * @param args
	 */
	DelayBean(DelayFunc fd, long m, Object... args) {
		super();
		this.fd = fd;
		this.args = args;
		this.m = m;
	}

	/** 停止 */
	abstract void stop();

	/**
	 * 是否已经停止了
	 * @return
	 */
	public boolean isOver(){
		return !stop;
	}
	
}
