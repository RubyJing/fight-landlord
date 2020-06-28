package org.view.coolq.controller.order;

import entity.Game;
import entity.GameCardVo;
import entity.Player;
import org.view.coolq.entity.PrivateMessage;
import org.view.coolq.entity.Response;
import org.view.coolq.game.GamePool;
import org.view.coolq.listener.CoolGroupListener;
import org.view.coolq.output.InputInfo;
import org.view.coolq.output.OutputInfo;
import service.*;

import java.util.List;

/**
 * 出牌命令
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/23 10:43
 */
public class SendCardOrder extends AbstractOrder implements Order {

    private PlayService playService = new PlayServiceImpl();
    private ImageService imageService = new ImageServiceImpl();

    public SendCardOrder(Game game, long playerQq, String message) {
        super(game, playerQq, message);
    }

    @Override
    public void execute() {
        String message = getMessage().toUpperCase();
        Player player = GamePool.getPlayerByPlayerQq(playerQq);
        assert player != null;
        try {
            if ("Z".equals(message)
                    || "CONTINUE".equals(message)
                    || playService.isKeyBoardsInputLegal(game, playService.getAllNotHitCards(player.getCards()), message)) {
                //当前已出牌玩家
                Player currPlayer = game.getCurrPlayer();
                //游戏询问玩家(当前操作玩家)
                Player gameCurrPlayer;
                //当局地主
                Player landLoad = playService.getGameLandLord(game);
                if (currPlayer == null) {
                    gameCurrPlayer = playService.gameCurrPlayer(game, playService.getGameLandLord(game));
                } else {
                    currPlayer = game.getCurrPlayer();
                    gameCurrPlayer = playService.gameCurrPlayer(game, null);
                }

                if (currPlayer == null && landLoad.getQqNum() == playerQq) {
                    //地主首先出牌
                    sendCards(landLoad);
                    askWhetherToPlayCards(gameCurrPlayer);
                } else if (currPlayer != null) {
                    if (playerQq == gameCurrPlayer.getQqNum()) {
                        if ("Z".equals(message) && gameCurrPlayer.getQqNum() == playerQq) {
                            this.noSendCards(player, currPlayer, gameCurrPlayer);
                        } else {
                            if (!sendCards(gameCurrPlayer)) {
                                InputInfo.messageQueue.put(new PrivateMessage(game, gameCurrPlayer.getQqNum(), "CONTINUE"));
                            } else {
                                //对局结束
                                game.setIsStart(false);
                                GamePool.removeGame(game);
                            }
                        }
                    }
                    if ("CONTINUE".equals(message)) {
                        askWhetherToPlayCards(gameCurrPlayer);
                    }
                }
            } else {
                OutputInfo.privateMessageQueue.put(new Response(game.getGroupId(), playerQq
                        , "输入不合法"));
                this.showPlayerHaveCards(player);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不出牌
     */
    private void noSendCards(Player player, Player currPlayer, Player gameCurrPlayer) throws InterruptedException {
        playService.noSendCard(game);
        OutputInfo.messageQueue.put(new Response(game.getGroupId(), null,
                player.getPlayerName() + "(" + player.getRole().getRoleName() + ")选择不要"));
        this.showPlayerHaveCards(player);
        if (game.getNoSendCardCount() == 2) {
            game.setCurrCard(null);
            game.setCurrSendCardType(null);
            OutputInfo.messageQueue.put(new Response(game.getGroupId(), null,
                    "玩家" + currPlayer.getPlayerName() + "(" + currPlayer.getRole().getRoleName() + ")请出牌"));
        } else {
            InputInfo.messageQueue.put(new PrivateMessage(game, gameCurrPlayer.getQqNum(), "CONTINUE"));
        }
    }

    /**
     * 出牌
     */
    private boolean sendCards(Player player) throws InterruptedException {
        String deskCard = playService.playerSendCard(game, playerQq, message);
        List<GameCardVo> gameCardVos = playService.getAllNotHitCards(player.getCards());
        OutputInfo.messageQueue.put(new Response(game.getGroupId(), null,
                player.getPlayerName() + "(" + player.getRole().getRoleName() + ")出牌--剩" + gameCardVos.size() + "张：\n"
                        + deskCard));
        boolean isEnd = false;
        if (gameCardVos.size() == 0) {
            isEnd = true;
            OutputInfo.messageQueue.put(new Response(game.getGroupId(), null,
                    "=========对局结束," + player.getRole().getRoleName() + "获得胜利========"));
            OutputInfo.messageQueue.put(new Response(game.getGroupId(), null,
                    "=========斗地主程序关闭。可通过输入“斗地主启动”再次唤醒游戏========="));
            CoolGroupListener.removeGame(game.getGroupId());
        } else {
            this.showPlayerHaveCards(player);
        }
        return isEnd;
    }

    /**
     * 询问是否出牌
     */
    private void askWhetherToPlayCards(Player gameCurrPlayer) throws InterruptedException {
        OutputInfo.messageQueue.put(new Response(game.getGroupId(), null,
                "正在等待玩家" + gameCurrPlayer.getPlayerName() + "(" + gameCurrPlayer.getRole().getRoleName() + ")" + "出牌"));
        OutputInfo.privateMessageQueue.put(new Response(game.getGroupId(), gameCurrPlayer.getQqNum()
                , "是否出牌 ? 不出牌输入Z，如出牌请根据键盘按键出牌："));
    }

    /**
     * 展示玩家手牌
     */
    private void showPlayerHaveCards(Player player) throws InterruptedException {
        OutputInfo.privateMessageQueue.put(new Response(game.getGroupId(), playerQq
                , "您的手牌：\n" + imageService.sendCardImage(playService.getAllNotHitCards(player.getCards()))));
    }
}
