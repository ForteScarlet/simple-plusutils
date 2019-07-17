package com.forte.utils.thread.threadutil.delay;




import com.forte.utils.thread.threadutil.inter.delay.DelayDestory;
import com.forte.utils.thread.threadutil.inter.delay.DelayFunc;
import com.forte.utils.thread.threadutil.inter.delay.DelayInit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 方法循环类
 * 
 * @author ForteScarlet
 *
 */
public class Interval<R> extends DelayBean<R> {

	/* —— 参数 - 参数存在于父抽象类 —— */

	/**
	 * 线程 - 方法执行
	 */
	@Override
	public void run() {
		// 判断是否有可调用函数
		if (fd == null) {
			Class<R> clazz = rClass;

			try {
				//创建实例
				object = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			// 循环
			while (stop) {
				// 线程休眠
				try {
					Thread.sleep(m);
				} catch (InterruptedException e) {
				}
				// 第一次执行，寻找此方法
				if (me == null) {
					List<Method> methods = Stream.of(clazz.getMethods())
							.filter(m -> (m.getName().equals(method) && m.getParameters().length == args.length))
							.collect(Collectors.toList());


					// 若没有找到此方法
					if (methods.isEmpty()) {
						System.err.println("方法 " + method + "未找到");
						return;
					}
					boolean b = true;
					// 获取对应的方法
					for (Method m : methods) {
						try {
							m.invoke(object, args);
							b = false;
							me = m;
							break;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							continue;
						}
					}

					// 若没有找到匹配方法
					if (b) {
						System.err.println("方法 " + method + "未找到、方法与参数不匹配或方法执行出现异常！");
						return;
					}

				} else {
					// 不是第一次执行，直接调用
					try {
						me.invoke(object, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						System.err.println("方法 " + this.method + "执行出现异常！");
						e.printStackTrace();
					}
				}
			}
		} else {
			//如果有自定义初始化方法
			if(fd instanceof DelayInit){
				//执行自定义初始化方法
				((DelayInit)fd).init();
			}

			// 循环
			while (stop) {
				// 线程休眠
				try {
					Thread.sleep(m);
				} catch (InterruptedException e) {
				}
				// 方法执行
				fd.delay(args);
			}


			//如果有自定义销毁方法
			if(fd instanceof DelayDestory){
				//执行自定义销毁方法
				((DelayDestory)fd).destroy();
			}

		}
	}

	/**
	 * 停止循环,代表停止执行
	 */
	@Override
	void stop() {
		stop = false;
	}



	/**
	 *
	 * 构造方法
	 *
	 * @param clazz
	 * @param method
	 * @param m
	 * @param args
	 */
	Interval(Class<R> clazz, String method, long m, Object... args) {
		super(clazz , method , m , args);
	}

	/**
	 *
	 * 构造方法
	 *
	 * @param fd
	 * @param m
	 * @param args
	 */
	Interval(DelayFunc fd, long m, Object... args) {
		super(fd , m , args);
	}

}
