package com.forte.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

        return getInterfaceProxy(annotationType, invocationHandlerCreator.apply(anno, type));
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

        return getInterfaceProxy(annotationType, invocationHandlerCreator.apply(anno, field));
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

        return getInterfaceProxy(annotationType, invocationHandlerCreator.apply(anno, method));
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

        return getInterfaceProxy(annotationType, invocationHandlerCreator.apply(anno, parameter));
    }


    /**
     * 为一个接口生成代理对象
     */
    public static <T> T proxy(Class<T> interfaceType, ExProxyHandler<Method, Object[], Object> proxyHandler){
        return getInterfaceProxy(interfaceType, (p, m, o) -> proxyHandler.apply(m, o));
    }

    /**
     * 为一个注解类型生成代理对象
     * @param annotationType    注解类型
     * @param proxyHandler      函数，接收方法、参数，返回方法的执行返回值
     */
    public static <T extends Annotation> T annotationProxy(Class<T> annotationType, ExProxyHandler<Method, Object[], Object> proxyHandler){
        return proxy(annotationType, proxyHandler);
    }

    /**
     * 获取一个额外默认值Map
     * @return
     */
    public static Map<String, BiFunction<Method, Object[], Object>> getProxyDefaultReturnMap(){
        return new HashMap<>(8);
    }

    /**
     * 默认返回注解的默认值，如果没用默认值则查询提供的默认值Map，如果没有则抛出异常
     * @param annotationType    注解类型
     * @param defaultReturn     额外的默认值映射，key为方法名，value为函数，接收方法、参数，返回一个执行值
     */
    public static <T extends Annotation> T annotationProxyByDefault(Class<T> annotationType, Map<String, BiFunction<Method, Object[], Object>> defaultReturn){
        return annotationProxy(annotationType, (m, p) -> {
            Object defaultValue = m.getDefaultValue();
            if(defaultValue == null){
                defaultValue = defaultReturn.getOrDefault(m.getName(), (dm, dp) -> null).apply(m, p);
                if(defaultValue == null){
                    //如果还是null，抛出异常
                    throw new IllegalArgumentException("注解'@" + annotationType.getSimpleName() + "' 参数 '" + m.getName() + "()' 不存在默认值。");
                }
            }
            return defaultValue;
        });
    }

    /**
     * 默认返回注解的默认值，如果没用默认值则查询提供的默认值Map，如果没有则抛出异常
     * @param annotationType    注解类型
     */
    public static <T extends Annotation> T annotationProxyByDefault(Class<T> annotationType){
        return annotationProxyByDefault(annotationType, Collections.emptyMap());
    }



        /**
         * 获取动态代理对象
         * TODO 存在缺陷
         */
    private static <B extends T, T> T getInterfaceProxy(Class<B> type, InvocationHandler handler){
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, handler);
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
