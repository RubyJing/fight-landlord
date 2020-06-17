package org.view.coolq.listener;

import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import entity.Game;
import org.view.coolq.controller.order.Invoker;
import org.view.coolq.game.GamePool;


/**
 * 内部读取线程
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/16 15:30
 */
public class ReadMessageTask implements Runnable {

    private Game game;

    public ReadMessageTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            String message = game.getGroupQueue().poll();
            if (message != null) {
                Invoker invoker = new Invoker(game, GamePool.eventGroupMessage.getSenderId(), message);
                invoker.execute();
            }
        }
    }
}
