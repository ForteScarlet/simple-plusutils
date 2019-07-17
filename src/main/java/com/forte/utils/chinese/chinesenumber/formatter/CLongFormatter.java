package com.forte.utils.chinese.chinesenumber.formatter;


import com.forte.utils.chinese.chinesenumber.CNumber;

import java.math.BigDecimal;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/16 13:08
 * @since JDK1.8
 **/
public class CLongFormatter extends BaseNormalCNumberFormatter<Long> {

    private static final boolean FLOAT = false;

    public CLongFormatter(String[] chineseStr, boolean negative) {
        super(chineseStr, negative);
    }

    /**
     * 接收汉字字符串，转化为中文数字对象
     */
    @Override
    public CNumber<Long> parse() {
        //解析
        BigDecimal result = this.parseAsBigDecimal();
        //值处理完成，封装
        return new CNumber<>(getChineseStr(), result.longValue(), FLOAT, isNegative(), result);
    }

    @Override
    public boolean isFloat() {
        return FLOAT;
    }

}
