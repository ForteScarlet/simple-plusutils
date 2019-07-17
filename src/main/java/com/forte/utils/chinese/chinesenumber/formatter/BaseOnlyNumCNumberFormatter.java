package com.forte.utils.chinese.chinesenumber.formatter;

import java.math.BigDecimal;

/**
 * 不会出现十位类型的解析
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/16 16:58
 * @since JDK1.8
 **/
public abstract class BaseOnlyNumCNumberFormatter<N extends Number> extends BaseCNumberFormatter<N> {
    public BaseOnlyNumCNumberFormatter(String[] chineseStr, boolean negative) {
        super(chineseStr, negative);
    }

    /**
     * 使用BigDecimal对中文数字进行解析并返回BigDecimal对象
     */
    @Override
    protected BigDecimal baseParse(String[] arr) {
        StringBuilder sb = new StringBuilder();
        //遍历
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            //获取每个字，获取他们的数字并拼接
            sb.append(get0_9Num(s));
        }
        return new BigDecimal(sb.toString());
    }


}
