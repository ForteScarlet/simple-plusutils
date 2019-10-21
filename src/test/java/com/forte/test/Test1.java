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

        String name = "newInstance";
        TestEm newEm = EnumUtils.newEnum(TestEm.class, name, (Supplier<String>) () -> "我是被之后创建出来的！");

        System.out.println(newEm);
        System.out.println(newEm.sup.get());

        TestEm x = TestEm.valueOf(name);
        System.out.println(x);

        System.out.println(x == newEm);

        System.out.println(x.ordinal());

        System.out.println(Arrays.toString(TestEm.values()));

    }
}
