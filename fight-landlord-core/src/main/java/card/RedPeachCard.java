package card;

import entity.CardType;

import java.awt.*;

/**
 * 红桃卡牌
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 14:54
 */
public class RedPeachCard extends CardType {

    @Override
    public Long getId() {
        return 1L;
    }

    @Override
    public String getCardType() {
        return "红桃";
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public String getGraph() {
        return "♡";
    }
}
