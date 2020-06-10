package entity;

import lombok.Data;

/**
 * 卡牌
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 17:16
 */
@Data
public class Card {
    private int id;

    /**
     * 卡牌名称
     */
    private String cardName;

    /**
     * 卡牌数值
     */
    private Integer cardNum;

    /**
     * 是否打出
     */
    private Boolean isHit = false;


}
