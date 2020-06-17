package org.view.coolq.entity;

import entity.Player;

/**
 * 对局当前操作玩家Vo
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/16 9:36
 */
public class GameCurrPlayer {

    private long groupId;

    private Player currPlayer;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public GameCurrPlayer(long groupId, Player currPlayer) {
        this.groupId = groupId;
        this.currPlayer = currPlayer;
    }
}
