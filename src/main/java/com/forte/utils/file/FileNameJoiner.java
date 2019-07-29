package com.forte.utils.file;

import java.io.File;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 文件名拼接类
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class FileNameJoiner {

    /** FileNameJoiner */
    private StringJoiner fileNameJoiner;


    /** 拼接一个路径，是个文件夹 */
    public FileNameJoiner directory(String directory){
        fileNameJoiner.add(directory);
        return this;
    }

    /**
     * 直接获取当前路径的文件对象
     */
    public File file(){
        return getFile();
    }

    /**
     * 获取文件列表
     * @throws NullPointerException maybe null
     * @see File#listFiles()
     */
    public File[] listFiles() {
        return getFile().listFiles();
    }

    /**
     * 获取文件列表，如果为null则返回空数组
     */
    public File[] listFilesOrEmpty(){
        File[] files = listFiles();
        return files == null ? new File[0] : files;
    }

    /**
     * 获取文件列表
     */
    public File[] listFilesIfNull(File[] ifNull){
        File[] files = listFiles();
        return files == null ? ifNull : files;
    }

    /**
     * 获取文件列表
     */
    public File[] listFilesIfNull(Function<File, File[]> ifNull){
        File file = getFile();
        File[] files = file.listFiles();
        return files == null ? ifNull.apply(file) : files;
    }

    /**
     * 获取文件列表
     */
    public Optional<File[]> listFilesOptional(){
        return Optional.ofNullable(listFiles());
    }

    /**
     * 获取文件列表流
     */
    public Stream<File> listFilesStream(){
        return Stream.of(listFiles());
    }

    /**
     * 获取文件列表流
     */
    public Stream<File> listFilesStream(Function<File, File[]> ifNull){
        return Stream.of(listFilesIfNull(ifNull));
    }

    /**
     * 获取文件列表流
     */
    public Stream<File> listFilesStream(File[] ifNull){
        return Stream.of(listFilesIfNull(ifNull));
    }

    /**
     * 是否为文件夹
     */
    public boolean isDirectory(){
        return getFile().isDirectory();
    }

    /**
     * 是否为文件
     */
    public boolean isFile(){
        return getFile().isFile();
    }

    /**
     * 是否存在
     */
    public boolean exists(){
        return getFile().exists();
    }

    /**
     * 同directory方法
     * @see #directory(String)
     */
    public FileNameJoiner folder(String folder){
        return directory(folder);
    }

    /**
     * 获取文件
     */
    public FileNameJoiner file(String fileName){
        fileNameJoiner.add(fileName);
        return this;
    }

    /**
     * 获取文件
     * @param fileName  文件名
     * @param fileType  文件扩展名
     * @return  文件对象
     */
    public FileNameJoiner file(String fileName, String fileType){
        return file(fileName + '.' + fileType);
    }



    private File getFile(){
        return new File(fileNameJoiner.toString());
    }

    @Override
    public String toString(){
        return fileNameJoiner.toString();
    }


    /**
     * 不指定开头
     */
    public FileNameJoiner(){
        fileNameJoiner = new StringJoiner(File.separator);
    }




}
