package com.forte.utils.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 对各种对象的操作
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ObjectUtils {

    /**
     * 通过反射，为一个对象生成toString字符串
     */
    public static String toString(Object object){
        if(object == null){
            return "null";
        }

        if(object instanceof String){
            return "\"" + object + '\"';
        }

        if(FieldUtils.isBasic(object.getClass())){
            return String.valueOf(object);
        }

//        //不是基础数据类型或者String类型，判断是否为AbstractCollection、AbstractMap
//        if(object instanceof AbstractCollection || object instanceof AbstractMap || ){
//            return object.toString();
//        }

        //如果是数组
        if(object.getClass().isArray()){
            return arrayToString((Object[]) object);
        }

        //如果是Entry
        if(object instanceof Map.Entry){
            return entryToString((Map.Entry<?, ?>) object);
        }

        //如果是Collection
        if(object instanceof Collection){
            return collectionToString((Collection) object);
        }

        //如果是Map
        if(object instanceof Map){
            return mapToString((Map) object);
        }

        return objectToString(object);
    }

    /**
     * collection转ToString
     */
    private static String collectionToString(Collection<?> collection){
        StringJoiner arrayToStringJoiner = getArrayToStringJoiner();

        collection.forEach(o -> arrayToStringJoiner.add(toString(o)));

        return arrayToStringJoiner.toString();
    }

    /**
     * Map转toString
     */
    private static String mapToString(Map<?, ?> map){
        StringJoiner mapToStringJoiner = getArrayToStringJoiner();

        map.forEach((k, v) -> mapToStringJoiner.add("{" + toString(k) + '=' + toString(v) + '}'));

        return mapToStringJoiner.toString();
    }

    private static String entryToString(Map.Entry<?, ?> entry){
        StringJoiner mapToStringJoiner = getEntryToStringJoiner();
        return mapToStringJoiner.add(toString(entry.getKey())).add(toString(entry.getValue())).toString();
    }


    /**
     * array转toString
     */
    private static String arrayToString(Object[] array){
        StringJoiner arrayToStringJoiner = getArrayToStringJoiner();

        for (Object o : array) {
            arrayToStringJoiner.add(toString(o));
        }

        return arrayToStringJoiner.toString();
    }

    private static String objectToString(Object obj){
        //一个普通的对象，尝试通过getter构建toString
        List<Method> getters = FieldUtils.getGetters(obj.getClass());

        StringJoiner toStringJoiner = getToStringJoiner(obj.getClass());

        getters.forEach(m -> {
            try {
                toStringJoiner.add(FieldUtils.getMethodNameWithoutGetter(m) + '=' + toString(m.invoke(obj)));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        return toStringJoiner.toString();
    }


    private static StringJoiner getToStringJoiner(Class<?> type){
        return new StringJoiner(", ", type.getSimpleName()+ "{", "}");
    }


    private static StringJoiner getArrayToStringJoiner(){
        return new StringJoiner(", ", "[", "]");
    }

    private static StringJoiner getEntryToStringJoiner(){
        return new StringJoiner("=", "{", "}");
    }

}
