package com.forte.utils.basis;

import com.forte.utils.coordinate.AbstractCoordinate;
import com.forte.utils.coordinate.IntCoordinate;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * 字符串匹配器
 * 当参数是一个直接的迭代器的时候，请注意从头开始，否则坐标的结果值会有偏差
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 * @deprecated 个人水平还是不行，没有达到预期效果
 *
 **/
@Deprecated
public class StringMatchFinder implements Iterator<AbstractCoordinate<Integer>> {

    /**
     * 查询索引位，默认初始值为0, 当值小于0的时候代表已经将迭代器迭代完了
     */
    private final AtomicInteger index = new AtomicInteger(0);

    /**
     * 字符串的字符迭代器
     */
    private final Iterator<Integer> charIterator;

    /**
     * 查询到的符合条件的值的索引位置坐标点
     * <p>
     * 坐标点仅使用x,y
     */
//    private final List<Coordinate<Integer>> foundIndex = new ArrayList<>();


    private final StringMatcher matcher;

    private AtomicReference<AbstractCoordinate<Integer>> next;

    /**
     * 当前的节点, 初始值为null
     */
    private AtomicReference<CharMatchNode> node = new AtomicReference<>();

    /**
     * 是否下一个开始查询， 一般与head结合
     */
    private final Predicate<Character> canFind;

    /**
     * 是否还有下一个
     */
    @Override
    public boolean hasNext() {
        if (!charIterator.hasNext()) {
            index.getAndSet(-1);
        }

        // 如果没有查询过，先查询第一个出现的
        if (next == null) {
            try {
                next();
            }catch (NoSuchElementException e){
                index.getAndSet(-1);
            }
        }

        // 如果下一个没有值了，返回null
        if (next.get() == null) {
            index.getAndSet(-1);
        }

        return index.get() >= 0;
    }

    /**
     * 寻找下一个匹配词
     */
    @Override
    public AbstractCoordinate<Integer> next() {
        // 是否是第一次执行此方法
        boolean first = false;
        // 如果匹配索引不是0, 则说明已经有一个值了，则此次为查询下一个值
        AbstractCoordinate<Integer> forReturn;
        AbstractCoordinate<Integer> forNext;
        first = index.get() == 0;
        if (!first) {
            forReturn = this.next.get();
            // 如果上一次查询结果是null，则直接返回null, 不再进行查询
            // 如果上一次查询结果是null，则抛出异常
            if (forReturn == null) {
                throw new NoSuchElementException();
            }
        } else {
            // 第一次
            next = new AtomicReference<>(null);
            forReturn = null;
        }
        // 查询下一个
        // 坐标点准备
        int x = -1;
        int y = -1;
        boolean matchbale = false;
        while (charIterator.hasNext()) {
            char c = (char) charIterator.next().intValue();
            // 判断当前字符是否可以匹配
            if (!matchbale) {
                // 如果当前不可匹配，则判断
                matchbale = canFind.test(c);
            }
            // 如果可以匹配，则直接匹配，回归至不可匹配的时候需要通过判断内部改变
            if (matchbale) {
                // 如果可以匹配，则进行匹配
                boolean findNode = findNode(c);
                // 判断当前字符是否与节点相同，如果相同则记录坐标
                if (findNode) {
                    // 如果找到了且x=-1,为x赋值初始值
                    if (x == -1) {
                        x = index.get();
                    }
                    // 如果当前节点是null，则说明匹配结束，为y赋值且结束循环
                    if (node.get() == null) {
                        y = index.get();
                        index.getAndAdd(1);
                        break;
                    }
                } else {
                    // 如果没有找到匹配的字符，回归为false
                    matchbale = false;
                    // 恢复坐标
                    x = -1;
                    y = -1;
                    resetNode();
                }
            } else {
                // 恢复坐标
                x = -1;
                y = -1;
                resetNode();
            }
            // 索引计数+1
            index.getAndAdd(1);
        }
        resetNode();

        if (first) {
            // 是第一次
            // 获取坐标对象并记录，返回上一次的数据
            if (x >= 0 && y > 0) {
                forReturn = forNext = new IntCoordinate(x, y, 0);
            } else {
                forReturn = forNext = null;
            }
        } else {
            // 获取坐标对象并记录，返回上一次的数据
            if (x >= 0 && y > 0) {
                forNext = new IntCoordinate(x, y, 0);
            } else {
                forNext = null;
            }
        }

        next.getAndSet(forNext);

        if (!charIterator.hasNext()) {
            // 如果没有下一个了，则记录-1标记为结束
            index.getAndSet(-1);
        }else{
//            index.getAndAdd(1);
        }
        // 而且，如果是第一次，再执行一次
        if(first){
            return next();
        }

        return forReturn;


//        }else{
//            // 第一次查询
//            // 开始迭代迭代器并通过节点查询，如果有head则当head匹配的时候在查询
//            // 是否匹配到一个
//            // 坐标点准备
//            int x = -1;
//            int y = -1;
//            boolean matchbale = false;
//            while(charIterator.hasNext()){
//                char c = (char) charIterator.next().intValue();
//                // 判断当前字符是否可以匹配
//                if(!matchbale){
//                    // 如果当前不可匹配，则判断
//                    matchbale = canFind.test(c);
//                }
//                // 如果可以匹配，则直接匹配，回归至不可匹配的时候需要通过判断内部改变
//                if(matchbale){
//                    // 如果可以匹配，则进行匹配
//                    boolean findNode = findNode(c);
//                    // 判断当前字符是否与节点相同，如果相同则记录坐标
//                    if(findNode){
//                        // 如果找到了且x=-1,为x赋值初始值
//                        if(x == -1){
//                            x = index.get();
//                        }
//                        // 如果当前节点是null，则说明匹配结束，为y赋值且结束循环
//                        if(node.get() == null){
//                            y = index.get();
//                            break;
//                        }
//                    }else{
//                        // 如果没有找到匹配的字符，回归为false
//                        matchbale = false;
//                        // 恢复坐标
//                        x = -1;
//                        y = -1;
//                    }
//                }else{
//                    // 恢复坐标
//                    x = -1;
//                    y = -1;
//
//
//                }
//                // 索引计数+1
//                index.getAndAdd(1);
//            }
//
//            // 获取坐标对象并记录，返回上一次的数据
//            if(x >= 0 && y > 0){
//                forReturn = forNext = new IntCoordinate(x, y, 0);
//            }else{
//                forReturn = forNext = null;
//            }
//            next.getAndSet(forNext);
//
//            if(!charIterator.hasNext()){
//                // 如果没有下一个了，则记录-1标记为结束
//                index.getAndSet(-1);
//            }
//
//            return forReturn;
//
//        }

    }

    private boolean findNode(char c) {
        // 寻找一个节点
        CharMatchNode thisNode = node.get();
        if (thisNode == null) {
            CharMatchNode findNode = matcher.find(c);
            if (findNode == null) {
                return false;
            } else {
                // 如果找到了，赋值并返回true
                node.getAndSet(findNode);
                return true;
            }
        } else {
            // 不是null, 尝试获取子节点
            // 如果是尾节点，返回true
            if (thisNode.isEnd()) {
                // 赋值为null，且返回true，代表一个匹配已经结束
                node.getAndSet(null);
                return true;
            } else {
                // 不是尾节点，获取子节点并赋值
                node.getAndSet(thisNode.find(c));
                return node.get() != null;
            }

        }
    }

    private void resetNode(){
        node.getAndSet(null);
    }



    public static StringMatchFinder of(String find, String... words) {
        return new StringMatchFinder(find.chars().iterator(), words);
    }

    public static StringMatchFinder of(String find, char head, String... words) {
        return new StringMatchFinder(find.chars().iterator(), head, words);
    }

    public static StringMatchFinder of(String find, StringMatcher matcher) {
        return new StringMatchFinder(find.chars().iterator(), matcher);
    }

    public static StringMatchFinder of(Iterator<Integer> find, String... words) {
        return new StringMatchFinder(find, words);
    }

    public static StringMatchFinder of(Iterator<Integer> find, char head, String... words) {
        return new StringMatchFinder(find, head, words);
    }

    public static StringMatchFinder of(Iterator<Integer> find, StringMatcher matcher) {
        return new StringMatchFinder(find, matcher);
    }

    public static StringMatchFinder of(char[] find, String... words) {
        int[] array = new int[find.length];
        for (int i = 0; i < find.length; i++) {
            array[i] = find[i];
        }
        return new StringMatchFinder(IntStream.of(array).iterator(), words);
    }

    public static StringMatchFinder of(char[] find, char head, String... words) {
        int[] array = new int[find.length];
        for (int i = 0; i < find.length; i++) {
            array[i] = find[i];
        }
        return new StringMatchFinder(IntStream.of(array).iterator(), head, words);
    }

    public static StringMatchFinder of(char[] find, StringMatcher matcher) {
        int[] array = new int[find.length];
        for (int i = 0; i < find.length; i++) {
            array[i] = find[i];
        }
        return new StringMatchFinder(IntStream.of(array).iterator(), matcher);
    }

    //**************** 构造列表 ****************//


    public StringMatchFinder(Iterator<Integer> find, String... words) {
        this.matcher = StringMatcher.getInstance(words);
        this.charIterator = find;
        // 没有head，直接开启
        canFind = c -> true;
    }

    public StringMatchFinder(Iterator<Integer> find, char head, String... words) {
        this.matcher = StringMatcher.getInstance(head, words);
        this.charIterator = find;
        AtomicBoolean can = new AtomicBoolean(false);
        canFind = c -> {
            // 如果可以，设置回不可以并返回true
            if (can.getAndSet(false)) {
                return true;
            } else {
                // 如果是false，判断
                boolean equals = c.equals(head);
                if (equals) {
                    // 如果相同，下一次获取则会为true
                    can.getAndSet(true);
                }
                return false;
            }
        };
    }

    /**
     * 构造之一
     *
     * @param find
     * @param matcher
     */
    public StringMatchFinder(Iterator<Integer> find, StringMatcher matcher) {
        this.matcher = matcher;
        this.charIterator = find;
        AtomicBoolean can = new AtomicBoolean(false);
        canFind = matcher.getHead() == null ? c -> true : c -> {
            // 如果可以，设置回不可以并返回true
            if (can.getAndSet(false)) {
                return true;
            } else {
                // 如果是false，判断
                boolean equals = c.equals(matcher.getHead());
                if (equals) {
                    // 如果相同，下一次获取则会为true
                    can.getAndSet(true);
                }
                return false;
            }
        };
    }

}
