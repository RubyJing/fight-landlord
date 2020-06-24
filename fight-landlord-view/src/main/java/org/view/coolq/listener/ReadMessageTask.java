package org.view.coolq.listener;

import entity.Game;
import org.view.coolq.controller.order.Invoker;
import org.view.coolq.entity.PrivateMessage;
import org.view.coolq.game.GamePool;
import org.view.coolq.output.InputInfo;


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

            PrivateMessage privateMessage = InputInfo.messageQueue.poll();
            if (privateMessage != null) {
                Invoker invoker = new Invoker(privateMessage.getGame(), privateMessage.getPlayerQq(), privateMessage.getMessage());
                invoker.execute();
            }

        }
    }
}
