package entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 玩家
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 17:07
 */
@Data
@NoArgsConstructor
public class Player {
    private Long id;

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
}
