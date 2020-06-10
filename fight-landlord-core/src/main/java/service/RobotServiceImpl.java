package service;

import card.CardTypeEnum;
import entity.GameCardVo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/10 13:59
 */
@Slf4j
public class RobotServiceImpl implements RobotService {

    CardFactory cardFactory = new CardFactory();

    @Override
    public List<GameCardVo> initCard() {
        List<GameCardVo> gameCardVos = new ArrayList<>();
        gameCardVos.addAll(cardFactory.initGeneralCard(CardTypeEnum.RED_PEACH));
        gameCardVos.addAll(cardFactory.initGeneralCard(CardTypeEnum.SPADES));
        gameCardVos.addAll(cardFactory.initGeneralCard(CardTypeEnum.PLUM_FLOWER));
        gameCardVos.addAll(cardFactory.initGeneralCard(CardTypeEnum.SQUARE));
        return gameCardVos;
    }

    public static void main(String[] args) {
        RobotServiceImpl robotService = new RobotServiceImpl();
        System.out.println(robotService.initCard());
    }



}
