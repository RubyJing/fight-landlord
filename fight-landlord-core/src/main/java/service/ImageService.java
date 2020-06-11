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
     * @return String
     */
    String gameCardImage(GameCardVo gameCardVo);

    /**
     * 包装牌组样式
     * @param gameCardVos 牌vos
     * @return String
     */
    String gameCardsImage(List<GameCardVo> gameCardVos);
}
