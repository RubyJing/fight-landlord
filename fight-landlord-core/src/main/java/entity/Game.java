package entity;

import entity.Card;
import entity.GameCardVo;
import entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Player> players;

    /**
     * 对局所有牌
     */
    private List<GameCardVo> cards;
}
