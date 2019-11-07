package com.forte.utils.basis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 字符匹配节点
 * 个人水平还是不行，没有达到预期效果
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Deprecated
public class CharMatchNode {
    /** 当前节点的值 */
    private final char c;

    /** 子节点列表 */
    private final Map<Character, CharMatchNode> nodesMap;

    // 是否为尾节点
    public boolean isEnd(){
        return nodesMap == null || nodesMap.size() == 0;
    }

    public CharMatchNode find(char c){
        return nodesMap.get(c);
    }

    public char getNodeChar(){
        return c;
    }

    public CharMatchNode[] nodes(){
        if(nodesMap == null){
            return null;
        }
        return nodesMap.values().toArray(new CharMatchNode[0]);
    }

    /**
     * 构造
     * @param c c是当前节点的字符
     * @param nodes 当前节点下的所有子列表, 已经排除当前节点位置
     */
    public CharMatchNode(char c, char[][] nodes){
        this.c = c;
        if(nodes != null && nodes.length > 0){
            Map<Character, char[][]> tmpNodesMap = new HashMap<>();
            // 记录下子节点中的节点与字符数组
            Map<Character, char[][]> nodeMap = new HashMap<>();
            // 遍历nodes
            for (char[] node : nodes) {
                if(node == null || node.length == 0){
                    // 相当于没有子节点了，跳过
                    continue;
                }
                // 获取下一个节点的头
                char nextC = node[0];
                // 获取下一个节点的剩余节点
                char[] nextNodes = Arrays.copyOfRange(node, 1, node.length);

                // 放入或合并
                char[][] chars = tmpNodesMap.get(nextC);
                if(chars == null){
                    tmpNodesMap.put(nextC, new char[][]{nextNodes});
                }else{
                    final int index = chars.length;
                    chars = Arrays.copyOf(chars, chars.length + 1);
                    chars[index] = nextNodes;
                    // 重新放入
                    tmpNodesMap.put(nextC, chars);
                }
            }

            if(tmpNodesMap.size() == 0){
                nodesMap = null;
            }else{
                // 全部记录完成, 转化
                nodesMap = new HashMap<>(tmpNodesMap.size());
                // 遍历
                tmpNodesMap.forEach((k, v) -> nodesMap.put(k, new CharMatchNode(k, v)));
            }
        }else{
            // 没有子节点了，直接赋值为空
            nodesMap = null;
        }



    }



}
