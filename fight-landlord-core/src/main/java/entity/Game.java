package entity;

import card.SendCardType;
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

    public Game(Long groupId, List<Player> players, List<GameCardVo> cards, BlockingQueue<String> groupQueue, Boolean isStart, int thread, int noSendCardCount, int noChooseLandLord, Player currPlayer, List<GameCardVo> currCard, SendCardType currSendCardType) {
        this.groupId = groupId;
        this.players = players;
        this.cards = cards;
        this.groupQueue = groupQueue;
        this.isStart = isStart;
        this.thread = thread;
        this.noSendCardCount = noSendCardCount;
        this.noChooseLandLord = noChooseLandLord;
        this.currPlayer = currPlayer;
        this.currCard = currCard;
        this.currSendCardType = currSendCardType;
    }

    public Game() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<GameCardVo> getCards() {
        return cards;
    }

    public void setCards(List<GameCardVo> cards) {
        this.cards = cards;
    }

    public BlockingQueue<String> getGroupQueue() {
        return groupQueue;
    }

    public void setGroupQueue(BlockingQueue<String> groupQueue) {
        this.groupQueue = groupQueue;
    }

    public Boolean getIsStart() {
        return isStart;
    }

    public void setIsStart(Boolean start) {
        isStart = start;
    }

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public int getNoSendCardCount() {
        return noSendCardCount;
    }

    public void setNoSendCardCount(int noSendCardCount) {
        this.noSendCardCount = noSendCardCount;
    }

    public int getNoChooseLandLord() {
        return noChooseLandLord;
    }

    public void setNoChooseLandLord(int noChooseLandLord) {
        this.noChooseLandLord = noChooseLandLord;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public List<GameCardVo> getCurrCard() {
        return currCard;
    }

    public void setCurrCard(List<GameCardVo> currCard) {
        this.currCard = currCard;
    }

    public SendCardType getCurrSendCardType() {
        return currSendCardType;
    }

    public void setCurrSendCardType(SendCardType currSendCardType) {
        this.currSendCardType = currSendCardType;
    }
}
