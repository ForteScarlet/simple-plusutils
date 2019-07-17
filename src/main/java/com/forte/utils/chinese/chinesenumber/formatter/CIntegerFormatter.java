package com.forte.utils.chinese.chinesenumber.formatter;


import com.forte.utils.chinese.chinesenumber.CNumber;
import com.forte.utils.chinese.chinesenumber.CNumberList;

import java.math.BigDecimal;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/16 13:08
 * @since JDK1.8
 **/
public class CIntegerFormatter extends BaseNormalCNumberFormatter<Integer> implements CNumberList {

    /** 是否为浮点数 */
    private static final boolean FLOAT = false;

    public CIntegerFormatter(String[] chineseStr, boolean negative) {
        super(chineseStr, negative);
    }

    /**
     * 接收汉字字符串，转化为中文数字对象
     */
    @Override
    public CNumber<Integer> parse() {

        BigDecimal result = this.parseAsBigDecimal();

        //值处理完成，封装
        return new CNumber<>(getChineseStr(), result.intValue(), FLOAT, isNegative(), result);
    }

    @Override
    public boolean isFloat() {
        return FLOAT;
    }
}
