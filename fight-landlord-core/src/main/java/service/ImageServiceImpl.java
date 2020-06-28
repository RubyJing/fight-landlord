package service;

import card.CardConstant;
import entity.GameCardVo;


import java.util.List;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/11 11:23
 */


public class ImageServiceImpl implements ImageService {
    @Override
    public String gameCardImage(GameCardVo gameCardVo,boolean subscript) {
        StringBuilder cardImage = new StringBuilder("|");
        cardImage.append(gameCardVo.getCardType().getGraph());
        cardImage.append(gameCardVo.getCard().getCardName());
        cardImage.append("|");
        if (subscript){
            if (gameCardVo.getSubscript() != null) {
                cardImage.append(gameCardVo.getSubscript()).append(" ");
            }
        }
        return cardImage.toString();
    }


    @Override
    public String gameCardsImage(List<GameCardVo> gameCardVos,boolean subscript) {
        StringBuilder cardImages = new StringBuilder();
        for (GameCardVo gameCardVo : gameCardVos) {
            cardImages.append(this.gameCardImage(gameCardVo,subscript));
        }
        return cardImages.toString();
    }



    @Override
    public void addSubscript(List<GameCardVo> gameCardVos) {
        for (int i = 0; i < gameCardVos.size(); i++) {
            gameCardVos.get(i).setSubscript(CardConstant.KEY_BOARDS[i]);
            gameCardVos.get(i).setSubscriptValue(CardConstant.KEY_BOARDS_VALUE[i]);
        }
    }

    @Override
    public String sendCardImage(List<GameCardVo> gameCardVos) {
        this.addSubscript(gameCardVos);
        return this.gameCardsImage(gameCardVos,true);
    }


}
