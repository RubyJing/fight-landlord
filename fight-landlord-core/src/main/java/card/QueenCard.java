package card;

import entity.CardType;

import java.awt.*;

/**
 * 王牌
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 17:30
 */
public class QueenCard extends CardType {

    @Override
    public Long getId() {
        return 5L;
    }

    @Override
    public String getCardType() {
        return "王";
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public String getGraph() {
        return "♛";
    }
}
