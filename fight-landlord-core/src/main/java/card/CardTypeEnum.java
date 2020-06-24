package card;


/**
 * 牌类型
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 14:46
 */
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
    SQUARE(4L),
    /**
     * 王
     */
    Queen(5L)
    ;

    private Long id;

    CardTypeEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
