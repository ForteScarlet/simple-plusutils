package com.forte.utils.coordinate;

import java.util.Objects;

/**
 *
 * 一个简单的坐标点对象的抽象类
 * 泛型为一个Number类型的值，一般坐标点不是Integer就是Double之类的
 *
 * 坐标点有<code>x</code>, <code>y</code>, <code>z</code>
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class AbstractCoordinate<E extends Number> implements Coordinate<E> {

    protected final E x;
    protected final E y;
    protected final E z;

    @Override
    public E getX() {
        return x;
    }

    @Override
    public E getY() {
        return y;
    }

    @Override
    public E getZ() {
        return z;
    }

    @Override
    public String toString(){
        return "["+x+", "+y+", "+z+"]";
    }

    /**
     * 判断是否处于原点
     * 就是x,y,z都是0
     * @return 是否为原点
     */
    public abstract boolean isOrigin();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCoordinate<?> that = (AbstractCoordinate<?>) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                Objects.equals(z, that.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public AbstractCoordinate(E x, E y, E z){
        this.x = x;
        this.y = y;
        this.z = z;
    }



}
