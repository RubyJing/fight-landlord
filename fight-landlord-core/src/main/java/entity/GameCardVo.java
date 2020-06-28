package entity;



/**
 * 游戏卡牌包装类
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 11:54
 */
public class GameCardVo {

    private int id;

    /**
     * 卡牌类型
     */
    private CardType cardType;

    /**
     * 卡牌
     */
    private Card card;

    /**
     * 牌 下标样式
     */
    private String subscript;

    /**
     * 牌 下标样式值
     */
    private String subscriptValue;


    /**
     * 是否打出
     */
    private Boolean isHit = false;

    public GameCardVo(int id, CardType cardType, Card card, String subscript, String subscriptValue, Boolean isHit) {
        this.id = id;
        this.cardType = cardType;
        this.card = card;
        this.subscript = subscript;
        this.subscriptValue = subscriptValue;
        this.isHit = isHit;
    }

    public GameCardVo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getSubscript() {
        return subscript;
    }

    public void setSubscript(String subscript) {
        this.subscript = subscript;
    }

    public String getSubscriptValue() {
        return subscriptValue;
    }

    public void setSubscriptValue(String subscriptValue) {
        this.subscriptValue = subscriptValue;
    }

    public Boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(Boolean hit) {
        isHit = hit;
    }
}
