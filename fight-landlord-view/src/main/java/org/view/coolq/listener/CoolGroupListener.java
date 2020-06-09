package org.view.coolq.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.User;
import lombok.extern.slf4j.Slf4j;

/**
 * 群聊/讨论组 信息监听
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 16:20
 */
@Slf4j
public class CoolGroupListener extends IcqListener {

    @EventHandler
    public void groupMessage(EventGroupMessage eventGroupMessage){
        User user = eventGroupMessage.getSender();
        log.debug("收到{}的群聊消息：{}",user.getId(),eventGroupMessage.message);
        eventGroupMessage.respond(eventGroupMessage.message);
    }

    @EventHandler
    public void groupMessage(EventDiscussMessage eventDiscussMessage){
        User user = eventDiscussMessage.getSender();
        log.debug("收到{}的群聊消息：{}",user.getId(),eventDiscussMessage.message);
        eventDiscussMessage.respond(eventDiscussMessage.message);
    }
}
