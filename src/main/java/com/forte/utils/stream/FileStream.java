package com.forte.utils.stream;

import java.io.File;
import java.util.stream.Stream;

/**
 * 文件的流对象
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class FileStream extends ExStream<File> {

    /**
     * 构造，以指定内部流对象
     */
    FileStream(Stream<File> stream) {
        super(stream);
    }

    protected static FileStream toFileEx(Stream<File> stream){
        return ofFile(stream);
    }

    public static FileStream ofFile(Stream<File> stream){
        return new FileStream(stream);
    }

    public static FileStream ofFile(File file){
        return toFileEx(Stream.of(file));
    }

    public static FileStream ofFile(File... file){
        return toFileEx(Stream.of(file));
    }

    public static FileStream ofFileInner(File file){
        File[] files = file.listFiles();
        if(files == null){
            return toFileEx(Stream.empty());
        }else{
            return toFileEx(Stream.of(files));
        }
    }



}
