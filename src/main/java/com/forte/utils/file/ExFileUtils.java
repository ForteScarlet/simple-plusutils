package com.forte.utils.file;

import com.forte.utils.basis.ExStringUtils;
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

    /** 创建临时文件时候文件名的最小长度 */
    private static final int TMP_FILE_THE_SMALLEST_LENGTH = 3;

    private static final char TMP_NAME_SUPPLEMENT_CHAR = '_';

    /**
     * 获取一个临时文件
     * @param prefix        文件名前半部分，如果长度不足3则用'_'(下划线)进行补全
     * @param suffix        文件名后半部分，如果为null则默认为.tmp, nullable
     * @param directory     指定一个腹肌文件夹，nullable
     * @param deleteOnExit  是否在程序结束之后删除此文件, 默认不删除
     * @return 临时文件
     * @throws IOException
     */
    public static File getTempFile(String prefix, String suffix, File directory, boolean deleteOnExit) throws IOException {
        // 如果不足最小长度，补足至最小长度
        if(prefix.length() < TMP_FILE_THE_SMALLEST_LENGTH){
            prefix += ExStringUtils.repeat(TMP_NAME_SUPPLEMENT_CHAR, TMP_FILE_THE_SMALLEST_LENGTH - prefix.length());
        }
        File tempFile = File.createTempFile(prefix, suffix, directory);
        if(deleteOnExit){
            tempFile.deleteOnExit();
        }
        return tempFile;
    }

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String fileName, File directory, boolean deleteOnExit) throws IOException {
        int i = fileName.lastIndexOf('.');
        String prefix, suffix;
        if(i > -1){
            prefix = fileName.substring(0, i);
            suffix = fileName.substring(i+1);
        }else{
            prefix = fileName;
            suffix = null;
        }
        return getTempFile(prefix, suffix, directory, deleteOnExit);
    }

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String fileName, boolean deleteOnExit) throws IOException {
        return getTempFile(fileName, (File) null, deleteOnExit);
    }

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String fileName, File directory) throws IOException {
        return getTempFile(fileName, directory, false);
    }


    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String prefix, String suffix, boolean deleteOnExit) throws IOException {
        return getTempFile(prefix, suffix, null, deleteOnExit);
    }

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String prefix, String suffix, File directory) throws IOException {
        return getTempFile(prefix, suffix, directory, false);
    }

    /**
     * @see #getTempFile(String, String, File, boolean)
     */
    public static File getTempFile(String prefix, String suffix) throws IOException {
        return getTempFile(prefix, suffix, null, false);
    }



}
