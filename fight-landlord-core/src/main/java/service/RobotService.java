package service;

import entity.Game;
import entity.GameCardVo;

import java.util.List;


/**
 *  机器人接口
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 9:33
 */
public interface RobotService {

    /**
     * 给玩家发牌
     * @param game 游戏
     */
    void licensing(Game game);

    /**
     * 分配角色（地主/农民）
     * @param game 对局
     * @param playerQq 玩家qq
     */
    void getLandLord(Game game,long playerQq);

    /**
     * 牌组通过数值排序
     *
     * @param gameCardVos 牌组voList
     */
    void sortAscByNum(List<GameCardVo> gameCardVos);


}
