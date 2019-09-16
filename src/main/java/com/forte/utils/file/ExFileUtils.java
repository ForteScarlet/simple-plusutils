package com.forte.utils.file;

import com.forte.utils.stream.CharStream;
import com.forte.utils.stream.StringStream;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.*;
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


    /**
     * 将字符流写入文件
     * @param file  文件对象
     * @param chars 字符流
     * @throws IOException
     */
    public static void write(File file, CharStream chars) throws IOException {
        createFile(file);

        try (
                FileWriter out = new FileWriter(file);
                BufferedWriter writer = new BufferedWriter(out)
        ) {
            writer.write(chars.toCharArray());
        }

    }

    /**
     * 将行写入文件
     * @param file  文件对象
     * @param lines 行数据
     * @throws IOException
     */
    public static void write(File file, String... lines) throws IOException {
        createFile(file);

        try (
                FileWriter out = new FileWriter(file);
                BufferedWriter writer = new BufferedWriter(out)
        ) {
            for (int i = 0; i < lines.length; i++) {
                if (i > 0) {
                    writer.newLine();
                }
                writer.write(lines[i]);
            }
        }

    }

    /**
     * 将行写入文件
     * @param file  文件对象
     * @param lines 行数据
     * @throws IOException
     */
    public static void write(File file, Collection<String> lines) throws IOException {
        createFile(file);

        try (
                FileWriter out = new FileWriter(file);
                BufferedWriter writer = new BufferedWriter(out)
        ) {
            int i = 0;
            for (String line : lines) {
                if (i > 0) {
                    writer.newLine();
                }
                writer.write(line);
                i++;
            }
        }
    }

    /**
     * 读取文件的char流对象
     * 效率情况不是很乐观，遍历全部字符的速度是BufferedReader的lines流遍历所有行数据的3倍。
     * @param file  文件对象
     * @return
     * @throws IOException
     */
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

    /**
     * 如果文件不存在，创建一个新文件
     * @param file 文件对象
     * @return  是否创建
     * @throws IOException
     */
    public static boolean createFile(File file) throws IOException {
        return !file.exists() && (file.getParentFile().mkdirs() && file.createNewFile());
    }

    /**
     * 获取主文件夹，例如window的桌面等
     * @return
     */
    public static File getHomeDirectory(){
        return getFileSystemView().getHomeDirectory();
    }

    /**
     * 获取文件在系统中的名称
     * @param file 文件对象
     */
    public static String getFileDisplayName(File file){
        return getFileSystemView().getSystemDisplayName(file);
    }

    /**
     * 获取{@link FileSystemView} 对象
     */
    public static FileSystemView getFileSystemView(){
        return FileSystemView.getFileSystemView();
    }



}
