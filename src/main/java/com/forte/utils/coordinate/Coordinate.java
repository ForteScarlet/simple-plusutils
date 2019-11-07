package com.forte.utils.coordinate;

/**
 *
 * 坐标点类接口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Coordinate<E extends Number> {
    /** 获取X坐标值 @return 获取X坐标值 */
    E getX();
    /** 获取Y坐标值 @return 获取Y坐标值 */
    E getY();
    /** 获取Z坐标值 @return 获取Z坐标值 */
    E getZ();

    /** x坐标值 @return x坐标值 */
    default E x() {
        return getX();
    }

    /** y坐标值 @return y坐标值 */
    default E y() {
        return getY();
    }

    /** z坐标值 @return z坐标值 */
    default E z() {
        return getZ();
    }
}
