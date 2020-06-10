package card;

import lombok.Getter;

/**
 * 牌类型
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 14:46
 */
@Getter
public enum  CardTypeEnum {
    /**
     * 红桃
     */
    RED_PEACH(1L),
    /**
     * 黑桃
     */
    SPADES(2L),
    /**
     * 梅花
     */
    PLUM_FLOWER(3L),
    /**
     * 方块
     */
    SQUARE(4L)
    ;

    private Long id;

    CardTypeEnum(Long id) {
        this.id = id;
    }
}
