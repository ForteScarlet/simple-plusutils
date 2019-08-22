package com.forte.utils.stream;

import java.util.stream.Stream;

/**
 *
 * 字符串类型的流对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class StringStream extends ExStream<String> {
    /**
     * 构造，以指定内部流对象
     *
     * @param stream
     */
    StringStream(Stream<String> stream) {
        super(stream);
    }



}
