package org.view.coolq.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.User;
import entity.Player;
import lombok.extern.slf4j.Slf4j;
import org.view.coolq.constant.RobotConstant;
import org.view.coolq.entity.Game;
import service.RobotService;

import java.util.ArrayList;
import java.util.List;

/**
 * 群聊/讨论组 信息监听
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 16:20
 */
@Slf4j
public class CoolGroupListener extends IcqListener {


    @EventHandler
    public void groupMessage(EventGroupMessage eventGroupMessage) {
        User user = eventGroupMessage.getSender();
        log.debug("收到{}的群聊消息：{}", user.getId(), eventGroupMessage.message);
        this.response(eventGroupMessage);
    }

    @EventHandler
    public void groupMessage(EventDiscussMessage eventDiscussMessage) {
        User user = eventDiscussMessage.getSender();
        log.debug("收到{}的群聊消息：{}", user.getId(), eventDiscussMessage.message);
        eventDiscussMessage.respond(eventDiscussMessage.message);
    }

    private void response(EventGroupMessage eventGroupMessage) {
        if (RobotConstant.START_FLAG.equals(eventGroupMessage.message)) {
            eventGroupMessage.respond("===========斗地主开始===========");
            eventGroupMessage.respond("想要加入的玩家请扣数字0");
        }
        List<Player> players = new ArrayList<>(3);
        if ("0".equals(eventGroupMessage.getMessage())) {
            Game game = new Game();
            game.setGroupId(eventGroupMessage.getGroupId());
            Player player = new Player();
            player.setId(eventGroupMessage.getSender().getId());
            player.setPlayerName(eventGroupMessage.getSender().getInfo().getNickname());
            players.add(player);
            if (players.size() == 1) {
                eventGroupMessage.respond("===========人数已满，对局开始===========");
//                eventGroupMessage.respond(robotService.initCard().toString());
            }
        }
    }
}
