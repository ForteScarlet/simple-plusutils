package com.forte.test;


import com.forte.utils.reflect.EnumUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test1 {

    public static final TestEm ts = TestEm.EN1;

    public static void main(String[] args) throws Exception {

//        String name = "newInstance";
//
//        long s = System.currentTimeMillis();
//
//        for (int i = 0; i < 100; i++) {
//            TestEm testEm = EnumUtils.newEnum(TestEm.class, name + i, (Supplier<String>) () -> "我是被之后创建出来的！");
////            System.out.println(testEm.ordinal());
//        }
//
//        long e = System.currentTimeMillis();
//
//        System.out.println((e - s) + "ms");
//
//        TestEm[] values = EnumUtils.values(TestEm.class, TestEm[]::new);
//        System.out.println("values.length: " + values.length);
//        System.out.println(values[0]);

        Integer[] a = {0,1,2,3,4,5,6,7,8,9};
        int i = 5;
        Integer[] integers = Arrays.stream(a).skip(i).limit(1).toArray(Integer[]::new);
        System.out.println(Arrays.toString(integers));


    }
}
