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
    private Boolean isPrivate;

    private ChooseRoleOrder chooseRoleOrder;
    private StartLicensingOrder startLicensingOrder;
    private SendCardOrder sendCardOrder;

    public Invoker(Boolean isPrivate, Game game, long playerQq, String message) {
        this.isPrivate = isPrivate;
        this.game = game;
        this.message = message;
        if (isPrivate) {
            sendCardOrder = new SendCardOrder(game, playerQq, message);
        } else {
            startLicensingOrder = new StartLicensingOrder(game, playerQq, message);
            chooseRoleOrder = new ChooseRoleOrder(game, playerQq, message);
        }
    }

    public void execute() {
        if (isPrivate) {
            sendCardOrder.execute();
        } else {
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
            }
        }
    }

    public Game getGame() {
        return game;
    }

}
