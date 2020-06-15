package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 游戏类
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
}
