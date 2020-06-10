package service;

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
     * 初始化卡牌
     * @return List<GameCardVo>
     */
    List<GameCardVo> initCard();
}
