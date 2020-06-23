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
    private String message;

    private ChooseRoleOrder chooseRoleOrder;
    private StartLicensingOrder startLicensingOrder;
    private SendCardOrder sendCardOrder;

    public Invoker(Game game, long playerQq, String message) {
        this.game = game;
        this.message = message;
        startLicensingOrder = new StartLicensingOrder(game, playerQq, message);
        chooseRoleOrder = new ChooseRoleOrder(game, playerQq, message);
        sendCardOrder = new SendCardOrder(game,playerQq,message);
    }

    public void execute() {
        switch (message) {
            case "licensing":
                startLicensingOrder.execute();
                break;
            case "begin":
            case "是":
            case "否":
                chooseRoleOrder.execute();
                break;
            default:
                sendCardOrder.execute();
        }
    }

    public Game getGame() {
        return game;
    }

}
