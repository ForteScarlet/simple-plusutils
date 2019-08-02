package com.forte.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 对枚举对象的动态代理工具类
 * 可以通过类、字段、方法、方法参数4个地方来获取并构建注解对象的代理对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ProxyUtils {

    /**
     * 从某个类上获取注解的代理对象
     *
     * @param annotationType      此枚举的类型
     * @param type          从某个类身上拿到此枚举
     * @param invocationHandlerCreator 通过枚举对象和类自身来构建InvocationHandler接口对象
     */
    public static <T extends Annotation> T proxy(Class<T> annotationType, Class<?> type, BiFunction<T, Class, InvocationHandler> invocationHandlerCreator){
        T anno = Objects.requireNonNull(type.getAnnotation(annotationType));

        return getProxy(annotationType, invocationHandlerCreator.apply(anno, type));
    }


    /**
     * 从某个字段上获取注解的代理对象
     *
     * @param annotationType      此枚举的类型
     * @param field          从某个字段身上拿到此枚举
     * @param invocationHandlerCreator 通过枚举对象和类自身来构建InvocationHandler接口对象
     */
    public static <T extends Annotation> T proxy(Class<T> annotationType, Field field, BiFunction<T, Field, InvocationHandler> invocationHandlerCreator){
        T anno = Objects.requireNonNull(field.getAnnotation(annotationType));

        return getProxy(annotationType, invocationHandlerCreator.apply(anno, field));
    }

    /**
     * 从某个字段上获取注解的代理对象
     *
     * @param annotationType      此枚举的类型
     * @param method          从某个方法身上拿到此枚举
     * @param invocationHandlerCreator 通过枚举对象和类自身来构建InvocationHandler接口对象
     */
    public static <T extends Annotation> T proxy(Class<T> annotationType, Method method, BiFunction<T, Method, InvocationHandler> invocationHandlerCreator){
        T anno = Objects.requireNonNull(method.getAnnotation(annotationType));

        return getProxy(annotationType, invocationHandlerCreator.apply(anno, method));
    }

    /**
     * 从某个字段上获取注解的代理对象
     *
     * @param annotationType      此枚举的类型
     * @param parameter          从某个方法参数身上拿到此枚举
     * @param invocationHandlerCreator 通过枚举对象和类自身来构建InvocationHandler接口对象
     */
    public static <T extends Annotation> T proxy(Class<T> annotationType, Parameter parameter, BiFunction<T, Parameter, InvocationHandler> invocationHandlerCreator){
        T anno = Objects.requireNonNull(parameter.getAnnotation(annotationType));

        return getProxy(annotationType, invocationHandlerCreator.apply(anno, parameter));
    }


    /**
     * 为一个接口生成代理对象
     */
    public static <T> T proxy(Class<T> interfaceType, ExProxyHandler<Method, Object[], Object> proxyHandler){
        return getProxy(interfaceType, (p, m, o) -> proxyHandler.apply(m, o));
    }


    /**
     * 获取动态代理对象
     * //TODO
     */
    private static <B extends T, T> T getProxy(Class<B> type, InvocationHandler handler){
        return (T) Proxy.newProxyInstance(type.getClassLoader(), type.getInterfaces(), handler);
    }


    /**
     *  带着异常处理的BiFunction，用于构建动态代理的参数
     */
    @FunctionalInterface
    public interface ExProxyHandler<T, U, R> {
        /**
         * 函数接口
         * @param t 第一参数
         * @param u 第二参数
         * @return  返回值
         * @throws Throwable 任意异常
         */
        R apply(T t, U u) throws Throwable;
    }

}
