package com.forte.utils.collections;

import com.forte.utils.function.ExConsumer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.*;

/**
 * 尝试实现一个排序map
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Deprecated
public class CacheLinkedMap<K, V>  {

//    private HashMap<K ,V> map;
//
//    private TimeNode[] tables;
    /** 永不过期的值的储存位置 */
    private final HashMap<K, V> NEVER_TIMEOUT = new HashMap<>(32);

    /** 存在的key值列表 */
    private final HashSet<K> KEYS = new HashSet<>();

    /** 时间排序，储存数据 */
    private TimeNode<K, V> nodes;


    /**
     * 储存一个数据
     * @param key       键
     * @param value     值
     * @param outTime   过期时间
     */
    public V put(K key, V value, long outTime){
        //如果时间小于0
        if(outTime < 0){
            throw new IllegalArgumentException("Expiration time cannot be less than zero, but : " + outTime);
        }

        if(outTime == 0){
            //如果直接为0，则说明此值永不过期，存入NEVER
            return NEVER_TIMEOUT.put(key, value);
        }

        //否则，正常保存
        return putVal(key, value, outTime);

    }

    /**
     * 保存一个值
     * @param key
     * @param value
     * @param outTime
     * @return
     */
    private V putVal(K key, V value, long outTime){
        //当前时间
        long nowTime = System.currentTimeMillis();
        if(nowTime >= outTime){
            //如果当前时间大于过期时间，则说明已经过期，直接返回null
            return null;
        }

        //否则，保存
        TimeNode<K, V> tn = putNode(new TimeNode<>(key, value, outTime));
        return tn == null ? null : tn.getValue();
    }





    /** 保存一个节点 */
    private TimeNode<K, V> putNode(TimeNode<K, V> newNode){
        //如果没有，赋值
        if(this.nodes == null){
            KEYS.add(newNode.getKey());
            this.nodes = newNode;
            return null;
        }else{
            //如果存在
            //如果是最后一个，也就是说只有唯一一个
            if(this.nodes.isLast()){
                KEYS.add(newNode.getKey());
                //如果只有一个，看看他们的key是否一样
                TimeNode<K, V> thisNode = this.nodes;
                if(thisNode.getKey() == newNode.getKey() ||
                        thisNode.getKey().equals(newNode.getKey())){
                    //key一样，直接替换
                    this.nodes = newNode;
                    return thisNode;
                }else{
                    //key值不一样
                    if(thisNode.getTime() >= newNode.getTime()){
                        //如果当前Node的时间比新加入的node晚，则新的作为首位
                        this.nodes = newNode;
                        //将当前的设置为下一个
                        newNode.setNext(thisNode);
                    }else{
                        //将新的值设置为当前的下一个
                        thisNode.setNext(newNode);
                    }
                    return null;
                }
            }else{
                //如果key已经存在，先移除这个key的节点
                if(KEYS.contains(newNode.getKey())){
                    //如果存在，查找这个Node
                    TimeNode<K, V> node = getNode(newNode.getKey());
                    //获取这个node的时间，如果时间相等则不做操作
                    long oldTime = node.getTime();
                    if(oldTime == newNode.getTime()){
                        return node;
                    }else{
                        //移除旧节点，并判断旧的时间与新的时间的关系
                        if(oldTime >= newNode.getTime()){
                            //如果旧的时间比新的时间要晚，从头遍历到旧节点
                            if(this.nodes.getTime() >= newNode.getTime()){
                                //第一个的时间也比新值大
                                //作为第一个值放入
                                TimeNode<K, V> thisNodes = this.nodes;
                                thisNodes.setPre(newNode);
                                this.nodes = newNode;

                            }else{
                                forEachNodes(this.nodes, TimeNode::getNext, (n1, n2) -> {
                                    if(n2 == null){
                                        //如果n2为null，则说明n1是最后一个
                                        //既然已经遍历到了最后一个，说明前面的都没符合的，直接放置于最后尾
                                        n1.setNext(newNode);
                                        return true;
                                    }else{
                                        //否则，查看关系
                                        if(n1.getTime() <= newNode.getTime() && n2.getTime() >= newNode.getTime()){
                                            //正好夹在中间
                                            n1.setNext(newNode);
                                            n2.setPre(newNode);
                                            return true;
                                        }else{
                                            //遍历到了旧节点
                                            return n1.equals(node);
                                        }
                                    }
                                });
                            }
                        }else{
                            //从旧的节点开始遍历到最后
                            forEachNodes(node, TimeNode::getNext, (n1, n2) -> {
                                if(n2 == null){
                                    //如果n2为null，则说明n1是最后一个
                                    //既然已经遍历到了最后一个，说明前面的都没符合的，直接放置于最后尾
                                    n1.setNext(newNode);
                                    return true;
                                }else{
                                    //否则，查看关系
                                    if(n1.getTime() <= newNode.getTime() && n2.getTime() >= newNode.getTime()){
                                        //正好夹在中间
                                        n1.setNext(newNode);
                                        n2.setPre(newNode);
                                        return true;
                                    }else{
                                        return false;
                                    }
                                }
                            });
                        }
                        removeNode(node);
                    }
                }else{
                    KEYS.add(newNode.getKey());
                    //不存在此key值，直接从头遍历到尾
                    forEachNodes(this.nodes, TimeNode::getNext, (n1, n2) -> {
                        if(n2 == null){
                            //如果n2为null，则说明n1是最后一个
                            //既然已经遍历到了最后一个，说明前面的都没符合的，直接放置于最后尾
                            n1.setNext(newNode);
                            return true;
                        }else{
                            //否则，查看关系
                            if(n1.getTime() <= newNode.getTime() && n2.getTime() >= newNode.getTime()){
                                //正好夹在中间
                                n1.setNext(newNode);
                                n2.setPre(newNode);
                                return true;
                            }else{
                                return false;
                            }
                        }
                    });


                }


            }

        }

        return null;
    }


    /**
     * forEach
     * @param consumer
     */
    public void forEach(ExConsumer<K, V, Long> consumer){
        if(this.nodes == null) {
            return;
        }
        for(TimeNode<K, V> i = this.nodes; i != null; i = i.next){
            consumer.accept(i.getKey(), i.getValue(), i.getTime());
        }
    }

    /**
     * forEach
     * @param consumer
     */
    public void forEach(Consumer<String> consumer){
        if(this.nodes == null) {
            return;
        }
        for(TimeNode<K, V> i = this.nodes; i != null; i = i.next){
            consumer.accept(i.toString());
        }
    }


    /**
     * 获取某个节点
     * TODO 暂时为public
     *
     */
    public TimeNode<K, V> getNode(K key){
        //寻找某个key
        if(nodes == null){
            return null;
        }

        TimeNode<K, V> node = nodes;

        do{
            if(node.getKey() == key || node.getKey().equals(key)){
                return node;
            }
            node = node.getNext();
        }while(node != null);
        return null;
    }


    /**
     * 遍历Node，可以指定从哪个Node开始，到哪个Node结束
     * @param start 指定node的开始节点
     * @param end 提供两个参数，一个是上一个节点，一个是当前节点，判断是否要结束遍历
     * 遍历值将根据start的值进行
     * 不论判断函数如何，当start的next为null的时候，下一次将不会再进行遍历。
     */
    public static <K, V> void forEachNodes(TimeNode<K, V> start,
                                           Function<TimeNode<K, V>, TimeNode<K, V>> getNext,
                                           BiPredicate<TimeNode<K, V>, TimeNode<K, V>> end){
        if(start == null){
            throw new NullPointerException();
        }

        TimeNode<K, V> now = start;
        TimeNode<K, V> next = getNext.apply(now);

        do{
            if(end.test(now, next)){
                break;
            }
            now = next;
            next = next == null ? null : getNext.apply(next);
        }while (now != null);
    }


    public void removeNode(TimeNode<K, V> node){
        //移除某个节点
        TimeNode<K, V> next = node.getNext();
        TimeNode<K, V>pre = node.getPre();

        if(pre == null && next == null){
            //仅此一个，直接移除
            this.nodes = null;
        }else{
            if(pre == null){
                //是第一个，将下一个设置为第一个
                next.setPre(null);
                this.nodes = next;
            }else if(next == null){
                //是最后一个，设置上一个的下一个是null
                pre.setNext(null);
            }else{
                //前后交接
                next.setPre(pre);
            }

        }

        KEYS.remove(node.getKey());
    }








    /**
     * 可以根据时间排序的Entry
     * 链表形式
     */
    static class TimeNode<K, V> implements Map.Entry<K, V>, Comparable<TimeNode<?, ?>> {

        private TimeNode<K, V> pre;
        private TimeNode<K, V> next;

        private final K key;
        private V value;
        private final long time;


        /**
         * 如果是第一个，则其没有前一个
         */
        public boolean isFirst(){
            return pre == null;
        }

        /**
         * 如果是最后一个，则其没有下一个
         */
        public boolean isLast(){
            return next == null;
        }

        public TimeNode<K, V> getPre() {
            return pre;
        }

        public void setPre(TimeNode<K, V> pre) {
            this.pre = pre;
            if(pre != null){
                pre.next = this;
            }
        }

        public TimeNode<K, V> getNext() {
            return next;
        }

        public void setNext(TimeNode<K, V> next) {
            this.next = next;
            if(next != null){
                next.pre = this;
            }
        }

        @Override
        public int compareTo(TimeNode<?, ?> o) {
            return Long.compare(time, o.time);
        }
        public long getTime(){
            return time;
        }
        @Override
        public K getKey() {
            return key;
        }
        @Override
        public V getValue() {
            return value;
        }
        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        @Override
        public String toString(){
            return "TimeNode{[time="+ time +"], "+ key +" = "+ value +"}";
        }


        TimeNode(K key, V value, long time){
            this.key = key;
            this.value = value;
            this.time = time;
        }

        public TimeNode(K key, V value, long time, TimeNode<K, V> pre, TimeNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.time = time;
            this.pre = pre;
            this.next = next;
        }
    }
}
