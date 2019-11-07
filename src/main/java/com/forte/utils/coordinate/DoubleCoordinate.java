package com.forte.utils.coordinate;

/**
 *
 * Double类型的坐标点
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DoubleCoordinate extends AbstractCoordinate<Double> {
    @Override
    public boolean isOrigin() {
        return (x == 0) && (y == 0) && (z == 0);
    }

    public DoubleCoordinate(Double x, Double y, Double z) {
        super(x, y, z);
    }
}
