package entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import role.Role;

import java.util.List;

/**
 * 玩家
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 17:07
 */
public class Player {
    /**
     * 牌桌位置：1-2,2-3,3-1
     */
    private Integer id;
    /**
     * 玩家QQ号
     */
    private long qqNum;
    /**
     * 是否好友
     */
    private boolean isBuddy;
    /**
     * 玩家名称
     */
    private String playerName;
    /**
     * 角色
     */
    private Role role;
    /**
     * 卡牌
     */
    private List<GameCardVo> cards;

    public Player() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getQqNum() {
        return qqNum;
    }

    public void setQqNum(long qqNum) {
        this.qqNum = qqNum;
    }

    public boolean isBuddy() {
        return isBuddy;
    }

    public void setBuddy(boolean buddy) {
        isBuddy = buddy;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<GameCardVo> getCards() {
        return cards;
    }

    public void setCards(List<GameCardVo> cards) {
        this.cards = cards;
    }
}
