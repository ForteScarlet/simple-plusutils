package forte.test;

import com.forte.utils.collections.ExStream;
import com.forte.utils.file.FilePlusUtils;
import com.forte.utils.file.FileNameJoiner;
import com.forte.utils.time.SimpleDateUtils;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test1 {

    @Test
    public void test1(){
        FileNameJoiner fileNameJoiner = FilePlusUtils.nameJoiner();
        FileNameJoiner joiner = fileNameJoiner.directory("F:").directory("forstudy");

        for (File file : joiner.listFiles()) {
            System.out.println(file.getAbsolutePath());
        }

    }

    @Test
    public void test2(){
        String s = SimpleDateUtils.toChineseWeekDay(LocalDate.now().getDayOfWeek());
        System.out.println(s);
    }

    @Test
    public void test3(){
        List<Integer> list = new ArrayList<Integer>(){{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }};


        ExStream<Integer> of = ExStream.of(list);

    }


}
