package com.forte.utils.thread.threadutil.Exception;

/**
 * @author ForteScarlet
 * <div>
 * <ul>
 * <li> ---><a href='https://gitee.com/ForteScarlet' target='_block'>码云个人地址</a></li>
 * </ul>
 * </div>
 */
public class NoThreadForParallelException extends RuntimeException{

    public NoThreadForParallelException(){
        super("线程任务列表为空！");
    }

}
