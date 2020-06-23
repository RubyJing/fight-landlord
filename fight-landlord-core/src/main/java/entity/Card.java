package entity;


/**
 * 卡牌
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 17:16
 */

public class Card {
    private int id;

    /**
     * 卡牌名称
     */
    private String cardName;

    /**
     * 卡牌数值
     */
    private int cardNum;

    public Card(int id, String cardName, int cardNum) {
        this.id = id;
        this.cardName = cardName;
        this.cardNum = cardNum;
    }

    public Card() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }
}
