package service;

import entity.GameCardVo;

import java.util.List;


/**
 * 样式包装
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/11 11:20
 */
public interface ImageService {
    /**
     * 包装牌样式
     * @param gameCardVo 牌
     * @param subscript true 加入下标
     * @return String
     */
    String gameCardImage(GameCardVo gameCardVo,boolean subscript);

    /**
     * 包装牌组 和 键盘符样式
     * @param gameCardVos 牌vos
     * @param subscript true 加入下标
     * @return String
     */
    String gameCardsImage(List<GameCardVo> gameCardVos,boolean subscript);

    /**
     * 加入键盘标识
     * @param gameCardVos 牌vos
     */
    void addSubscript(List<GameCardVo> gameCardVos);

    /**
     * 出牌（重新加入标识，加入样式）
     * @param gameCardVos 牌vos
     * @return 已包装好玩家当前手牌
     */
    String sendCardImage(List<GameCardVo> gameCardVos);
}
