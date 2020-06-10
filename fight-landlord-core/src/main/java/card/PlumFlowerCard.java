package card;

import entity.CardType;

import java.awt.*;

/**
 * 梅花
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 16:01
 */
public class PlumFlowerCard extends CardType {
    @Override
    public Long getId() {
        return 3L;
    }

    @Override
    public String getCardType() {
        return "梅花";
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public String getGraph() {
        return "♧";
    }
}
