package card;

import entity.CardType;

import java.awt.*;

/**
 * 方块
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 16:03
 */
public class SquareCard extends CardType {

    @Override
    public Long getId() {
        return 4L;
    }

    @Override
    public String getCardType() {
        return "方块";
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public String getGraph() {
        return "♢";
    }
}
