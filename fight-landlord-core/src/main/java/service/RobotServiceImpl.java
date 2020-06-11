package service;

import card.CardTypeEnum;
import entity.Card;
import entity.Game;
import entity.GameCardVo;
import entity.Player;
import lombok.extern.slf4j.Slf4j;

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

    CardFactory cardFactory = new CardFactory();

    @Override
    public List<GameCardVo> initCard() {
        List<GameCardVo> gameCardVos = new ArrayList<>();
        gameCardVos.addAll(cardFactory.initGeneralCard(CardTypeEnum.RED_PEACH));
        gameCardVos.addAll(cardFactory.initGeneralCard(CardTypeEnum.SPADES));
        gameCardVos.addAll(cardFactory.initGeneralCard(CardTypeEnum.PLUM_FLOWER));
        gameCardVos.addAll(cardFactory.initGeneralCard(CardTypeEnum.SQUARE));
        gameCardVos.addAll(cardFactory.initQueenCard());
        return gameCardVos;
    }

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
            this.sortAscWithSubscriptByNum(cards);
            player.setCards(cards);
        }
    }

    /**
     * 牌组通过数值排序，并标识下标
     * @param gameCardVos 牌组voList
     */
    private void sortAscWithSubscriptByNum(List<GameCardVo> gameCardVos){
        gameCardVos.sort(Comparator.comparingInt(a -> a.getCard().getCardNum()));
        String[] keyBoards = new String[]{"1","2","3","4","5","6","7","8","9","0","Q","W","E","R","T","Y","U","I","O","P"};
        for (int i = 0; i < gameCardVos.size(); i++) {
            gameCardVos.get(i).setSubscript(keyBoards[i]);
        }
    }




}
