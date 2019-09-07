package com.forte.utils.file;

import com.forte.utils.stream.CharStream;
import com.forte.utils.stream.StringStream;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * 操作文件相关
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExFileUtils {

    /**
     * 获取FileNameJoiner
     */
    public static FileNameJoiner nameJoiner() {
        return new FileNameJoiner();
    }


    /**
     * 读取全部行（流对象
     */
    public static StringStream getLines(File file) throws FileNotFoundException {
        return StringStream.ofString(new BufferedReader(new FileReader(file)).lines());
    }


    public static void write(File file, CharStream chars) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(chars.toCharArray());
        writer.flush();
        writer.close();
    }


    public static CharStream getChars(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        PrimitiveIterator.OfInt iter = new PrimitiveIterator.OfInt() {
            int next = -1;

            @Override
            public boolean hasNext() {
                if (next > -1) {
                    return true;
                } else {
                    try {
                        next = reader.read();
                        if (next == -1) {
                            // 如果没有下一个了，关闭流
                            reader.close();
                        }
                        return (next > -1);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            }

            @Override
            public int nextInt() {
                if (next > -1 || hasNext()) {
                    int now = next;
                    next = -1;
                    return now;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };

        return CharStream.of(
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                        iter, Spliterator.ORDERED | Spliterator.NONNULL), false)
        );
    }


}
