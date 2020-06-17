package service;

import entity.Game;
import entity.GameCardVo;
import entity.Player;
import lombok.extern.slf4j.Slf4j;
import role.Farmer;
import role.LandLord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 13:59
 */
@Slf4j
public class RobotServiceImpl implements RobotService {

    @Override
    public void licensing(Game game) {
        int gap = (game.getCards().size() - 3) / game.getPlayers().size();
        for (Player player : game.getPlayers()) {
            List<GameCardVo> cards = new ArrayList<>();
            for (int i = 0; i < gap; i++) {
                int randomNum = new Random().nextInt(game.getCards().size());
                cards.add(game.getCards().get(randomNum));
                game.getCards().remove(randomNum);
            }
            this.sortAscByNum(cards);
            player.setCards(cards);
        }
    }

    @Override
    public void getLandLord(Game game, long playerQq) {
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            if (player.getQqNum() == playerQq) {
                player.setRole(new LandLord());
                player.getCards().addAll(game.getCards());
                this.sortAscByNum(player.getCards());
            } else {
                player.setRole(new Farmer());
            }
        }
    }

    /**
     * 牌组通过数值排序
     *
     * @param gameCardVos 牌组voList
     */
    private void sortAscByNum(List<GameCardVo> gameCardVos) {
        gameCardVos.sort(Comparator.comparingInt(a -> a.getCard().getCardNum()));
    }


}
