package com.forte.utils.basis;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 将字符串集合转化为Reader对象
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class StringListReader extends Reader {

    private final List<Character> charArr;

    //当前所在索引,默认从0开始
    private int index = 0;

    /** 是否关闭 */
    private boolean onClose = false;

    /**
     * Reads characters into a portion of an array.  This method will block
     * until some input is available, an I/O error occurs, or the end of the
     * stream is reached.
     *
     * @param cbuf Destination buffer
     * @param off  Offset at which to start storing characters
     * @param len  Maximum number of characters to read
     * @return The number of characters read, or -1 if the end of the
     * stream has been reached
     * @throws IOException If an I/O error occurs
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if(charArr.size() <= 0){
            return -1;
        }

        if(index > charArr.size() - 1){
            return -1;
        }

        //已经记录过的数量
        int nums = 0;

        //开始索引应该是数组的开始索引
        for (int i = 0; i < len; i++) {
            if(index > charArr.size() - 1){
                return nums;
            }
            cbuf[off+i] = charArr.get(index++);
            nums++;
        }

        return nums;
    }

    /**
     * Closes the stream and releases any system resources associated with
     * it.  Once the stream has been closed, further read(), ready(),
     * mark(), reset(), or skip() invocations will throw an IOException.
     * Closing a previously closed stream has no effect.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void close() throws IOException {

    }

    /**
     * Creates a new character-stream reader whose critical sections will
     * synchronize on the reader itself.
     */
    public StringListReader(List<String> list) {
        //使用properties接收参数，则将字符串集合转化为Reader流对象
        this.charArr = list.stream().skip(1).flatMap(s -> {
            s = s.endsWith("\n") ? s : s + "\n";
            char[] chars = s.toCharArray();
            Character[] arr = new Character[chars.length];
            for (int i = 0; i < chars.length; i++) {
                arr[i] = chars[i];
            }
            return Arrays.stream(arr);
        }).collect(Collectors.toList());
    }



}
