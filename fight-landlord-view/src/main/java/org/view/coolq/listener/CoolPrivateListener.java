package org.view.coolq.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import lombok.extern.slf4j.Slf4j;


/**
 * 私聊监听
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/8 14:34
 */
@Slf4j
public class CoolPrivateListener extends IcqListener {

    @EventHandler
    public void privateMessage(EventPrivateMessage eventPrivateMessage) {
        log.debug("收到私聊消息：{}",eventPrivateMessage.message);
        eventPrivateMessage.respond(eventPrivateMessage.message);
    }
}
