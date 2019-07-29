package forte.test;

import com.forte.utils.file.FilePlusUtils;
import com.forte.utils.file.FileNameJoiner;
import org.junit.Test;

import java.io.File;

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


}
