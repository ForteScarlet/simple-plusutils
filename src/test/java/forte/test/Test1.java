package forte.test;


import com.forte.utils.reflect.EnumUtils;
import com.forte.utils.time.Stopwatch;

import java.util.function.Supplier;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test1 {

    public static final TestEm ts = TestEm.EN1;

    public static void main(String[] args) throws Exception {

        String name = "newInstance";
//
//        long s = System.currentTimeMillis();
//
//
//        long e = System.currentTimeMillis();
//
//        System.out.println((e - s) + "ms");
//
//        TestEm[] values = EnumUtils.values(TestEm.class, TestEm[]::new);
//        System.out.println("values.length: " + values.length);
//        System.out.println(values[0]);


        Stopwatch.difference();

        for (int i = 0; i < 1000_0000; i++) {
            final int index = i;
            EnumUtils.newEnum(TestEm.class, name + i, (Supplier<String>) () -> "我是被之后创建出来的！索引是" + index + "!");
//            System.out.println(testEm.ordinal());
        }

        System.out.println(Stopwatch.difference() + " ms");

        System.out.println("size: " + EnumUtils.values(TestEm.class, TestEm[]::new).length);

    }
}
