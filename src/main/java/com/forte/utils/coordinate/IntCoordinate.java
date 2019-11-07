package com.forte.utils.coordinate;

/**
 *
 * int类型的坐标点
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class IntCoordinate extends Coordinate<Integer> {
    @Override
    public boolean isOrigin() {
        return (x | y | z) == 0;
    }

    public IntCoordinate(Integer x, Integer y, Integer z) {
        super(x, y, z);
    }
}
