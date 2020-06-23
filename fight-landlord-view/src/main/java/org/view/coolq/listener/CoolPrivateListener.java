package org.view.coolq.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.User;
import entity.Game;
import entity.Player;
import lombok.extern.slf4j.Slf4j;
import org.view.coolq.entity.PrivateMessage;
import org.view.coolq.game.GamePool;
import org.view.coolq.output.InputInfo;


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
    public void privateMessage(EventPrivateMessage eventPrivateMessage) throws InterruptedException {
        User user = eventPrivateMessage.getSender();
        log.debug("收到{}的私聊消息：{}", user.getId(), eventPrivateMessage.message);
        Game game = GamePool.getGameByPlayerQq(user.getId());
        if (game != null) {
            InputInfo.messageQueue.put(new PrivateMessage(game, user.getId(), eventPrivateMessage.message));
        }
    }

}
