package com.forte.utils.basis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 *
 * 字符串匹配器
 * 当参数是一个直接的迭代器的时候，请注意从头开始，否则坐标的结果值会有偏差
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class StringMatchFinder implements Iterator<Coordinate<Integer>> {

    /** 查询索引位，默认初始值为0, 当值小于0的时候代表已经将迭代器迭代完了 */
    private final AtomicInteger index = new AtomicInteger(0);

    /** 字符串的字符迭代器  */
    private final Iterator<Integer> charIterator;

    /**
     * 查询到的符合条件的值的索引位置坐标点
     *
     * 坐标点仅使用x,y
     */
//    private final List<Coordinate<Integer>> foundIndex = new ArrayList<>();


    private final StringMatcher matcher;

    private AtomicReference<Coordinate<Integer>> next;

    /** 当前的节点, 初始值为null */
    private AtomicReference<CharMatchNode> node = new AtomicReference<>();

    /** 是否下一个开始查询， 一般与head结合 */
    private final Predicate<Character> canFind;

    /**
     * 是否还有下一个
     */
    @Override
    public boolean hasNext() {
        if(!charIterator.hasNext()) {
            index.getAndSet(-1);
        }

        // 如果没有查询过，先查询第一个出现的
        if(next == null) {
            next();
        }

        // 如果下一个没有值了，返回null
        if(next.get() == null) {
            index.getAndSet(-1);
        }

        return index.get() < 0;
    }

    /**
     * 寻找下一个匹配词
     */
    @Override
    public Coordinate<Integer> next() {
//        if(!hasNext()){
//            throw new RuntimeException("is over.");
//        }
        // 如果匹配索引不是0, 则说明已经有一个值了，则此次为查询下一个值
        Coordinate<Integer> forReturn;
        Coordinate<Integer> forNext;
        if(index.get() != 0){
            forReturn = this.next.get();
            // 如果上一次查询结果是null，则直接返回null, 不再进行查询
            if(forReturn == null){
                return null;
            }
            // 查询下一个




            return forReturn;
        }else{
            next = new AtomicReference<>(null);
            // 第一次查询
            // 开始迭代迭代器并通过节点查询，如果有head则当head匹配的时候在查询

            // 是否匹配到一个
            boolean matchbale = false;
            // 坐标点准备
            int x = -1;
            int y = -1;
            while(charIterator.hasNext()){
                char c = (char) charIterator.next().intValue();
                if(matchbale){
                    // 如果可以匹配，则进行匹配
//                    System.out.println(c);
//                    System.out.println(node.get());
//                    if(node.get()!=null)
//                        System.out.println("node char>>" + node.get().getNodeChar());
                    boolean findNode = findNode(c);
//                    System.out.println(node.get());
//                    if(node.get()!=null)
//                        System.out.println("node char>>" + node.get().getNodeChar());
//                    System.out.println(findNode);
//                    System.out.println("----");
                    // 判断当前字符是否与节点相同，如果相同则记录坐标
                    if(findNode){
                        // 如果找到了且x=-1,为x赋值初始值
                        if(x == -1){
                            x = index.get();
                        }
                        // 如果当前节点是null，则说明匹配结束，为y赋值且结束循环
                        if(node.get() == null){
                            y = index.get();
                            break;
                        }
                    }

                }else{
                    // 如果不行，判断下一个能否匹配
                    matchbale = canFind.test(c);
                }
                // 索引计数+1
                index.getAndAdd(1);
            }
            // 获取坐标对象并记录，返回上一次的数据
            if(x > 0 && y > 0){
                forReturn = forNext = new IntCoordinate(x, y, 0);
                next.getAndSet(forNext);
            }else{
                forReturn = forNext = null;
            }

            if(!charIterator.hasNext()){
                // 如果没有下一个了，则记录-1标记为结束
                index.getAndSet(-1);
            }

            return forReturn;

        }




    }

    private boolean findNode(char c){
        // 寻找一个节点
        CharMatchNode thisNode = node.get();
        if(thisNode == null){
            CharMatchNode findNode = matcher.find(c);
            if(findNode == null){
                return false;
            }else{
                // 如果找到了，赋值并返回true
                node.getAndSet(findNode);
                return true;
            }
        }else{
            // 不是null, 尝试获取子节点
            // 如果是尾节点，返回true
            if(thisNode.isEnd()){
                // 赋值为null，且返回true，代表一个匹配已经结束
                node.getAndSet(null);
                return true;
            }else{
                // 不是尾节点，获取子节点并赋值
                node.getAndSet(thisNode.find(c));
                return node.get() != null;
            }

        }

    }


    public static StringMatchFinder of(String find, String... words){
        return new StringMatchFinder(find.chars().iterator(), words);
    }
    public static StringMatchFinder of(String find, char head, String... words){
        return new StringMatchFinder(find.chars().iterator(), head, words);
    }
    public static StringMatchFinder of(String find, StringMatcher matcher){
        return new StringMatchFinder(find.chars().iterator(), matcher);
    }
    public static StringMatchFinder of(Iterator<Integer> find, String... words){
        return new StringMatchFinder(find, words);
    }
    public static StringMatchFinder of(Iterator<Integer> find, char head, String... words){
        return new StringMatchFinder(find, head, words);
    }
    public static StringMatchFinder of(Iterator<Integer> find, StringMatcher matcher){
        return new StringMatchFinder(find, matcher);
    }
    public static StringMatchFinder of(char[] find, String... words){
        int[] array = new int[find.length];
        for (int i = 0; i < find.length; i++) {
            array[i] = find[i];
        }
        return new StringMatchFinder(IntStream.of(array).iterator(), words);
    }
    public static StringMatchFinder of(char[] find, char head, String... words){
        int[] array = new int[find.length];
        for (int i = 0; i < find.length; i++) {
            array[i] = find[i];
        }
        return new StringMatchFinder(IntStream.of(array).iterator(), head, words);
    }
    public static StringMatchFinder of(char[] find, StringMatcher matcher){
        int[] array = new int[find.length];
        for (int i = 0; i < find.length; i++) {
            array[i] = find[i];
        }
        return new StringMatchFinder(IntStream.of(array).iterator(), matcher);
    }

    //**************** 构造列表 ****************//


    public StringMatchFinder(Iterator<Integer> find, String... words){
        this.matcher = StringMatcher.getInstance(words);
        this.charIterator = find;
        // 没有head，直接开启
        canFind = c -> true;
    }
    public StringMatchFinder(Iterator<Integer> find, char head, String... words){
        this.matcher = StringMatcher.getInstance(head, words);
        this.charIterator = find;
        canFind = c -> c.equals(head);
    }
    public StringMatchFinder(Iterator<Integer> find, StringMatcher matcher){
        this.matcher = matcher;
        this.charIterator = find;
        canFind = matcher.getHead() == null ? c -> true : c -> c.equals(matcher.getHead());
    }

}
