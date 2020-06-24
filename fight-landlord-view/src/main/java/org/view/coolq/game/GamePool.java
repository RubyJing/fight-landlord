package org.view.coolq.game;

import cc.moecraft.icq.event.events.message.EventGroupMessage;
import entity.Game;
import entity.Player;

import java.util.ArrayList;
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
     * 群消息
     */
    public static EventGroupMessage eventGroupMessage;

    /**
     * 通过玩家qq号，获取玩家信息
     *
     * @param qqNum qq号
     * @return Game
     */
    public static Player getPlayerByPlayerQq(long qqNum) {
        if (games.size() != 0) {
            for (Game game : games) {
                for (Player player : game.getPlayers()) {
                    if (player.getQqNum() == qqNum) {
                        return player;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 通过玩家qq号，获取游戏对局
     *
     * @param qqNum qq号
     * @return Game
     */
    public static Game getGameByPlayerQq(long qqNum) {
        if (games.size() != 0) {
            for (Game game : games) {
                for (Player player : game.getPlayers()) {
                    if (player.getQqNum() == qqNum) {
                        return game;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 移除对局
     * @param game 游戏对局
     */
    public static void removeGame(Game game) {
        if (games != null && games.size() != 0) {
            if (games.iterator().hasNext()) {
                if (games.iterator().next().getGroupId().equals(game.getGroupId())) {
                    games.remove(game);
                }
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
            for (Game game : games) {
                if (game.getGroupId().equals(groupId)) {
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
        removeGame(game);
        assert games != null;
        games.add(game);
    }
}
