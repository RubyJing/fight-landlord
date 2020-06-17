package service;

import entity.GameCardVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/11 11:23
 */

@Slf4j
public class ImageServiceImpl implements ImageService {
    @Override
    public String gameCardImage(GameCardVo gameCardVo) {
        StringBuilder cardImage = new StringBuilder("|");
        cardImage.append(gameCardVo.getCardType().getGraph());
        cardImage.append(gameCardVo.getCard().getCardName());
        cardImage.append("|");
        if (gameCardVo.getSubscript() != null) {
            cardImage.append(gameCardVo.getSubscript()+" ");
        }
        return cardImage.toString();
    }


    @Override
    public String gameCardsImage(List<GameCardVo> gameCardVos) {
        StringBuilder cardImages = new StringBuilder();
        for (GameCardVo gameCardVo : gameCardVos) {
            cardImages.append(this.gameCardImage(gameCardVo));
        }
        return cardImages.toString();
    }


    @Override
    public void addSubscript(List<GameCardVo> gameCardVos) {
        String[] keyBoards = new String[]{"⓵", "⓶", "⓷", "⓸", "⓹", "⓺", "⓻", "⓼", "⓽", "⓿", "Ⓠ", "Ⓦ", "Ⓔ", "Ⓡ", "Ⓣ", "Ⓨ", "Ⓤ", "Ⓘ", "Ⓞ", "Ⓟ"};
        for (int i = 0; i < gameCardVos.size(); i++) {
            gameCardVos.get(i).setSubscript(keyBoards[i]);
        }
    }



}
