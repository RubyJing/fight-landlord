package service;

import card.SendCardType;
import entity.Card;
import entity.GameCardVo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 出牌工厂
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/22 10:29
 */
public class SendCardFactory {

    /**
     * 是否能出牌
     *
     * @param sendCardType     出牌类型
     * @param sendCards        出牌
     * @param currGameCards    当前游戏卡牌
     * @param currSendCardType 当前游戏卡牌类型
     * @return boolean
     */
    public boolean isSendCard(SendCardType sendCardType, List<Card> sendCards, List<GameCardVo> currGameCards, SendCardType currSendCardType) {
        List<Card> cards = currGameCards.stream().map(GameCardVo::getCard).collect(Collectors.toList());
        //场上出了王炸，其他牌都不能出
        if (currSendCardType == SendCardType.TWO_QUEEN_BOMB) {
            return false;
        }
        switch (sendCardType) {
            case TWO_QUEEN_BOMB:
                return true;
            case FOUR_BOMB:
                return sendFourBomb(sendCards, cards, currSendCardType);
            case ONE:
            case TWO_PAIR:
            case THREE_SAME:
                return sendSame(sendCardType, sendCards, cards, currSendCardType);
            case THREE_WITH_ONE:
            case THREE_WITH_TWO:
            case FOUR_BOMB_WITH_ONE:
            case FOUR_BOMB_WITH_TWO:
                return sendMoreSameWith(sendCardType, sendCards, cards, currSendCardType);
            case MORE_ONE:
            case MORE_TWO_PAIR:
            case MORE_THREE:
                return sendStraight(sendCardType, sendCards, cards, currSendCardType);
            case MORE_THREE_WITH:
                return sendMoreThreeWith(sendCards, cards, currSendCardType);
            default:
                return false;
        }
    }

    /**
     * 打出飞机带翅膀
     *
     * @param sendCards        出的牌
     * @param currCards        场上的牌
     * @param currSendCardType 场上出牌的类型
     * @return boolean
     */
    private boolean sendMoreThreeWith(List<Card> sendCards, List<Card> currCards, SendCardType currSendCardType) {
        if (currSendCardType != SendCardType.MORE_THREE_WITH) {
            return false;
        }
        Map<Integer, Integer> currMap = new HashMap<>((int) (currCards.size() / 0.75) + 1);
        for (Card currCard : currCards) {
            int count = 0;
            for (int i = 0; i < currCards.size(); i++) {
                count++;
            }
            currMap.put(currCard.getCardNum(), count);
        }
        List<Map.Entry<Integer, Integer>> currThreeList = currMap.entrySet().stream().filter(e -> e.getValue() == 3).sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());

        Map<Integer, Integer> sendMap = new HashMap<>((int) (sendCards.size() / 0.75) + 1);
        for (Card currCard : sendCards) {
            int count = 0;
            for (int i = 0; i < sendCards.size(); i++) {
                count++;
            }
            sendMap.put(currCard.getCardNum(), count);
        }
        List<Map.Entry<Integer, Integer>> sendThreeList = sendMap.entrySet().stream().filter(e -> e.getValue() == 3).sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());

        if (currThreeList.size() != sendThreeList.size()) {
            return false;
        }

        if (currThreeList.get(0).getKey() > sendThreeList.get(0).getKey()) {
            return false;
        }

        for (Map.Entry<Integer, Integer> curr : currMap.entrySet()) {
            for (Map.Entry<Integer, Integer> send : sendMap.entrySet()) {
                if (curr.getValue().equals(send.getValue()) && send.getValue() == 1) {
                    return true;
                }
                if (curr.getValue().equals(send.getValue()) && send.getValue() == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 打出顺子（单顺，双顺（连对），三顺）
     *
     * @param sendCardType     出牌类型
     * @param sendCards        出的牌
     * @param currCards        场上的牌
     * @param currSendCardType 场上出牌的类型
     * @return boolean
     */
    private boolean sendStraight(SendCardType sendCardType, List<Card> sendCards, List<Card> currCards, SendCardType currSendCardType) {
        if (sendCardType != currSendCardType) {
            return false;
        }
        List<Card> sendCardsDistinctAndSort = distinctAndSort(sendCards);
        List<Card> currCardsDistinctAndSort = distinctAndSort(currCards);

        if (sendCardsDistinctAndSort.size() != currCardsDistinctAndSort.size()) {
            return false;
        }

        return sendCardsDistinctAndSort.get(0).getCardNum() > currCardsDistinctAndSort.get(0).getCardNum();
    }

    /**
     * 打出多带1/2--三带一，三带一对，四带一，四带一对
     *
     * @param sendCardType     出牌类型
     * @param sendCards        出的牌
     * @param currCards        场上的牌
     * @param currSendCardType 场上出牌的类型
     * @return boolean
     */
    private boolean sendMoreSameWith(SendCardType sendCardType, List<Card> sendCards, List<Card> currCards, SendCardType currSendCardType) {
        if (sendCardType != currSendCardType) {
            return false;
        }
        //牌局
        Map<Integer, Integer> currCount = new HashMap<>((int) (currCards.size() / 0.75) + 1);
        for (Card currCard : currCards) {
            int count = 0;
            for (Card card : currCards) {
                if (currCard.getCardNum() == card.getCardNum()) {
                    count++;
                }
            }
            currCount.put(currCard.getCardNum(), count);
        }
        Set<Map.Entry<Integer, Integer>> currEntrySet = currCount.entrySet();
        List<Integer> currFirst = currEntrySet.stream()
                .sorted((Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) -> e2.getValue() - e1.getValue())
                .map(Map.Entry::getKey).collect(Collectors.toList());

        //出牌
        Map<Integer, Integer> sendCardCount = new HashMap<>((int) (sendCards.size() / 0.75) + 1);
        for (Card card : sendCards) {
            int count = 0;
            for (Card sendCard2 : sendCards) {
                if (card.getCardNum() == sendCard2.getCardNum()) {
                    count++;
                }
            }
            sendCardCount.put(card.getCardNum(), count);
        }
        Set<Map.Entry<Integer, Integer>> sendEntrySet = sendCardCount.entrySet();
        List<Integer> sendFirst = sendEntrySet.stream()
                .sorted((Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) -> e2.getValue() - e1.getValue())
                .map(Map.Entry::getKey).collect(Collectors.toList());

        return sendFirst.get(0) > currFirst.get(0);
    }

    /**
     * 打出四炸
     *
     * @param sendCards        出的牌
     * @param currCards        场上的牌
     * @param currSendCardType 场上出牌的类型
     * @return boolean
     */
    private boolean sendFourBomb(List<Card> sendCards, List<Card> currCards, SendCardType currSendCardType) {
        if (currSendCardType == SendCardType.FOUR_BOMB) {
            return currCards.get(0).getCardNum() <= sendCards.get(0).getCardNum();
        }
        return true;
    }

    /**
     * 出的同样的牌
     *
     * @param sendCardType     出牌类型
     * @param sendCards        出的牌
     * @param currCards        场上的牌
     * @param currSendCardType 场上出牌的类型
     * @return boolean
     */
    private boolean sendSame(SendCardType sendCardType, List<Card> sendCards, List<Card> currCards, SendCardType currSendCardType) {
        if (sendCardType != currSendCardType) {
            return false;
        }
        return currCards.get(0).getCardNum() < sendCards.get(0).getCardNum();
    }


    /**
     * 出牌类型
     *
     * @param cards 卡牌
     * @return 类型
     */
    public SendCardType isFightLandlordCardRule(List<Card> cards) {
        //无牌
        if (cards == null || cards.size() == 0) {
            return null;
        }
        //排序
        cards = cards.stream().sorted(Comparator.comparingInt(Card::getCardNum)).collect(Collectors.toList());
        //一张牌
        if (cards.size() == SendCardType.ONE.getCardNum()) {
            return SendCardType.ONE;
        }

        //两张牌：对子，大小王
        if (cards.size() == SendCardType.TWO_QUEEN_BOMB.getCardNum()) {
            if (cards.get(0).getCardNum() > 20 && cards.get(1).getCardNum() > 20) {
                //大小王
                return SendCardType.TWO_QUEEN_BOMB;
            } else {
                if (cards.get(0).getCardNum() == cards.get(1).getCardNum()) {
                    return SendCardType.TWO_PAIR;
                }
            }
        }

        //三张牌：三个一样
        if (cards.size() == SendCardType.THREE_SAME.getCardNum()) {
            if (cards.get(0).getCardNum() == cards.get(1).getCardNum()
                    && cards.get(1).getCardNum() == cards.get(2).getCardNum()) {
                return SendCardType.THREE_SAME;
            }

        }

        //四张牌：四炸，三带一
        if (cards.size() == SendCardType.FOUR_BOMB.getCardNum()) {
            int sameSum = this.sameSumInCards(cards);
            if (sameSum == SendCardType.FOUR_BOMB.getCardNum() - 1) {
                return SendCardType.FOUR_BOMB;
            }
            if (isThreeWithOne(cards)) {
                return SendCardType.THREE_WITH_ONE;
            }
        }

        //五张牌：三带一对，四带一
        if (cards.size() == SendCardType.FOUR_BOMB_WITH_ONE.getCardNum()) {
            if (isThreeWithTwo(cards)) {
                return SendCardType.THREE_WITH_TWO;
            }
            if (sameSumInCards(cards) == SendCardType.FOUR_BOMB.getCardNum() - 1) {
                return SendCardType.FOUR_BOMB_WITH_ONE;
            }
        }

        //六张牌:四带二
        if (cards.size() == SendCardType.FOUR_BOMB_WITH_TWO.getCardNum()) {
            if (isFourBombWithTwo(cards)) {
                return SendCardType.FOUR_BOMB_WITH_TWO;
            }
        }

        //顺子：大于五张牌，小于13张牌
        if (cards.size() >= 5 && cards.size() <= 12) {
            if (isStraight(cards)) {
                return SendCardType.MORE_ONE;
            }
        }

        //顺子：连对(三个或三个以上）; 三顺-飞机（起码2个）
        if (cards.size() > SendCardType.MORE_TWO_PAIR.getCardNum()) {
            if (this.isStraightDouble(cards)) {
                return SendCardType.MORE_TWO_PAIR;
            }
            if (this.isStraightThree(cards)) {
                return SendCardType.MORE_THREE;
            }
        }

        //飞机带翅膀：三顺＋同数量的单牌（或同数量的对牌）
        if (cards.size() > SendCardType.MORE_THREE_WITH.getCardNum()) {
            if (isMoreThreeWith(cards)) {
                return SendCardType.MORE_THREE_WITH;
            }

        }

        return null;
    }


    /**
     * 是否是 飞机带翅膀(三顺带1个/1对)
     *
     * @param cards 出牌
     * @return boolean
     */
    protected boolean isMoreThreeWith(List<Card> cards) {
        if (cards == null || cards.size() < 8) {
            return false;
        }
        int size = (int) ((cards.size() / 0.75) + 1);
        Map<Integer, Integer> sendCardVos = new HashMap<>(size);
        for (Card card : cards) {
            int count = 0;
            for (Card card2 : cards) {
                if (card.getCardNum() == card2.getCardNum()) {
                    count++;
                }
            }
            sendCardVos.put(card.getCardNum(), count);
        }
        //分析牌组
        if (sendCardVos.size() == 0) {
            return false;
        }
        Set<Map.Entry<Integer, Integer>> entrySet = sendCardVos.entrySet();
        int threeCount = 0;
        int oneCount = 0;
        int twoCount = 0;
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            if (entry.getValue() == 1) {
                oneCount++;
            }
            if (entry.getValue() == 3) {
                threeCount++;
            }
            if (entry.getValue() == 2) {
                twoCount++;
            }
        }
        if (threeCount < 2) {
            return false;
        }
        List<Map.Entry<Integer, Integer>> entryList = sendCardVos.entrySet().stream().filter(e -> e.getValue() == 3).sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
        for (int i = 1; i < entryList.size(); i++) {
            if (entryList.get(i).getKey() != entryList.get(i - 1).getKey() + 1) {
                return false;
            }
        }
        if (threeCount == oneCount && cards.size() == threeCount * 3 + oneCount) {
            return true;
        }
        return threeCount == twoCount && cards.size() == threeCount * 3 + twoCount * 2;
    }

    /**
     * 是否是四带二
     *
     * @param cards 出牌
     * @return boolean
     */
    protected boolean isFourBombWithTwo(List<Card> cards) {
        if (cards == null || cards.size() != 6) {
            return false;
        }
        boolean isFourBombWithTwo = false;
        for (int i = 0; i < cards.size(); i++) {
            int count = 0;
            for (Card card : cards) {
                if (cards.get(i).getCardNum() == card.getCardNum()) {
                    count++;
                }
            }
            if (count == 4) {
                isFourBombWithTwo = true;
                break;
            }
        }
        return isFourBombWithTwo;
    }

    /**
     * 是否是三带一
     *
     * @param cards 出牌
     * @return boolean
     */
    protected boolean isThreeWithOne(List<Card> cards) {
        if (cards == null || cards.size() != 4) {
            return false;
        }
        cards.sort(Comparator.comparingInt(Card::getCardNum));
        //sss + x 或 xxx + s
        if (cards.get(0).getCardNum() == cards.get(1).getCardNum()
                && cards.get(1).getCardNum() == cards.get(2).getCardNum()
                && cards.get(2).getCardNum() != cards.get(3).getCardNum()) {
            return true;
        }
        return cards.get(0).getCardNum() != cards.get(1).getCardNum()
                && cards.get(1).getCardNum() == cards.get(2).getCardNum()
                && cards.get(2).getCardNum() == cards.get(3).getCardNum();
    }

    /**
     * 是否是三带一对
     *
     * @param cards 卡牌
     * @return boolean
     */
    protected boolean isThreeWithTwo(List<Card> cards) {
        if (cards == null || cards.size() != 5) {
            return false;
        }
        cards.sort(Comparator.comparingInt(Card::getCardNum));
        //sss + xx 或者 ss + xxx
        if (cards.get(0).getCardNum() != cards.get(1).getCardNum()) {
            return false;
        }
        if (cards.get(3).getCardNum() != cards.get(4).getCardNum()) {
            return false;
        }
        return cards.get(1).getCardNum() == cards.get(2).getCardNum()
                || cards.get(2).getCardNum() == cards.get(3).getCardNum();
    }

    /**
     * 三顺-飞机（起码2个）
     *
     * @param cards 卡牌
     * @return boolean
     */
    protected boolean isStraightThree(List<Card> cards) {
        if (cards == null || cards.size() < 5) {
            return false;
        }
        if (cards.size() % 3 != 0) {
            return false;
        }
        int num = cards.size() / 3;
        List<Card> cardList = distinctAndSort(cards);
        if (cardList.size() != num) {
            return false;
        }
        return isContinuous(cardList);
    }

    /**
     * 去重和排序
     *
     * @param cards 牌list
     * @return List<Card>
     */
    private List<Card> distinctAndSort(List<Card> cards) {
        return cards
                .stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Card::getCardNum))), ArrayList::new))
                .stream().sorted(Comparator.comparing(Card::getCardNum)).collect(Collectors.toList());
    }

    /**
     * 是否是连续的数字
     *
     * @param cards 牌s
     * @return boolean
     */
    private boolean isContinuous(List<Card> cards) {
        cards.sort(Comparator.comparingInt(Card::getCardNum));
        boolean isContinuous = true;
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getCardNum() != cards.get(i - 1).getCardNum() + 1) {
                isContinuous = false;
                break;
            }
        }
        return isContinuous;
    }

    /**
     * 判断是否是双顺--连对(三个或三个以上）
     *
     * @param cards 卡牌
     * @return boolean
     */
    protected boolean isStraightDouble(List<Card> cards) {
        if (cards == null || cards.size() < 5) {
            return false;
        }
        if (cards.size() % 2 != 0) {
            return false;
        }
        int num = cards.size() / 2;
        List<Card> cardList = distinctAndSort(cards);
        if (cardList.size() != num) {
            return false;
        }
        return isContinuous(cardList);
    }

    /**
     * 判断是否是顺子(单顺)
     *
     * @param cards 卡牌
     * @return boolean
     */
    protected boolean isStraight(List<Card> cards) {
        if (cards == null || cards.size() < 5) {
            return false;
        }
        return isContinuous(cards);
    }

    /**
     * 卡牌连续相同的次数
     *
     * @param cards 卡牌vos
     * @return 数值
     */
    protected int sameSumInCards(List<Card> cards) {
        int sameSum = 0;
        if (cards == null || cards.size() == 0 || cards.size() < 2) {
            return sameSum;
        }
        cards.sort(Comparator.comparingInt(Card::getCardNum));
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getCardNum() == cards.get(i - 1).getCardNum()) {
                sameSum++;
            }
        }
        return sameSum;
    }

}
