package service;

import entity.Game;
import entity.GameCardVo;

import java.util.List;

/**
 * 游戏规则
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 9:34
 */
public interface PlayService {

    /**
     * 判断键盘输入是否合法
     * @param game 游戏对局
     * @param gameCardVos 手牌vos
     * @param keyBoards 键盘标识输入
     * @return true
     */
    boolean isKeyBoardsInputLegal(Game game,List<GameCardVo> gameCardVos,String keyBoards);
    /**
     * 判断是否是输入的键盘标识
     *
     * @param keyBoards 键盘标识输入
     * @return true是
     */
    boolean isKeyBoard(String keyBoards);

    /**
     * 判断是否输入的是键盘标识都是手牌的标识
     *
     * @param gameCardVos 手牌vos
     * @param keyBoards 键盘标识输入
     * @return
     */
    boolean isInPlayerBoard(List<GameCardVo> gameCardVos, String keyBoards);

    /**
     * 是否符合斗地主的规则
     * @param game 当前游戏
     * @param gameCardVos 玩家手牌
     * @param keyBoards 键盘输入
     * @return true
     */
    boolean isFightLandlordRule(Game game,List<GameCardVo> gameCardVos,String keyBoards);

    /**
     * 获取所有（没有打出）手牌
     * @param gameCardVos 全部状态的牌
     * @return 没有打出的牌
     */
    List<GameCardVo> getAllNotHitCards(List<GameCardVo> gameCardVos);



    /**
     * 玩家出牌
     *
     * @param game      对局
     * @param playQq    玩家qq
     * @param keyBoards 键盘标识
     * @return 出牌图像包装
     */
    String playerSendCard(Game game, long playQq, String keyBoards);
}
