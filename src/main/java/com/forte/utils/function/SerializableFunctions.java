package com.forte.utils.function;

import java.io.Serializable;
import java.lang.reflect.TypeVariable;
import java.util.StringJoiner;
import java.util.function.*;

/**
 *
 * all function interfaces implements {@link Serializable}
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SerializableFunctions {
    /** all of the function interfaces, package: java.util.function */
    public static final Class<?>[] ALL_FUNCTIONS = {
            BiConsumer.class,
            BiFunction.class,
            BinaryOperator.class,
            BiPredicate.class,
            BooleanSupplier.class,
            Consumer.class,
            DoubleBinaryOperator.class,
            DoubleConsumer.class,
            DoubleFunction.class,
            DoublePredicate.class,
            DoubleSupplier.class,
            DoubleToIntFunction.class,
            DoubleToLongFunction.class,
            DoubleUnaryOperator.class,
            Function.class,
            IntBinaryOperator.class,
            IntConsumer.class,
            IntFunction.class,
            IntPredicate.class,
            IntSupplier.class,
            IntToDoubleFunction.class,
            IntToLongFunction.class,
            IntUnaryOperator.class,
            LongBinaryOperator.class,
            LongConsumer.class,
            LongFunction.class,
            LongPredicate.class,
            LongSupplier.class,
            LongToDoubleFunction.class,
            LongToIntFunction.class,
            LongUnaryOperator.class,
            ObjDoubleConsumer.class,
            ObjIntConsumer.class,
            ObjLongConsumer.class,
            Predicate.class,
            Supplier.class,
            ToDoubleBiFunction.class,
            ToDoubleFunction.class,
            ToIntBiFunction.class,
            ToIntFunction.class,
            ToLongBiFunction.class,
            ToLongFunction.class,
            UnaryOperator.class
    };

//    public static void main(String[] args) {
//
//        long baseID = 1145148100000000000L;
//
//        for (Class<?> fc : ALL_FUNCTIONS) {
//            String className = fc.getSimpleName();
//            String generic = getGenericString(fc);
//            String genericName = className + generic;
//            String serializableName = "Serializable" + genericName;
//
//            long serialVersionUID = baseID += 1;
//
//            String out = "public static interface " + serializableName + " extends "+ genericName +", Serializable {long serialVersionUID = " + serialVersionUID + "L;}";
//            System.out.println("/** An interface that extends both {@link java.util.function."+ className +"} and {@link Serializable} */");
//            System.out.println(out);
//            System.out.println();
//        }
//    }

    /**
     * Get the generic string of the Class ( if it exists )
     * @param c class
     * @return generic string
     */
    public static String getGenericString(Class<?> c){
        TypeVariable<? extends Class<?>>[] typeParameters = c.getTypeParameters();
        if(typeParameters.length > 0){
            StringJoiner joiner = new StringJoiner(",", "<", ">");
            for (TypeVariable<? extends Class<?>> t : typeParameters) {
                joiner.add(t.getTypeName());
            }
            return joiner.toString();
        }
        return "";
    }


    /** An interface that extends both {@link BiConsumer} and {@link Serializable} */
    public static interface SerializableBiConsumer<T,U> extends BiConsumer<T,U>, Serializable {long serialVersionUID = 1145148100000000001L;}

    /** An interface that extends both {@link BiFunction} and {@link Serializable} */
    public static interface SerializableBiFunction<T,U,R> extends BiFunction<T,U,R>, Serializable {long serialVersionUID = 1145148100000000002L;}

    /** An interface that extends both {@link BinaryOperator} and {@link Serializable} */
    public static interface SerializableBinaryOperator<T> extends BinaryOperator<T>, Serializable {long serialVersionUID = 1145148100000000003L;}

    /** An interface that extends both {@link BiPredicate} and {@link Serializable} */
    public static interface SerializableBiPredicate<T,U> extends BiPredicate<T,U>, Serializable {long serialVersionUID = 1145148100000000004L;}

    /** An interface that extends both {@link BooleanSupplier} and {@link Serializable} */
    public static interface SerializableBooleanSupplier extends BooleanSupplier, Serializable {long serialVersionUID = 1145148100000000005L;}

    /** An interface that extends both {@link Consumer} and {@link Serializable} */
    public static interface SerializableConsumer<T> extends Consumer<T>, Serializable {long serialVersionUID = 1145148100000000006L;}

    /** An interface that extends both {@link DoubleBinaryOperator} and {@link Serializable} */
    public static interface SerializableDoubleBinaryOperator extends DoubleBinaryOperator, Serializable {long serialVersionUID = 1145148100000000007L;}

    /** An interface that extends both {@link DoubleConsumer} and {@link Serializable} */
    public static interface SerializableDoubleConsumer extends DoubleConsumer, Serializable {long serialVersionUID = 1145148100000000008L;}

    /** An interface that extends both {@link DoubleFunction} and {@link Serializable} */
    public static interface SerializableDoubleFunction<R> extends DoubleFunction<R>, Serializable {long serialVersionUID = 1145148100000000009L;}

    /** An interface that extends both {@link DoublePredicate} and {@link Serializable} */
    public static interface SerializableDoublePredicate extends DoublePredicate, Serializable {long serialVersionUID = 1145148100000000010L;}

    /** An interface that extends both {@link DoubleSupplier} and {@link Serializable} */
    public static interface SerializableDoubleSupplier extends DoubleSupplier, Serializable {long serialVersionUID = 1145148100000000011L;}

    /** An interface that extends both {@link DoubleToIntFunction} and {@link Serializable} */
    public static interface SerializableDoubleToIntFunction extends DoubleToIntFunction, Serializable {long serialVersionUID = 1145148100000000012L;}

    /** An interface that extends both {@link DoubleToLongFunction} and {@link Serializable} */
    public static interface SerializableDoubleToLongFunction extends DoubleToLongFunction, Serializable {long serialVersionUID = 1145148100000000013L;}

    /** An interface that extends both {@link DoubleUnaryOperator} and {@link Serializable} */
    public static interface SerializableDoubleUnaryOperator extends DoubleUnaryOperator, Serializable {long serialVersionUID = 1145148100000000014L;}

    /** An interface that extends both {@link Function} and {@link Serializable} */
    public static interface SerializableFunction<T,R> extends Function<T,R>, Serializable {long serialVersionUID = 1145148100000000015L;}

    /** An interface that extends both {@link IntBinaryOperator} and {@link Serializable} */
    public static interface SerializableIntBinaryOperator extends IntBinaryOperator, Serializable {long serialVersionUID = 1145148100000000016L;}

    /** An interface that extends both {@link IntConsumer} and {@link Serializable} */
    public static interface SerializableIntConsumer extends IntConsumer, Serializable {long serialVersionUID = 1145148100000000017L;}

    /** An interface that extends both {@link IntFunction} and {@link Serializable} */
    public static interface SerializableIntFunction<R> extends IntFunction<R>, Serializable {long serialVersionUID = 1145148100000000018L;}

    /** An interface that extends both {@link IntPredicate} and {@link Serializable} */
    public static interface SerializableIntPredicate extends IntPredicate, Serializable {long serialVersionUID = 1145148100000000019L;}

    /** An interface that extends both {@link IntSupplier} and {@link Serializable} */
    public static interface SerializableIntSupplier extends IntSupplier, Serializable {long serialVersionUID = 1145148100000000020L;}

    /** An interface that extends both {@link IntToDoubleFunction} and {@link Serializable} */
    public static interface SerializableIntToDoubleFunction extends IntToDoubleFunction, Serializable {long serialVersionUID = 1145148100000000021L;}

    /** An interface that extends both {@link IntToLongFunction} and {@link Serializable} */
    public static interface SerializableIntToLongFunction extends IntToLongFunction, Serializable {long serialVersionUID = 1145148100000000022L;}

    /** An interface that extends both {@link IntUnaryOperator} and {@link Serializable} */
    public static interface SerializableIntUnaryOperator extends IntUnaryOperator, Serializable {long serialVersionUID = 1145148100000000023L;}

    /** An interface that extends both {@link LongBinaryOperator} and {@link Serializable} */
    public static interface SerializableLongBinaryOperator extends LongBinaryOperator, Serializable {long serialVersionUID = 1145148100000000024L;}

    /** An interface that extends both {@link LongConsumer} and {@link Serializable} */
    public static interface SerializableLongConsumer extends LongConsumer, Serializable {long serialVersionUID = 1145148100000000025L;}

    /** An interface that extends both {@link LongFunction} and {@link Serializable} */
    public static interface SerializableLongFunction<R> extends LongFunction<R>, Serializable {long serialVersionUID = 1145148100000000026L;}

    /** An interface that extends both {@link LongPredicate} and {@link Serializable} */
    public static interface SerializableLongPredicate extends LongPredicate, Serializable {long serialVersionUID = 1145148100000000027L;}

    /** An interface that extends both {@link LongSupplier} and {@link Serializable} */
    public static interface SerializableLongSupplier extends LongSupplier, Serializable {long serialVersionUID = 1145148100000000028L;}

    /** An interface that extends both {@link LongToDoubleFunction} and {@link Serializable} */
    public static interface SerializableLongToDoubleFunction extends LongToDoubleFunction, Serializable {long serialVersionUID = 1145148100000000029L;}

    /** An interface that extends both {@link LongToIntFunction} and {@link Serializable} */
    public static interface SerializableLongToIntFunction extends LongToIntFunction, Serializable {long serialVersionUID = 1145148100000000030L;}

    /** An interface that extends both {@link LongUnaryOperator} and {@link Serializable} */
    public static interface SerializableLongUnaryOperator extends LongUnaryOperator, Serializable {long serialVersionUID = 1145148100000000031L;}

    /** An interface that extends both {@link ObjDoubleConsumer} and {@link Serializable} */
    public static interface SerializableObjDoubleConsumer<T> extends ObjDoubleConsumer<T>, Serializable {long serialVersionUID = 1145148100000000032L;}

    /** An interface that extends both {@link ObjIntConsumer} and {@link Serializable} */
    public static interface SerializableObjIntConsumer<T> extends ObjIntConsumer<T>, Serializable {long serialVersionUID = 1145148100000000033L;}

    /** An interface that extends both {@link ObjLongConsumer} and {@link Serializable} */
    public static interface SerializableObjLongConsumer<T> extends ObjLongConsumer<T>, Serializable {long serialVersionUID = 1145148100000000034L;}

    /** An interface that extends both {@link Predicate} and {@link Serializable} */
    public static interface SerializablePredicate<T> extends Predicate<T>, Serializable {long serialVersionUID = 1145148100000000035L;}

    /** An interface that extends both {@link Supplier} and {@link Serializable} */
    public static interface SerializableSupplier<T> extends Supplier<T>, Serializable {long serialVersionUID = 1145148100000000036L;}

    /** An interface that extends both {@link ToDoubleBiFunction} and {@link Serializable} */
    public static interface SerializableToDoubleBiFunction<T,U> extends ToDoubleBiFunction<T,U>, Serializable {long serialVersionUID = 1145148100000000037L;}

    /** An interface that extends both {@link ToDoubleFunction} and {@link Serializable} */
    public static interface SerializableToDoubleFunction<T> extends ToDoubleFunction<T>, Serializable {long serialVersionUID = 1145148100000000038L;}

    /** An interface that extends both {@link ToIntBiFunction} and {@link Serializable} */
    public static interface SerializableToIntBiFunction<T,U> extends ToIntBiFunction<T,U>, Serializable {long serialVersionUID = 1145148100000000039L;}

    /** An interface that extends both {@link ToIntFunction} and {@link Serializable} */
    public static interface SerializableToIntFunction<T> extends ToIntFunction<T>, Serializable {long serialVersionUID = 1145148100000000040L;}

    /** An interface that extends both {@link ToLongBiFunction} and {@link Serializable} */
    public static interface SerializableToLongBiFunction<T,U> extends ToLongBiFunction<T,U>, Serializable {long serialVersionUID = 1145148100000000041L;}

    /** An interface that extends both {@link ToLongFunction} and {@link Serializable} */
    public static interface SerializableToLongFunction<T> extends ToLongFunction<T>, Serializable {long serialVersionUID = 1145148100000000042L;}

    /** An interface that extends both {@link UnaryOperator} and {@link Serializable} */
    public static interface SerializableUnaryOperator<T> extends UnaryOperator<T>, Serializable {long serialVersionUID = 1145148100000000043L;}



}
