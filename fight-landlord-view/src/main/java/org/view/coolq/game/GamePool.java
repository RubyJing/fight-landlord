package org.view.coolq.game;

import entity.Game;
import entity.Player;
import org.view.coolq.entity.GameCurrPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 对局缓存
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/11 15:20
 */
public class GamePool {
    /**
     * 对局缓存池
     */
    public static List<Game> games = new ArrayList<>();

    /**
     * 对局当前操作者缓存池
     */
    public static List<GameCurrPlayer> currPlayers = new ArrayList<>();

    /**
     * 获取当前群当前操作玩家
     *
     * @param groupId 群qq
     * @return GameCurrPlayer
     */
    public static Player getCurrPlayer(Long groupId) {
        if (currPlayers != null && currPlayers.size() != 0) {
            for (GameCurrPlayer currPlayer : currPlayers) {
                if (currPlayer.getGroupId() == groupId) {
                    return currPlayer.getCurrPlayer();
                }
            }
        }
        return null;
    }

    /**
     * 新增/修改 群对局当前操作玩家
     *
     * @param player  玩家
     * @param groupId 群qq
     */
    public static void addOrUpdateGameCurrPlayer(Player player, Long groupId) {
        if (currPlayers.size() == 0) {
            currPlayers.add(new GameCurrPlayer(groupId, player));
        } else {
            boolean isHave = false;
            for (GameCurrPlayer currPlayer : currPlayers) {
                if (currPlayer.getGroupId() == groupId) {
                    currPlayer.setCurrPlayer(player);
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                currPlayers.add(new GameCurrPlayer(groupId, player));
            }
        }
    }

    /**
     * 获取对局
     *
     * @param groupId 群Id
     * @return Game
     */
    public static Game getGame(Long groupId) {
        if (games != null && games.size() != 0) {
            Iterator<Game> gameIterator = games.iterator();
            while (gameIterator.hasNext()) {
                if (gameIterator.next().getGroupId().equals(groupId)) {
                    return games.iterator().next();
                }
            }
        }
        return null;
    }

    /**
     * 新增对局
     *
     * @param game 对局
     */
    public static void addGame(Game game) {
        if (games != null && games.size() != 0) {
            if (games.iterator().hasNext()) {
                if (games.iterator().next().getGroupId().equals(game.getGroupId())) {
                    games.remove(game);
                }
            }
        }
        assert games != null;
        games.add(game);
    }
}
