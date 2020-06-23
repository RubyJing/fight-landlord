package org.view.coolq.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;

/**
 * 异常处理
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/16 11:38
 */
public class EventLocalException extends IcqListener {

    @EventHandler
    private void printException(cc.moecraft.icq.event.events.local.EventLocalException e) {
        e.getException().printStackTrace();
    }
}
