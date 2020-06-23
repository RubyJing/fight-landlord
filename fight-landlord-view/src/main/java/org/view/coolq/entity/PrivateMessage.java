package org.view.coolq.entity;

import entity.Game;

/**
 * 游戏私人信息
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/23 9:51
 */
public class PrivateMessage {

    private Game game;

    private long playerQq;

    private String message;

    public PrivateMessage(Game game, long playerQq, String message) {
        this.game = game;
        this.playerQq = playerQq;
        this.message = message;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public long getPlayerQq() {
        return playerQq;
    }

    public void setPlayerQq(long playerQq) {
        this.playerQq = playerQq;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
