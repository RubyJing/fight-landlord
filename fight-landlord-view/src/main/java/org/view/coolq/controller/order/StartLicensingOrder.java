package org.view.coolq.controller.order;

import card.CardFactory;
import entity.Game;
import org.view.coolq.entity.Response;
import org.view.coolq.output.OutputInfo;
import service.ImageService;
import service.ImageServiceImpl;
import service.RobotService;
import service.RobotServiceImpl;

import java.util.ArrayList;

/**
 * 开局发牌
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/16 10:04
 */
public class StartLicensingOrder extends AbstractOrder implements Order {

    private RobotService robotService = new RobotServiceImpl();
    private ImageService imageService = new ImageServiceImpl();

    public StartLicensingOrder(Game game, long playerQq, String message) {
        super(game, playerQq, message);
    }


    @Override
    public void execute() {
        try {
            String order = getMessage();
            if ("licensing".equals(order)) {
                OutputInfo.messageQueue.put(new Response(game.getGroupId(),null,"===========开始发牌============"));
                OutputInfo.messageQueue.put(new Response(game.getGroupId(),null,"温馨提示：为了方便出牌，请根据牌后对应的键盘标识出牌"));
                game.setCards(new ArrayList<>(CardFactory.initCardPool));
                robotService.licensing(game);
                for (int i = game.getPlayers().size(); i > 0; i--) {
                    int index = i - 1;
                    game.getPlayers().get(index).setId(i);
                    OutputInfo.privateMessageQueue.put(new Response(game.getGroupId(),game.getPlayers().get(index).getQqNum()
                            , imageService.gameCardsImage(game.getPlayers().get(index).getCards())));
                }
                game.getGroupQueue().put("begin");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
