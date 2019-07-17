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
 * 方法时间延迟类
 * 
 * @author ForteScarlet
 *
 */
public class Timeout<R> extends DelayBean<R> {

	/* —— 参数 - 参数存在于父抽象类 —— */


	/**
	 * 线程 - 方法执行
	 */
	@Override
	public void run() {
		if(fd == null){
			Class<R> clazz = rClass;

			//创建实例对象
			try {
				object = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			// 线程休眠 - 延时执行
			try {
				Thread.sleep(m);
			} catch (InterruptedException e) {
			}
			// 若被清除，结束执行
			if (!stop) {
				return;
			}
			
			List<Method> methods = Stream.of(clazz.getMethods())
					.filter(m -> (m.getName().equals(method) && m.getParameters().length == args.length))
					.collect(Collectors.toList());
			
			// 若没有找到此方法
			if (methods.isEmpty()) {
				System.err.println("方法 " + method + "未找到此方法！");
				return;
			}
			boolean b = true;
			for (Method m : methods) {
				try {
					m.invoke(object, args);
					b = false;
					break;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
			}
			
			// 若没有找到匹配方法
			if (b) {
				System.err.println("方法 " + method + "未找到、方法与参数不匹配或方法执行出现异常！");
				return;
			}
		}else{
			//如果有初始化方法
			//如果有自定义初始化方法
			if(fd instanceof DelayInit){
				//执行自定义初始化方法
				((DelayInit)fd).init();
			}

			// 线程休眠 - 延时执行
			try {
				Thread.sleep(m);
			} catch (InterruptedException e) {
			}
			// 若被清除，结束执行
			if (!stop) {
				return;
			}
			//执行方法
			fd.delay(args);

			//执行完毕后设置是否停止属性为true
			stop();

			//如果有自定义销毁方法
			if(fd instanceof DelayDestory){
				//执行自定义销毁方法
				((DelayDestory)fd).destroy();
			}

		}
	}

	/**
	 * 停止循环
	 */
	@Override
	void stop() {
		stop = false;
	}


	/**
	 * 
	 * 构造方法
	 * @param clazz
	 * @param method
	 * @param m
	 * @param args
	 */
	Timeout(Class<R> clazz, String method, long m, Object... args) {
		super(clazz , method , m , args);
	}

	/**
	 * 
	 * 构造方法
	 * @param f
	 * @param m
	 * @param args
	 */
	Timeout(DelayFunc f, long m, Object... args) {
		super(f , m , args);
	}
}
