package org.view.coolq.listener;

import card.CardFactory;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RLoginInfo;
import cc.moecraft.icq.user.User;
import entity.Player;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.view.coolq.constant.RobotConstant;
import entity.Game;
import org.view.coolq.controller.order.Invoker;
import org.view.coolq.game.GamePool;
import org.view.coolq.output.OutputInfo;
import service.ImageService;
import service.ImageServiceImpl;
import service.RobotService;
import service.RobotServiceImpl;

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

    private RobotService robotService = new RobotServiceImpl();
    private ImageService imageService = new ImageServiceImpl();

    public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @EventHandler
    public void groupMessage(EventGroupMessage message) {
        User user = message.getSender();
        log.debug("收到{}群{}消息：{}", message.getGroup().getInfo(), user.getInfo(), message.message);
        this.response(message);
    }


    @EventHandler
    public void groupMessageStart(EventDiscussMessage message) {
        User user = message.getSender();
        log.debug("收到{}群{}消息：{}", message.getGroup().getInfo(), user.getInfo(), message.message);
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
                for (Game game : games) {
                    if (game.getGroupId().equals(eventGroupMessage.getGroupId())) {
                        List<Player> players = game.getPlayers();
                        Player player = new Player();
                        player.setQqNum(eventGroupMessage.getSender().getId());
                        player.setPlayerName(eventGroupMessage.getSender().getInfo().getNickname());
                        players.add(player);
                        if (players.size() == 3) {
                            game.getGroupQueue().add("begin");
                            game.setIsStart(true);
                            GamePool.addGame(game);
                            eventGroupMessage.respond("======人数已满，对局开始=======");
                            eventGroupMessage.respond("===========开始发牌============");
                            eventGroupMessage.respond("温馨提示：为了方便出牌，请根据牌后对应的键盘标识出牌");
                            game.setCards(new ArrayList<>(CardFactory.initCardPool));
                            robotService.licensing(game);
                            for (int i = game.getPlayers().size(); i > 0; i--) {
                                int index = i - 1;
                                game.getPlayers().get(index).setId(i);
                                eventGroupMessage.getHttpApi().sendPrivateMsg(game.getPlayers().get(index).getQqNum()
                                        , imageService.gameCardsImage(game.getPlayers().get(index).getCards()));
                            }
                            return;
                        }
                        if (players.size() > 3) {
                            eventGroupMessage.respond("抱歉，游戏人数已满，无法加入对局");
                            return;
                        }
                    }
                }
            }
        }
    }

    private void checkBegin(EventGroupMessage eventGroupMessage){
        long groupId = eventGroupMessage.getGroup().getId();
        for (Game game : GamePool.games) {
            if (groupId == game.getGroupId() && game.getIsStart()) {
                game.getGroupQueue().add(eventGroupMessage.message);
                Invoker invoker = new Invoker(game,eventGroupMessage.getSender().getId());
                invoker.execute();
                if (game.getThread() == 0){
                    cachedThreadPool.execute(new ReadMessageWriteTask(eventGroupMessage));
                }
            }
        }
    }



}
