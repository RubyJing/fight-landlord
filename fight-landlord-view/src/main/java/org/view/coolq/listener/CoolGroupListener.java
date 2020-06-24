package org.view.coolq.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RLoginInfo;
import cc.moecraft.icq.user.User;
import entity.Player;
import lombok.extern.slf4j.Slf4j;
import org.view.coolq.constant.RobotConstant;
import entity.Game;
import org.view.coolq.game.GamePool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 群聊/讨论组 信息监听
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 16:20
 */
@Slf4j
public class CoolGroupListener extends IcqListener {

    private List<Game> games = new ArrayList<>();
    private String robotName;


    public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @EventHandler
    public void groupMessage(EventGroupMessage message) {
        User user = message.getSender();
        GamePool.eventGroupMessage = message;
        log.info("收到{}群{}消息：{}", message.getGroup().getInfo(), user.getInfo(), message.message);
        this.response(message);
    }


    @EventHandler
    public void groupMessageStart(EventDiscussMessage message) {
        User user = message.getSender();
        log.info("收到{}群{}消息：{}", message.getGroup().getInfo(), user.getInfo(), message.message);
        message.respond(message.message);
    }

    private void response(EventGroupMessage eventGroupMessage) {
        this.responseAlert(eventGroupMessage);
        this.responseStart(eventGroupMessage);
        this.joinGame(eventGroupMessage);
        this.checkBegin(eventGroupMessage);
    }

    /**
     * 群机器人提示
     */
    private void responseAlert(EventGroupMessage eventGroupMessage) {
        RLoginInfo returnPojoBase = eventGroupMessage.getHttpApi().getLoginInfo().getData();
        if (robotName == null) {
            robotName = returnPojoBase.getNickname();
        }
        String message = eventGroupMessage.message;
        String at = "[CQ:at,qq=" + returnPojoBase.getUserId() + "]";
        if (message.equals("@" + robotName) || message.equals(at)) {
            eventGroupMessage.respond("你好，我是斗地主机器人。在群里发送“斗地主启动”五个字，可以开启斗地主游戏哦！");
        }
    }

    /**
     * 群游戏提示启动
     */
    private void responseStart(EventGroupMessage eventGroupMessage) {
        if (RobotConstant.START_FLAG.equals(eventGroupMessage.message)) {
            Game game = GamePool.getGame(eventGroupMessage.getGroupId());
            if (game == null) {
                game = new Game();
                game.setGroupId(eventGroupMessage.getGroupId());
                eventGroupMessage.respond("==========斗地主程序启动==========");
                eventGroupMessage.respond("想要加入的玩家请扣数字0");
                games.add(game);
            } else {
                eventGroupMessage.respond("抱歉，该群已存在对局,无法创建新对局！");
            }
        }

    }

    /**
     * 游戏成员加入
     */
    private void joinGame(EventGroupMessage eventGroupMessage) {
        if ("0".equals(eventGroupMessage.getMessage())) {
            if (games != null && games.size() != 0) {
                //判断玩家是否已在对局中
                if (GamePool.getPlayerByPlayerQq(eventGroupMessage.getSender().getId()) == null) {
                    for (Game game : games) {
                        if (game.getGroupId().equals(eventGroupMessage.getGroupId())) {
                            List<Player> players = game.getPlayers();
                            Player player = new Player();
                            player.setQqNum(eventGroupMessage.getSender().getId());
                            player.setPlayerName(eventGroupMessage.getSender().getInfo().getNickname());
                            players.add(player);
                            if (players.size() == 3) {
                                try {
                                    game.getGroupQueue().put("licensing");
                                    eventGroupMessage.respond("人数已满，对局开始");
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                game.setIsStart(true);
                                GamePool.addGame(game);
                            }
                            if (players.size() > 3) {
                                eventGroupMessage.respond("抱歉，游戏人数已满，无法加入对局");
                                return;
                            }
                        }
                    }
                } else {
                    eventGroupMessage.respond("抱歉，" + eventGroupMessage.getSender().getInfo().getNickname() + "已加入其他对局无法重复加入");
                }
            }
        }
    }

    private void checkBegin(EventGroupMessage eventGroupMessage) {
        long groupId = eventGroupMessage.getGroup().getId();
        for (Game game : GamePool.games) {
            if (groupId == game.getGroupId() && game.getIsStart()) {
                if (game.getThread() == 0) {
                    cachedThreadPool.execute(new MessageWriteTask(eventGroupMessage));
                    cachedThreadPool.execute(new ReadMessageTask(game));
                    game.setThread(2);
                }
                if (!"0".equals(eventGroupMessage.message)) {
                    try {
                        game.getGroupQueue().put(eventGroupMessage.message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
