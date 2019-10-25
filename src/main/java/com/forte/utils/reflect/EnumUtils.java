package com.forte.utils.reflect;

import com.forte.utils.function.ExFunction;
import sun.reflect.ConstructorAccessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

/**
 *
 * 枚举工具类
 *
 * <br>
 *
 * 通过反射的方式创建一个新的枚举类实例，并且可以通过valueOf方法获取到。<br>
 * 但是无法通过原本的values方法直接获取，（毕竟values方法获取的是final类型的数组，无法变更）<br>
 * 如果在创建后想要完整的values结果可以通过此类{@link EnumUtils} 中的values方法获取。<br>
 * 枚举内部的排序字段也会同时递增。<br>
 * <i>
 *  由于使用了深反射以绕过枚举类实例化检测，所以整个流程或多或少存在安全问题。
 * </i>
 *  <i>
 *      而关于性能问题，此类在初始化的时候会提前缓存所需要的资源，相对而言不会频繁使用反射，所以性能相对还是不会太低的。
 *      测试中：
 *      创建 100_0000 个枚举的新实例用时1s左右
 *      创建 1000_0000 个枚举的新实例用时22620 ms左右
 *  </i>
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class EnumUtils {
    /*
        newInstance ->
     */
    /** 默认的构造参数，其他参数均拼接于其之后 */
    private static final Class<?>[] DEFAULT_CONSTRUCTOR_TYPES;

    private static final Function<Class<?>[], Class<?>[]> constructorTypesCreator;

    private static final ExFunction<Class<? extends Enum<?>>, String, Object[], Object[]> constructorArgsCreator;

    /** 记录某枚举类型后续追加的参数类型 */
    private static final Map<Class<? extends Enum<?>>, AtomicInteger> enumOrdinalRecord;

    /** 枚举类型对应他的Map，用于保证可以通过valueOf方法获取到新增加的值 */
    private static final Map<Class<? extends Enum<?>>, EnumValueOfResource> enumValueOfResourceMap;

    // 初始化各种参数
    static {
        // 默认的构造参数-即当枚举无参数的时候的构造
        DEFAULT_CONSTRUCTOR_TYPES = new Class<?>[]{ String.class, int.class };

        enumOrdinalRecord = new ConcurrentHashMap<>(8);
        enumValueOfResourceMap = new ConcurrentHashMap<>(8);

        // 构造参数获取器
        constructorTypesCreator = ts -> {
            if(ts == null || ts.length == 0){
                return DEFAULT_CONSTRUCTOR_TYPES;
            }else{
                // 新数组
                Class<?>[] types = new Class<?>[ts.length + 2];
                types[0] = String.class;
                types[1] = int.class;
                // 有参数，拼接参数列表
                System.arraycopy(ts, 0, types,2, ts.length);
                return types;
            }
        };

        // 获取参数 - 需要枚举类型、枚举名称、和真正的参数集合
        constructorArgsCreator = (et, name, args) -> {
            AtomicInteger ordinalAtomic = enumOrdinalRecord.get(et);
            if(ordinalAtomic == null){
                // 构建真正的参数 - 先获取枚举当前的追加排序值
                Method values = EnumNewInstanceResource._enumValuesMethodFunction.apply(et);
                // 执行获取判断长度
                try {
                    Object[] valuesArray = (Object[]) values.invoke(null);
                    ordinalAtomic = new AtomicInteger(valuesArray == null ? 0 : valuesArray.length + 1);
                    enumOrdinalRecord.put(et, ordinalAtomic);
                } catch (Exception e) {
                    throw new RuntimeException("方法values执行失败！", e);
                }
            }
            // 增长一位
            int ordinal = ordinalAtomic.getAndAdd(1);
            // 构建参数
            if(args == null || args.length == 0){
                return new Object[]{name, ordinal};
            }else{
                Object[] params = new Object[args.length + 2];
                params[0] = name;
                params[1] = ordinal;
                // 有参数，拼接参数列表
                System.arraycopy(args, 0, params,2, args.length);
                return params;
            }
        };



    }


    /**
     * 获取一个枚举的新实例对象
     * @param enumType          枚举类型
     * @param name              枚举名称
     * @param constructorTypes  构造方法参数类型集
     * @param args              构造对应的参数列表
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    public static <T extends Enum<T>> T newEnum(Class<T> enumType, String name, Class<?>[] constructorTypes, Object[] args) throws NoSuchMethodException, IllegalAccessException {
        // 获取构造参数列表
        Class<?>[] conTypes = constructorTypesCreator.apply(constructorTypes);

        Constructor<T> constructor = enumType.getDeclaredConstructor(conTypes);
        // 开放此私有构造的公开使用
        constructor.setAccessible(true);

        // 通过构造获取实例对象
        return EnumNewInstanceResource.newEnumInstance(enumType, constructor, name, args);
    }

    /**
     * 获取一个枚举的新实例对象
     * 默认尝试使用枚举的无参构造
     * @param enumType  枚举类型
     * @param name      枚举的name
     * @return          枚举新实例对象
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    public static <T extends Enum<T>> T newEnum(Class<T> enumType, String name) throws NoSuchMethodException, IllegalAccessException {
        return newEnum(enumType, name, new Class<?>[0], new Object[0]);
    }

    /**
     * 创建一个枚举新实例，会根据枚举构造参数数量尝试寻找唯一对应的构造。
     * 找不到或者有多个会抛出异常。
     * @param enumType  枚举类型
     * @param name      枚举名称
     * @param args      枚举的构造参数
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    public static <T extends Enum<T>> T newEnum(Class<T> enumType, String name, Object[] args) throws NoSuchMethodException, IllegalAccessException {
        if(args == null || args.length == 0){
            return newEnum(enumType, name);
        }else{

            // 尝试寻找相同参数数量的构造
            Constructor<?>[] constructors = Arrays.stream(enumType.getDeclaredConstructors()).filter(c -> c.getParameterCount() == args.length + 2).toArray(Constructor<?>[]::new);

            // 如果只找到一个构造，则使用这个构造的类型数组，否则抛出异常
            if(constructors.length == 1){
                Class<?>[] parameterTypes = constructors[0].getParameterTypes();
                // 从索引2开始向后截取
                Class<?>[] cts = new Class<?>[parameterTypes.length - 2];
                System.arraycopy(parameterTypes, 2, cts, 0, cts.length);
                return newEnum(enumType, name, cts, args);
            }else{
                throw new RuntimeException("无法定位到参数数量为"+ args.length + 2 +"的构造方法：寻找到"+ constructors.length +"个");
            }
        }
    }

    /**
     * 获取枚举的全部实例
     * 由于原生的values属于final字段无法修改，于是从此处提供一个方法获取values值
     * 为了使得效果与原生values一致，会进行排序
     * @param t     枚举类型
     * @return      枚举全部实例
     */
    public static <T extends Enum<T>> T[] values(Class<T> t, IntFunction<T[]> initArray) {
        EnumValueOfResource<T> resource = enumValueOfResourceMap.get(t);
        if(resource == null){
            // 执行values方法
            try {
                return (T[]) EnumNewInstanceResource._enumValuesMethodFunction.apply(t).invoke(null);
            }catch (Exception e){
                throw new RuntimeException("invoke Enum method 'values' failed ", e);
            }
        }else{
            return resource.enumConstantDirectory.values().stream().sorted().toArray(initArray);
        }
    }


    /**
     * 通过枚举的类型获取这个枚举类型的成员Map
     * 会进行copy
     * @param enumType  枚举类型
     * @return          枚举内部全部成员对应键值对
     */
    public static <T extends Enum<T>> Map<String, T> getEnumConstantDirectory(Class<T> enumType) throws IllegalAccessException {
            return new HashMap<>(getEnumValueOfResource(enumType).enumConstantDirectory);
    }





    /**
     * 获取枚举元素资源封装类
     * @param enumType  枚举类型
     * @return  枚举元素值封装类
     * @throws IllegalAccessException
     */
    private static <T extends Enum<T>> EnumValueOfResource<T> getEnumValueOfResource(Class<T> enumType) throws IllegalAccessException {
        // 先看看是否存在此name的枚举
        EnumValueOfResource<T> enumValueOfResource = enumValueOfResourceMap.get(enumType);
        if(enumValueOfResource == null){
            // 如果没有，则实例化并保存
            enumValueOfResource = new EnumValueOfResource<>(enumType);
            enumValueOfResourceMap.put(enumType, enumValueOfResource);
        }

        return enumValueOfResource;
    }


    /**
     * 获取某个索引下的
     * @param t     枚举类型
     * @param index 索引位
     * @return      该索引位下的枚举元素
     */
    public static <T extends Enum<T>> T valueIndexOf(Class<T> t, int index){
        EnumValueOfResource<T> resource = enumValueOfResourceMap.get(t);
        if(resource == null){
            // 执行values方法
            try {
                return ((T[]) EnumNewInstanceResource._enumValuesMethodFunction.apply(t).invoke(null))[index];
            }catch (Exception e){
                throw new RuntimeException("invoke Enum method 'values' failed ", e);
            }
        }else{
            // 有， 截取
            return resource.enumConstantDirectory.values().stream().sorted().skip(index).limit(1).findFirst().orElse(null);
        }

    }




    /**
     * 枚举实例化所需资源， 内部提前拿取所需资源
     */
    static class EnumNewInstanceResource {

        /** 枚举类的values方法获取并缓存 */
        private static final Map<Class<? extends Enum<?>>, Method> _enumValuesMethods;

        /** 获取枚举类型下的values方法 */
        private static final Function<Class<? extends Enum<?>>, Method> _enumValuesMethodFunction;

        /** 枚举类中的valueOf方法 */
        private static final Method _enumValueOfMethod;

        private static final Consumer<Class<? extends Enum<?>>> _initValueOf;

        /** Class中的 enumConstantDirectory 字段，只能为枚举使用 */
        private static final Field _Class_enumConstantDirectory;
        /** Class中的 enumConstants 字段，只能为枚举使用 */
        private static final Field _Class_enumConstants;


        /** 构造对象内部的Override字段 */
        private static final Field _AccessibleObject_overrideField;

        /** 判断一个构造内部的override字段值 */
        private static final Predicate<Constructor<?>> _testOverride;

        /** Constructor中的 constructorAccessor 字段，用于获取实例化对象 */
        private static final Field _Constructor_constructorAccessor;

        /** Constructor中的 acquireConstructorAccessor 方法，用于constructorAccessor字段实例  */
        private static final Method _Constructor_acquireConstructorAccessor;

        /** 获取类 ConstructorAccessor 的实例对象 */
        private static final Function<Constructor<?>, ConstructorAccessor> _constructorAccessorCreator;

        /** 参数获取实例化对象 */
        private static final BiFunction<ConstructorAccessor, Object[], Object> _ConstructorAccessor_newInstance;

        /** 根据以上所有的字段、构造实例、参数来获取一个新的实例对象 */
        private static final BiFunction<Constructor<?>, Object[], ?> _newInstance;

        static {
            try {
                // enumValues方法的缓存Map
                _enumValuesMethods = new ConcurrentHashMap<>(8);

                // enumValues方法的获取函数 - 通过缓存，如果没有则反射获取
                _enumValuesMethodFunction = et -> {
                    Method method = _enumValuesMethods.get(et);
                    if(method != null){
                        return method;
                    }else{
                        try {
                            method = et.getMethod("values");
                            _enumValuesMethods.put(et, method);
                            return method;
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException("获取values方法失败！", e);
                        }
                    }
                };

                // valueOf方法
                _enumValueOfMethod = Enum.class.getMethod("valueOf", Class.class, String.class);

                // 执行valueOf方法且无视结果
                _initValueOf = et -> {
                    try {
                        // 必定抛出空指针异常
                        _enumValueOfMethod.invoke(null, et, null);
                    }catch (Exception ignore){ }
                };

                // 获取Class两个字段
                Class<Class> clzType = Class.class;
                /*
                    enumConstantDirectory字段 与 enumConstants字段
                 */
                _Class_enumConstantDirectory = clzType.getDeclaredField("enumConstantDirectory");
                _Class_enumConstantDirectory.setAccessible(true);
                _Class_enumConstants = clzType.getDeclaredField("enumConstants");
                _Class_enumConstants.setAccessible(true);


                // 获取override字段
                _AccessibleObject_overrideField = AccessibleObject.class.getDeclaredField("override");
                _AccessibleObject_overrideField.setAccessible(true);
                // 构建检测函数
                _testOverride = c -> {
                    try {
                        return (boolean) _AccessibleObject_overrideField.get(c);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("枚举字段 override 检测失败！", e);
                    }
                };

                //获取构造对象中的字段 constructorAccessor
                _Constructor_constructorAccessor = Constructor.class.getDeclaredField("constructorAccessor");
                _Constructor_constructorAccessor.setAccessible(true);

                // 获取构造对象中的方法 acquireConstructorAccessor() ，当上面那个字段获取而不存在的时候通过这个方法来实例化
                _Constructor_acquireConstructorAccessor = Constructor.class.getDeclaredMethod("acquireConstructorAccessor");
                _Constructor_acquireConstructorAccessor.setAccessible(true);

                // 根据构造的实例对象获取 ConstructorAccessor 类实例，用于下一步获取实例化对象操作
                _constructorAccessorCreator = c -> {
                    try {
                        return (ConstructorAccessor) _Constructor_acquireConstructorAccessor.invoke(c);
                    } catch (Exception e) {
                        throw new RuntimeException("sun.reflect.ConstructorAccessor 实例对象获取失败！", e);
                    }
                };

                // 获取实例化对象
                _ConstructorAccessor_newInstance = (ca, args) -> {
                    // 通过 ConstructorAccessor 获取实例对象
                    try {
                        return ca.newInstance(args);
                    } catch (Exception e) {
                        throw new RuntimeException("获取实例化对象失败！", e);
                    }
                };

                // 实例对象获取一条龙服务
                _newInstance = (c, args) -> {
                    // 通过构造获取 ConstructorAccessor 对象
                    ConstructorAccessor constructorAccessor = _constructorAccessorCreator.apply(c);
                    // 通过 ConstructorAccessor 获取实例化对象
                    return _ConstructorAccessor_newInstance.apply(constructorAccessor, args);
                };

            }catch (Exception e){
                throw new RuntimeException("枚举实例化资源初始化失败！", e);
            }
        }

        public static synchronized <T extends Enum<T>> T newEnumInstance(Class<T> type, Constructor<T> constructor, String name, Object[] args) throws IllegalAccessException {
            EnumValueOfResource<T> enumValueOfResource = getEnumValueOfResource(type);

            // 判断name是否已经存在
            if(enumValueOfResource.enumConstantDirectory.containsKey(name)){
                throw new IllegalAccessException(type + "中已经存在name值为 '"+ name +"' 的枚举类型");
            }

            // 构建真正的参数
            args = constructorArgsCreator.apply(type, name, args);


            // 实例化Resource之后，创建枚举实例
            T newEnum = (T) _newInstance.apply(constructor, args);

            // 保存这个枚举新实例到Map并返回
            enumValueOfResource.enumConstantDirectory.put(newEnum.name(), newEnum);
            return newEnum;
        }


    }


    /**
     * 封装某个枚举类型内部对应的
     * enumConstantDirectory字段
     * 与
     * enumConstants字段
     */
    static class EnumValueOfResource<T extends Enum<T>> {
        /** 枚举真正的对象 */
        private Class<T> enumType;
        /** 此Map控制着对应枚举类的valueOf方法 */
        private Map<String, T> enumConstantDirectory;

        // 此字段似乎仅使用过一次以用于初始化上面那个字段，所以暂时先不获取它了
        //  private T[] enumConstants;

        /**
         * 构造，提供枚举类型
         * @param enumType  枚举类型
         * @throws IllegalAccessException
         */
        private EnumValueOfResource (Class<T> enumType) throws IllegalAccessException {
            this.enumType = enumType;
            // 首先调用一次这个枚举的valueOf方法以初始化那两个参数
            EnumNewInstanceResource._initValueOf.accept(enumType);

            // 然后获取两个参数
            enumConstantDirectory = ( Map<String, T>) EnumNewInstanceResource._Class_enumConstantDirectory.get(enumType);
        }

        @Override
        public String toString(){
            return "{Resource for ["+ enumType +"]}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EnumValueOfResource<?> that = (EnumValueOfResource<?>) o;
            return enumType.equals(that.enumType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(enumType);
        }
    }

}
