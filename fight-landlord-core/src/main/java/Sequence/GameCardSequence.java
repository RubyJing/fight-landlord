package Sequence;

/**
 * 游戏卡牌序列
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 17:12
 */
public class GameCardSequence {
    private static int GAME_CARD_SEQUENCE = 1;

    public static int getInstance() {
        return GAME_CARD_SEQUENCE++;
    }

}
