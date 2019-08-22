package com.forte.utils.function;

import java.util.function.Function;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ParseTo {



    @FunctionalInterface
    interface BooleanTo<R> extends ParseTo {

        R apply(boolean b);

    }

    /**
     * @author ForteScarlet <[email]ForteScarlet@163.com>
     * @since JDK1.8
     **/
    @FunctionalInterface
    interface DoubleTo<R> extends ParseTo {

        R apply(double d);

    }

    /**
     * @author ForteScarlet <[email]ForteScarlet@163.com>
     * @since JDK1.8
     **/
    @FunctionalInterface
    interface IntTo<R> extends ParseTo {

        R apply(int t);

    }

    /**
     * @author ForteScarlet <[email]ForteScarlet@163.com>
     * @since JDK1.8
     **/
    @FunctionalInterface
    interface LongTo<R> extends ParseTo {

        R apply(long l);

    }

    /**
     * 将字符串转化为其他类型的函数
     * @author ForteScarlet <[email]ForteScarlet@163.com>
     * @since JDK1.8
     **/
    @FunctionalInterface
    interface StringTo<T> extends Function<String, T>, ParseTo {
    }


    @FunctionalInterface
    interface CharTo<R> extends ParseTo {

        R apply(char c);

    }


    @FunctionalInterface
    interface ByteTo<R> extends ParseTo {

        R apply(byte b);

    }

    //**************************************
    //*             To some
    //**************************************




    /**
     * @author ForteScarlet <[email]ForteScarlet@163.com>
     * @since JDK1.8
     **/
    @FunctionalInterface
    interface ToBoolean<R> extends ParseTo {

        boolean apply(R r);

    }

    /**
     * @author ForteScarlet <[email]ForteScarlet@163.com>
     * @since JDK1.8
     **/
    @FunctionalInterface
    interface ToDouble<R> extends ParseTo {

        double apply(R r);

    }

    /**
     * @author ForteScarlet <[email]ForteScarlet@163.com>
     * @since JDK1.8
     **/
    @FunctionalInterface
    interface ToInt<R> extends ParseTo {

        int apply(R r);
    }

    /**
     * @author ForteScarlet <[email]ForteScarlet@163.com>
     * @since JDK1.8
     **/
    @FunctionalInterface
    interface ToLong<R> extends ParseTo {

        long apply(R r);

    }

    /**
     * 转为String类型的函数
     * @author ForteScarlet <[email]ForteScarlet@163.com>
     * @since JDK1.8
     **/
    @FunctionalInterface
    interface ToString<T> extends Function<T, String>, ParseTo {
    }


    @FunctionalInterface
    interface ToChar<R> extends ParseTo {

        char apply(R r);

    }

    @FunctionalInterface
    interface ToByte<R> extends ParseTo {

        byte apply(R r);

    }




}
