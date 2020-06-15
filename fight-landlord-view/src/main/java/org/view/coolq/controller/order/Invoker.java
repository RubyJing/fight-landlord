package org.view.coolq.controller.order;

import entity.Game;

/**
 * 命令模式-执行者
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 14:51
 */
public class Invoker {

    private Game game;
    private ChooseRoleOrder chooseRoleOrder;

    public Invoker(Game game,long playerQq) {
        this.game = game;
        chooseRoleOrder = new ChooseRoleOrder(game,playerQq);
    }

    public void execute() {
        chooseRoleOrder.execute();
    }

    public Game getGame() {
        return game;
    }

}
