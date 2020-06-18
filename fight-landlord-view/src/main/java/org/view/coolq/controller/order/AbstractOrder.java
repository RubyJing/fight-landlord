package org.view.coolq.controller.order;

import entity.Game;


/**
 * 命令
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 14:23
 */
public abstract class AbstractOrder implements Order {

    protected Game game;
    protected long playerQq;
    protected String message;

    public AbstractOrder(Game game, long playerQq, String message) {
        this.game = game;
        this.playerQq = playerQq;
        this.message = message;
    }

    public Game getGame() {
        return game;
    }

    public long getPlayerQq() {
        return playerQq;
    }

    public String getMessage() {
        return message;
    }
}
