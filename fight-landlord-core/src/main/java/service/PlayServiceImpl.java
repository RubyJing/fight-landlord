package service;

import card.CardConstant;
import card.SendCardType;
import entity.Card;
import entity.Game;
import entity.GameCardVo;
import entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/18 10:56
 */
public class PlayServiceImpl implements PlayService {

    private ImageService imageService = new ImageServiceImpl();

    @Override
    public boolean isKeyBoardsInputLegal(Game game, List<GameCardVo> gameCardVos, String keyBoards) {
        if (!isKeyBoard(keyBoards)) {
            return false;
        }
        if (!isInPlayerBoard(gameCardVos, keyBoards)) {
            return false;
        }
        if (!isFightLandlordRule(game, gameCardVos, keyBoards)) {
            return false;
        }
        return true;
    }

    @Override
    public List<GameCardVo> getAllNotHitCards(List<GameCardVo> gameCardVos) {
        if (gameCardVos == null || gameCardVos.size() == 0) {
            return null;
        }

        return gameCardVos.stream().filter(e -> !e.getIsHit()).collect(Collectors.toList());
    }

    @Override
    public boolean isKeyBoard(String keyBoards) {
        String[] input = keyBoards.trim().split("");
        if (input.length == 0) {
            return false;
        }
        int sum = 0;
        for (String s : input) {
            for (int y = 0; y < CardConstant.KEY_BOARDS_VALUE.length; y++) {
                if (s.equals(CardConstant.KEY_BOARDS_VALUE[y])) {
                    sum++;
                }
            }
        }
        return sum == input.length;
    }

    @Override
    public boolean isInPlayerBoard(List<GameCardVo> gameCardVos, String keyBoards) {
        if (gameCardVos == null || gameCardVos.size() == 0) {
            return false;
        }
        String[] input = keyBoards.trim().split("");
        if (input.length == 0) {
            return false;
        }
        int sum = 0;
        for (String s : input) {
            for (GameCardVo gameCardVo : gameCardVos) {
                if (gameCardVo.getSubscriptValue().equals(s)) {
                    sum++;
                }
            }
        }
        return sum == input.length;
    }

    @Override
    public boolean isFightLandlordRule(Game game, List<GameCardVo> gameCardVos, String keyBoards) {
        String[] input = keyBoards.trim().split("");
        if (input.length == 0) {
            return true;
        }
        List<Card> cards = new ArrayList<>();
        for (String s : input) {
            for (GameCardVo gameCardVo : gameCardVos) {
                if (gameCardVo.getSubscriptValue().equals(s)) {
                    cards.add(gameCardVo.getCard());
                }
            }
        }
        if (isFightLandlordCardRule(cards) == null) {
            return false;
        }

        List<GameCardVo> currGameCards = game.getCurrCard();

        return true;
    }

    /**
     * 出牌类型
     *
     * @param cards 卡牌
     * @return 类型
     */
    private SendCardType isFightLandlordCardRule(List<Card> cards) {
        //无牌
        if (cards == null || cards.size() == 0) {
            return null;
        }

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
        Map<Integer, Integer> sendCardVos = new HashMap<>();
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
        if (cards.get(0).getCardNum() != cards.get(1).getCardNum()
                && cards.get(1).getCardNum() == cards.get(2).getCardNum()
                && cards.get(2).getCardNum() == cards.get(3).getCardNum()) {
            return true;
        }
        return false;
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
        if (cards.get(1).getCardNum() == cards.get(2).getCardNum()
                || cards.get(2).getCardNum() == cards.get(3).getCardNum()) {
            return true;
        }
        return false;
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

    @Override
    public String playerSendCard(Game game, long playQq, String keyBoards) {
        String[] input = keyBoards.trim().split("");
        if (input.length == 0) {
            return null;
        }

        //匹配玩家
        Player currPlayer = null;
        for (Player player : game.getPlayers()) {
            if (player.getQqNum() == playQq) {
                currPlayer = player;
            }
        }
        if (currPlayer == null) {
            return null;
        }

        //处理卡牌
        List<GameCardVo> playerCardsVos = currPlayer.getCards();
        List<GameCardVo> isHitVos = new ArrayList<>();
        List<Card> currCard = new ArrayList<>();
        for (GameCardVo card : playerCardsVos) {
            for (String s : input) {
                if (card.getSubscriptValue().equals(s)) {
                    card.setSubscriptValue("");
                    card.setIsHit(true);
                    isHitVos.add(card);
                    currCard.add(card.getCard());
                }
            }
        }
        currPlayer.setCards(playerCardsVos);
        game.setCurrCard(isHitVos);
        game.setCurrSendCardType(isFightLandlordCardRule(currCard));
        return game.getCurrSendCardType().getName() + "：" + imageService.gameCardsImage(game.getCurrCard(), false);
    }
}
