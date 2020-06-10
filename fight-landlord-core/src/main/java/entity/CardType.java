package entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * 卡牌类型
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 17:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
