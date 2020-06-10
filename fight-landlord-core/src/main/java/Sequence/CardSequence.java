package Sequence;

/**
 * 卡牌序列
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 17:02
 */
public class CardSequence {

    private static int CARD_SEQUENCE = 1;


    public static int getInstance() {
        return CARD_SEQUENCE++;
    }

}
