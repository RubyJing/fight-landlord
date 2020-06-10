package service;

import Sequence.CardSequence;
import Sequence.GameCardSequence;
import card.*;
import entity.Card;
import entity.CardType;
import entity.GameCardVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 14:42
 */
public class CardFactory {

    /**
     * 初始化常用卡
     *
     * @param cardTypeEnum 牌类型
     * @return GameCardVo
     */
    public List<GameCardVo> initGeneralCard(CardTypeEnum cardTypeEnum) {
        return this.initGameCardVoByType(this.findCardTypeById(cardTypeEnum));
    }

    /**
     * 通过类型id数组，获取对应类型
     *
     * @param cardTypeEnum 类型数组
     * @return CardType 的子类
     */
    private CardType findCardTypeById(CardTypeEnum cardTypeEnum) {
        CardType cardType;
        switch (cardTypeEnum) {
            case RED_PEACH:
                cardType = new RedPeachCard();
                break;
            case SPADES:
                cardType = new SpadesCard();
                break;
            case PLUM_FLOWER:
                cardType = new PlumFlowerCard();
                break;
            case SQUARE:
                cardType = new SquareCard();
                break;
            default:
                throw new IllegalArgumentException("不存在的卡牌类型");
        }
        return cardType;
    }

//    /**
//     * 初始化卡组
//     *
//     * @param cardTypeEnum 牌类型
//     * @return List<Card>
//     */
//    public List<Card> initCards(CardTypeEnum cardTypeEnum) {
//        switch (cardTypeEnum) {
//            case SPADES:
//                RED_PEACH:
//                PLUM_FLOWER:
//                SQUARE:
//                break;
//            default:
//        }
//    }

    private List<GameCardVo> initGameCardVoByType(CardType cardType) {
        if (cardType == null) {
            return null;
        }
        List<GameCardVo> gameCardVos = new ArrayList<>();
        List<Card> cards = this.generateNormalCard();
        for (Card card : cards) {
            GameCardVo gameCardVo = new GameCardVo();
            gameCardVo.setCardType(cardType);
            gameCardVo.setCard(card);
            gameCardVo.setId(GameCardSequence.getInstance());
            gameCardVos.add(gameCardVo);
        }
        return gameCardVos;
    }

    /**
     * 生成 3~A,2的牌
     *
     * @return
     */
    private List<Card> generateNormalCard() {
        int[] nums = new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 20};
        List<Card> cards = new ArrayList<>(13);
        for (int num : nums) {
            Card card = new Card();
            card.setId(CardSequence.getInstance());
            card.setCardNum(num);
            switch (num) {
                case 11:
                    card.setCardName("J");
                    break;
                case 12:
                    card.setCardName("Q");
                    break;
                case 13:
                    card.setCardName("K");
                    break;
                case 14:
                    card.setCardName("A");
                    break;
                case 20:
                    card.setCardName("2");
                    break;
                default:
                    card.setCardName(String.valueOf(num));
            }
            cards.add(card);
        }
        return cards;
    }


}
