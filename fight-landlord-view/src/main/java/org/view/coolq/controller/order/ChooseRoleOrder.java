package org.view.coolq.controller.order;

import entity.Game;
import entity.Player;
import org.view.coolq.output.OutputInfo;
import role.Farmer;
import service.RobotService;
import service.RobotServiceImpl;

import java.util.List;

/**
 * 选择地主
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 14:20
 */
public class ChooseRoleOrder extends AbstractOrder implements Order {

    private RobotService robotService = new RobotServiceImpl();

    public ChooseRoleOrder(Game game, long playerQq) {
        super(game, playerQq);
    }

    @Override
    public void execute() {
        try {
            String order = getOrder();
            List<Player> players = game.getPlayers();
            boolean chooseRole = true;
            Player player = null;
            for (Player e : players) {
                if (e.getRole() == null) {
                    chooseRole = false;
                    player = e;
                    break;
                }
            }
            if ("begin".equals(order) || !chooseRole) {
                assert player != null;
                if ("是".equals(order)) {
                    robotService.getLandLord(game,super.playerQq);
                } else if ("否".equals(order)) {
                    player.setRole(new Farmer());
                }
                OutputInfo.messageQueue.put(player.getPlayerName() + "是否抢地主:请回答“是”或者“否”");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
