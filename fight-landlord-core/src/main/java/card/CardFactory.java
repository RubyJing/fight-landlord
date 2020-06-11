package card;

import Sequence.CardSequence;
import Sequence.GameCardSequence;
import card.*;
import entity.Card;
import entity.CardType;
import entity.GameCardVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡工厂类
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 14:42
 */
public class CardFactory {

    public CardFactory() {
        initCardPool = initCard();
    }

    /**
     * 游戏初始化牌组
     */
    public static List<GameCardVo> initCardPool = new ArrayList<>(54);


    public List<GameCardVo> initCard() {
        List<GameCardVo> gameCardVos = new ArrayList<>();
        gameCardVos.addAll(this.initGeneralCard(CardTypeEnum.RED_PEACH));
        gameCardVos.addAll(this.initGeneralCard(CardTypeEnum.SPADES));
        gameCardVos.addAll(this.initGeneralCard(CardTypeEnum.PLUM_FLOWER));
        gameCardVos.addAll(this.initGeneralCard(CardTypeEnum.SQUARE));
        gameCardVos.addAll(this.initQueenCard());
        return gameCardVos;
    }

    /**
     * 初始化王卡
     *
     * @return List<GameCardVo>
     */
    public List<GameCardVo> initQueenCard() {
        CardType cardType = this.findCardTypeById(CardTypeEnum.Queen);
        return this.initGameCardVoByType(cardType);
    }

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
            case Queen:
                cardType = new QueenCard();
                break;
            default:
                throw new IllegalArgumentException("不存在的卡牌类型");
        }
        return cardType;
    }


    private List<GameCardVo> initGameCardVoByType(CardType cardType) {
        if (cardType == null) {
            return null;
        }
        List<GameCardVo> gameCardVos = new ArrayList<>();
        List<Card> cards;
        if ("王".equals(cardType.getCardType())) {
            cards = this.generateNormalCard(true);
        } else {
            cards = this.generateNormalCard(false);
        }
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
     * 生成卡牌牌
     *
     * @return
     */
    private List<Card> generateNormalCard(boolean isQueen) {
        int[] nums;
        if (isQueen) {
            nums = new int[]{100, 101};
        } else {
            nums = new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 20};
        }
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
                case 100:
                    card.setCardName("小");
                    break;
                case 101:
                    card.setCardName("大");
                    break;
                default:
                    card.setCardName(String.valueOf(num));
            }
            cards.add(card);
        }
        return cards;
    }


}
