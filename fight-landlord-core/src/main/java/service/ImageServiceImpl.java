package service;

import entity.GameCardVo;
import javafx.print.Printer;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
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
        return cardImage.toString();
    }

    @Override
    public String gameCardsImage(List<GameCardVo> gameCardVos) {
        StringBuilder cardImages = new StringBuilder();
        StringBuilder keyBoard = new StringBuilder();
        for (GameCardVo gameCardVo : gameCardVos) {
            cardImages.append(this.gameCardImage(gameCardVo));
            keyBoard.append(" ❅"+gameCardVo.getSubscript()+"");
        }
        return cardImages.append("\n").append(keyBoard).toString();
    }


}
