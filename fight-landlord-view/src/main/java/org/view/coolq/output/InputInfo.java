package org.view.coolq.output;

import org.view.coolq.entity.PrivateMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 输入
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/23 10:06
 */
public class InputInfo {

    public static BlockingQueue<PrivateMessage> messageQueue = new LinkedBlockingQueue<>();

}
