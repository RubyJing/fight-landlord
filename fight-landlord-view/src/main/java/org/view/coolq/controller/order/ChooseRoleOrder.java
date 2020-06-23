package org.view.coolq.controller.order;

import entity.Game;
import entity.Player;
import org.view.coolq.entity.Response;
import org.view.coolq.game.GamePool;
import org.view.coolq.output.OutputInfo;
import role.Farmer;
import service.ImageService;
import service.ImageServiceImpl;
import service.RobotService;
import service.RobotServiceImpl;

import java.util.List;

/**
 * 选择地主
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 14:20
 */
public class ChooseRoleOrder extends AbstractOrder implements Order {

    private RobotService robotService = new RobotServiceImpl();
    private ImageService imageService = new ImageServiceImpl();

    public ChooseRoleOrder(Game game, long playerQq, String message) {
        super(game, playerQq, message);
    }


    @Override
    public void execute() {
        try {
            String order = getMessage();
            List<Player> players = game.getPlayers();
            for (Player e : players) {
                if (e.getRole() == null) {
                    GamePool.addOrUpdateGameCurrPlayer(e, game.getGroupId());
                    break;
                }
            }
            Player currPlayer = GamePool.getCurrPlayer(game.getGroupId());
            //开头询问
            if ("begin".equals(order)) {
                assert currPlayer != null;
                OutputInfo.messageQueue.put(new Response(game.getGroupId(), super.playerQq,
                        currPlayer.getPlayerName() + "是否抢地主：请回答“是”或者“否”"));
            } else {
                boolean chooseRole = false;
                for (Player e : players) {
                    if (e.getRole() == null) {
                        chooseRole = true;
                        break;
                    }
                }
                assert currPlayer != null;
                if (chooseRole && playerQq == currPlayer.getQqNum()) {
                    if ("是".equals(order)) {
                        OutputInfo.messageQueue.put(new Response(game.getGroupId(), super.playerQq,
                                "显示最后三张牌：" + imageService.gameCardsImage(game.getCards(),false)));
                        robotService.getLandLord(game, super.playerQq);
                        imageService.addSubscript(currPlayer.getCards());
                        OutputInfo.messageQueue.put(new Response(game.getGroupId(), super.playerQq,
                                currPlayer.getPlayerName() + "成为地主"));
                        OutputInfo.messageQueue.put(new Response(game.getGroupId(), super.playerQq,
                               "等待地主出牌"));
                        OutputInfo.privateMessageQueue.put(new Response(game.getGroupId(), super.playerQq, "您的角色是地主："));
                        OutputInfo.privateMessageQueue.put(new Response(game.getGroupId(), super.playerQq, imageService.gameCardsImage(currPlayer.getCards(),true)));
                        OutputInfo.privateMessageQueue.put(new Response(game.getGroupId(), super.playerQq, "请输入键盘符号出牌(字母大写)"));
                    } else if ("否".equals(order)) {
                        currPlayer.setRole(new Farmer());
                        game.setNoChooseLandLord(game.getNoChooseLandLord() + 1);
                        if (game.getNoChooseLandLord() == 3) {
                            game.setNoChooseLandLord(0);
                            for (Player player : game.getPlayers()) {
                                player.setRole(null);
                            }
                            OutputInfo.messageQueue.put(new Response(game.getGroupId(), super.playerQq,
                                    "======没有人抢地主，对局结束，重新发牌====="));
                            game.getGroupQueue().put("licensing");
                        } else {
                            game.getGroupQueue().put("begin");
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
