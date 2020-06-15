package org.view.coolq.controller.order;

import entity.Game;


import java.util.concurrent.BlockingQueue;

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

    protected String getOrder() throws InterruptedException {
        BlockingQueue<String> blockingQueue = game.getGroupQueue();
        return blockingQueue.take();
    }

    public AbstractOrder(Game game,long playerQq) {
        this.game = game;
        this.playerQq = playerQq;
    }
}
