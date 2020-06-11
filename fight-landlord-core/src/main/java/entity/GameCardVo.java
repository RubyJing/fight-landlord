package entity;

import lombok.Data;


/**
 * 游戏卡牌包装类
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 11:54
 */
@Data
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
     * 牌 下标
     */
    private String subscript;
}
