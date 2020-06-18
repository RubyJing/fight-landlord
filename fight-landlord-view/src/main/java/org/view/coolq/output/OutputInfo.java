package org.view.coolq.output;


import org.view.coolq.entity.Response;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 统一返回输出
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 14:48
 */
public class OutputInfo {

    public static BlockingQueue<Response> messageQueue = new LinkedBlockingQueue<>();

    public static BlockingQueue<Response> privateMessageQueue = new LinkedBlockingQueue<>();


}
