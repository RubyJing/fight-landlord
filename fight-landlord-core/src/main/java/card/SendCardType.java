package card;

/**
 * 出牌类型
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/19 11:13
 */
public enum  SendCardType {
    /**
     * 单牌
     */
    ONE(1,true,"单张"),
    /**
     * 对子
     */
    TWO_PAIR(2,true,"对子"),
    /**
     * 王炸
     */
    TWO_QUEEN_BOMB(2,true,"王炸"),
    /**
     * 三张一样
     */
    THREE_SAME (3,true,"三同"),
    /**
     * 三带一
     */
    THREE_WITH_ONE(4,true,"三带一"),
    /**
     * 三带一对
     */
    THREE_WITH_TWO(5,true,"三带一对"),
    /**
     * 四炸
     */
    FOUR_BOMB(4,true,"四炸"),
    /**
     * 四带一
     */
    FOUR_BOMB_WITH_ONE(5,true,"四带一"),
    /**
     * 四带二
     */
    FOUR_BOMB_WITH_TWO(6,true,"四带二"),
    /**
     * 顺子：单顺
     */
    MORE_ONE(5,false,"顺子"),
    /**
     * 顺子：双顺(三个或三个以上）
     */
    MORE_TWO_PAIR(5,false,"连对"),
    /**
     * 顺子：三顺-飞机（起码2个）
     */
    MORE_THREE(5,false,"飞机"),
    /**
     * 飞机带翅膀：三顺＋同数量的单牌（或同数量的对牌）
     */
    MORE_THREE_WITH(7,false,"飞机带翅膀"),

    ;

    /**
     * 出牌的数量
     */
    private int cardNum;
    /**
     * 出牌的数量是否是需要等于；否就是大于
     */
    private boolean isEqual;
    /**
     * 出牌名称
     */
    private String name;

    SendCardType(int cardNum, boolean isEqual, String name) {
        this.cardNum = cardNum;
        this.isEqual = isEqual;
        this.name = name;
    }

    public int getCardNum() {
        return cardNum;
    }

    public boolean isEqual() {
        return isEqual;
    }

    public String getName() {
        return name;
    }
}
