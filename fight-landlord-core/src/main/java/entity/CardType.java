package entity;



import java.awt.*;

/**
 * 卡牌类型
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 17:30
 */
public class CardType {
    private Long id;

    /**
     * 卡牌类型：方块，红桃，黑桃，梅花 + 大王小王
     */
    private String cardType;

    /**
     * 对应花色
     */
    private Color color;

    /**
     * 对应形状
     */
    private String graph;

    public CardType(Long id, String cardType, Color color, String graph) {
        this.id = id;
        this.cardType = cardType;
        this.color = color;
        this.graph = graph;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public CardType() {
    }
}
