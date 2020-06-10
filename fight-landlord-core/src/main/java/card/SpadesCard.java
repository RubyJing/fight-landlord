package card;

import entity.CardType;

import java.awt.*;

/**
 * 黑桃牌
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 15:56
 */
public class SpadesCard extends CardType {

    @Override
    public Long getId() {
        return 2L;
    }

    @Override
    public String getCardType() {
        return "黑桃";
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public String getGraph() {
        return "♤";
    }
}
