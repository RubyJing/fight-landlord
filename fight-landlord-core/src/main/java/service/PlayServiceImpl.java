package service;

import card.CardConstant;
import card.SendCardType;
import entity.Card;
import entity.Game;
import entity.GameCardVo;
import entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/18 10:56
 */
public class PlayServiceImpl implements PlayService {

    private ImageService imageService = new ImageServiceImpl();
    private SendCardFactory sendCardFactory = new SendCardFactory();

    @Override
    public void noSendCard(Game game) {
        game.setNoSendCardCount(game.getNoSendCardCount() + 1);
    }

    @Override
    public boolean isKeyBoardsInputLegal(Game game, List<GameCardVo> gameCardVos, String keyBoards) {
        if (!isKeyBoard(keyBoards)) {
            return false;
        }
        if (!isInPlayerBoard(gameCardVos, keyBoards)) {
            return false;
        }
        if (!isFightLandlordRule(game, gameCardVos, keyBoards)) {
            return false;
        }
        return true;
    }

    @Override
    public List<GameCardVo> getAllNotHitCards(List<GameCardVo> gameCardVos) {
        if (gameCardVos == null || gameCardVos.size() == 0) {
            return null;
        }

        return gameCardVos.stream().filter(e -> !e.getIsHit()).collect(Collectors.toList());
    }

    @Override
    public boolean isKeyBoard(String keyBoards) {
        String[] input = keyBoards.trim().split("");
        if (input.length == 0) {
            return false;
        }
        int sum = 0;
        for (String s : input) {
            for (int y = 0; y < CardConstant.KEY_BOARDS_VALUE.length; y++) {
                if (s.equals(CardConstant.KEY_BOARDS_VALUE[y])) {
                    sum++;
                }
            }
        }
        return sum == input.length;
    }

    @Override
    public boolean isInPlayerBoard(List<GameCardVo> gameCardVos, String keyBoards) {
        if (gameCardVos == null || gameCardVos.size() == 0) {
            return false;
        }
        String[] input = keyBoards.trim().split("");
        if (input.length == 0) {
            return false;
        }
        int sum = 0;
        for (String s : input) {
            for (GameCardVo gameCardVo : gameCardVos) {
                if (gameCardVo.getSubscriptValue().equals(s)) {
                    sum++;
                }
            }
        }
        return sum == input.length;
    }

    @Override
    public boolean isFightLandlordRule(Game game, List<GameCardVo> gameCardVos, String keyBoards) {
        String[] input = keyBoards.trim().split("");
        if (input.length == 0) {
            return true;
        }
        List<Card> cards = new ArrayList<>();
        for (String s : input) {
            for (GameCardVo gameCardVo : gameCardVos) {
                if (gameCardVo.getSubscriptValue().equals(s)) {
                    cards.add(gameCardVo.getCard());
                }
            }
        }
        //判断出牌类型
        SendCardType sendCardType = sendCardFactory.isFightLandlordCardRule(cards);
        if (sendCardType == null) {
            return false;
        }

        //与已出牌的对比
        List<GameCardVo> currGameCards = game.getCurrCard();
        if (currGameCards != null && currGameCards.size() != 0) {
            return sendCardFactory.isSendCard(sendCardType, cards, currGameCards, game.getCurrSendCardType());
        }

        return true;
    }


    @Override
    public Player gameCurrPlayer(Game game, Player landLord) {
        int currPlayerId;
        if (landLord == null) {
            currPlayerId = game.getCurrPlayer().getId();
        } else {
            currPlayerId = landLord.getId();
        }
        int sendCardCount = game.getNoSendCardCount();
        if (sendCardCount == 2) {
            return game.getCurrPlayer();
        }
        int gameCurr;
        //1-2,2-3,3-1
        int sum = currPlayerId + sendCardCount;
        if (sum < 3) {
            gameCurr = sum + 1;
        } else if (sum == 3) {
            gameCurr = 1;
        } else {
            gameCurr = sum % 3 + 1;
        }
        for (Player player : game.getPlayers()) {
            if (player.getId() == gameCurr) {
                return player;
            }
        }
        return null;
    }

    @Override
    public Player getGameLandLord(Game game) {
        for (Player player : game.getPlayers()) {
            if ("地主".equals(player.getRole().getRoleName())) {
                return player;
            }
        }
        return null;
    }

    @Override
    public String playerSendCard(Game game, long playQq, String keyBoards) {
        String[] input = keyBoards.trim().split("");
        if (input.length == 0) {
            return null;
        }

        //匹配玩家
        Player currPlayer = null;
        for (Player player : game.getPlayers()) {
            if (player.getQqNum() == playQq) {
                currPlayer = player;
            }
        }
        if (currPlayer == null) {
            return null;
        }

        //处理卡牌
        List<GameCardVo> playerCardsVos = currPlayer.getCards();
        List<GameCardVo> isHitVos = new ArrayList<>();
        List<Card> currCard = new ArrayList<>();
        for (String s : input) {
            for (GameCardVo card : playerCardsVos) {
                if (card.getSubscriptValue().equals(s)) {
                    card.setSubscriptValue("");
                    card.setIsHit(true);
                    isHitVos.add(card);
                    currCard.add(card.getCard());
                    break;
                }
            }
        }
        currPlayer.setCards(playerCardsVos);
        game.setCurrPlayer(currPlayer);
        game.setCurrCard(isHitVos);
        game.setCurrSendCardType(sendCardFactory.isFightLandlordCardRule(currCard));
        game.setNoSendCardCount(0);

        return game.getCurrSendCardType().getName()
                + "："
                + imageService.gameCardsImage(game.getCurrCard().stream().sorted(Comparator.comparingInt(e -> e.getCard().getCardNum())).collect(Collectors.toList()), false);
    }
}
