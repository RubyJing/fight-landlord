package entity;

import card.SendCardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 游戏对局类
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 10:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    /**
     * 对局唯一标识:群Id
     */
    private Long groupId;

    /**
     * 对局玩家数
     */
    private List<Player> players = new ArrayList<>();

    /**
     * 对局所有牌
     */
    private List<GameCardVo> cards;

    /**
     * 群组消息队列
     */
    private BlockingQueue<String> groupQueue = new LinkedBlockingQueue<>();

    /**
     * 对局开始(已发牌，准备开始）
     */
    private Boolean isStart = false;

    /**
     * 线程数
     */
    private int thread;

    /**
     * 选择不出牌数：最大2
     */
    private int noSendCardCount;

    /**
     * 不抢地主数:最大3
     */
    private int noChooseLandLord;

    /**
     * 当前出牌玩家
     */
    private Player currPlayer;

    /**
     * 当前出牌
     */
    private List<GameCardVo> currCard;

    /**
     * 当前出牌的牌型
     */
    private SendCardType currSendCardType;
}
