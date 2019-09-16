package com.forte.utils.regex;

import com.forte.utils.collections.ExSimpleIterator;
import com.forte.utils.stream.ExStream;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 正则表达式工具类
 *
 * @author 来源:https://www.cnblogs.com/jimmy-c/p/4139664.html
 */
public class RegexUtil {

    static {
        //初始化部分资源
        PATTERN_DEFAULT_FLAG = 0;
        csp = '\\';


        String asRe = toIgnoreCaseRegex("as");
        String fromRe = toIgnoreCaseRegex("from");
        //初始化部分参数
        //sql字符串的表解析正则
        //多表必定有别名
        String r1 = "("+ fromRe +") +[\\w_]+(( +("+ asRe +"))? +)? +\\w+ *(, *[\\w_]+( +("+ asRe +"))? +\\w+)+ *";
        //( +(WHERE|where))?
        //from table (as? (tableName))?
        String r2 = "("+ fromRe +") +[\\w_]+(( +("+ asRe +"))? +\\w+)?((?!,) *)";
        //( +(WHERE|where))?
        //TODO JOIN


        //多表在前，单表在后，还有join表
        String regex = "" +
                "("+
                r1 +
                ")"+
                "|"+
                "(" +
                r2 +
                ")";
        SQL_REGEX = regex;
    }

    /** 解析出select sql中的表名正则 */
    private static final String SQL_REGEX;

    /** 所有的正则解析式 */
    private static final Map<String, Map<Integer, Pattern>> PATTERNS = new ConcurrentHashMap<>();

    /** 正则默认的初始flags值 */
    private static final int PATTERN_DEFAULT_FLAG;

    /** 特殊符号 */
    private static final Character csp;

    public static String toIgnoreCaseRegex(String str){
        //前一个字符
        Character last1 = null;
        //前两个字符
        Character last2 = null;

        StringBuilder sb = new StringBuilder();

        for (char c : str.toCharArray()) {
            //如果上一个字符不为\或者前两个字符都是\\
            boolean b = (!csp.equals(last1)) || (csp.equals(last2));
            if(b && Character.isLetter(c)){
                sb.append('[').append(Character.toLowerCase(c)).append(Character.toUpperCase(c)).append(']');
            }else{
                sb.append(c);
            }
            Character in = last1;
            last1 = c;
            last2 = in;
        }
        return sb.toString();
    }

    /**
     * 获取正则对应的pattern
     */
    public static Pattern getPattern(String regex, int flags){
        Pattern pattern;
        Map<Integer, Pattern> flagPattern = PATTERNS.get(regex);
        if(flagPattern == null){
            //如果压根没有，保存一个并返回
            flagPattern = new ConcurrentHashMap<>(1);
            //保存flag
            PATTERNS.put(regex, flagPattern);
            pattern = Pattern.compile(regex);
            //保存正则
            flagPattern.put(flags, pattern);
            return pattern;
        }else{
            //如果有，看看有没有对应的FLAG的
            pattern = flagPattern.get(flags);
            if(pattern == null){
                //如果没有，创建保存并返回
                pattern = Pattern.compile(regex, flags);
                flagPattern.put(flags, pattern);
            }
        }
        return pattern;
    }

    public static Pattern getPattern(String regex){
        return getPattern(regex, PATTERN_DEFAULT_FLAG);
    }

    public static Matcher getMatcher(String source, String regex){
        return getPattern(regex).matcher(source);
    }

    public static Matcher getMatcher(String source, String regex, int flags){
        return getPattern(regex, flags).matcher(source);
    }

    public static Matcher getMatcher(String source, Pattern pattern){
        return pattern.matcher(source);
    }

    /**
     * 切割出匹配正则的字符串列表
     */
    public static List<String> getSplit(String source, Pattern pattern){
        /*
            Pattern： 一个Pattern是一个正则表达式经编译后的表现模式。
            Matcher： 一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
         */
        Matcher matcher = pattern.matcher(source);
        //记录匹配的数量并创建数组
        List<String> end = new ArrayList<>();
        //遍历并保存结果集

        while (matcher.find()) {
            end.add(matcher.group());
        }

//        //返回结果
        return end;
    }

    /**
     * 切割出匹配正则的字符串流对象
     */
    public static Stream<String> getSplitStream(String source, Pattern pattern){
        /*
            Pattern： 一个Pattern是一个正则表达式经编译后的表现模式。
            Matcher： 一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
         */
        Matcher matcher = pattern.matcher(source);
        //构建迭代器对象
        Iterator<String> iter = new ExSimpleIterator<>(matcher::find, matcher::group);
        //通过迭代器构建Stream对象
        return ExStream.byIter(iter);
    }

    /**
     * 切割出匹配正则的字符串列表
     */
    public static List<String> getSplit(String source, String regex) {
        return getSplit(source, getPattern(regex));
    }

    /**
     * 切割出匹配正则的字符串列表
     */
    public static Stream<String> getSplitStream(String source, String regex) {
        return getSplitStream(source, getPattern(regex));
    }

    /**
     * 获取字符串
     */
    public static String[] getSplitArray(String source, String regex){
        return getSplit(source, regex).toArray(new String[0]);
    }

    /**
     * 获取字符串
     */
    public static String[] getMatcherArray(String source, Pattern pattern){
        return getSplit(source, pattern).toArray(new String[0]);
    }


    /**
     * 获取sql查询语句中使用到的表名
     * 暂时不支持join类型的查询sql
     * @param selectSql
     * @return
     */
    public static List<String> getTableNameFromSql(String selectSql){
        List<String> matchers = RegexUtil.getSplit(selectSql, SQL_REGEX);

        if(matchers.size() == 0){
            return matchers;
        }

        return matchers.stream().flatMap(ta -> {
            Matcher matcher = getMatcher(ta, toIgnoreCaseRegex("((as)|(from)|(join)|(where))\\s+"), Pattern.UNICODE_CASE);
            ta = matcher.replaceAll(" ").replaceAll("\\s+", " ");
            //先尝试根据逗号切割
            return Arrays.stream(ta.split(",")).filter(s -> (s != null) && s.trim().length() > 0).map(RegexUtil::getTable);
        }).collect(Collectors.toList());
    }

    /**
     * 看看一个表名会不会有别名，有就删除
     */
    private static String getTable(String t){
        return Arrays.stream(t.trim().split(" ")).filter(s -> (s != null) && s.trim().length() > 0).findFirst().orElse(null);
    }

}
