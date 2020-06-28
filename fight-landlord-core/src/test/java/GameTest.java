import card.CardFactory;
import entity.Game;
import entity.GameCardVo;
import entity.Player;
import service.ImageServiceImpl;
import service.PlayService;
import service.PlayServiceImpl;
import service.RobotServiceImpl;
import util.Print;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/11 10:47
 */
public class GameTest {
    public static void main(String[] args) {
        new CardFactory();
        RobotServiceImpl robotService = new RobotServiceImpl();
        ImageServiceImpl imageService = new ImageServiceImpl();
        PlayService playService = new PlayServiceImpl();
        List<Player> players = new ArrayList<>(3);
        for (int i = 1; i < 4; i++) {
            Player player = new Player();
            player.setId(i);
            player.setPlayerName("玩家" + i);
            players.add(player);
            player.setQqNum(i);
        }
        Game game = new Game();
        game.setGroupId(1L);
        game.setCards(CardFactory.initCardPool);
        game.setPlayers(players);
        robotService.licensing(game);
        List<Player> gamePlayers = game.getPlayers();
        for (Player gamePlayer : gamePlayers) {
            System.out.println(gamePlayer.getPlayerName() + "的牌是：");
            imageService.addSubscript(gamePlayer.getCards());
            System.out.println(imageService.gameCardsImage(gamePlayer.getCards(), true));
        }
        System.out.println("===========场上剩余卡牌===========");
        for (GameCardVo card : game.getCards()) {
            System.out.print(imageService.gameCardImage(card, false));
        }
        robotService.getLandLord(game, 1);
        System.out.println("\n地主的牌是：");
        imageService.addSubscript(players.get(0).getCards());
        System.out.println(imageService.gameCardsImage(players.get(0).getCards(), true));
        while (true){
            System.out.print("选择出牌：");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (playService.isKeyBoardsInputLegal(game, playService.getAllNotHitCards(players.get(0).getCards()), input)) {
                Print.PN("场上" + players.get(0).getPlayerName() + "打出：", Print.GREEN);
                Print.PN(playService.playerSendCard(game, 1, input), Print.RED, Print.REVERSE);
                System.out.println("您的手牌:");
                List<GameCardVo> gameCardVos = playService.getAllNotHitCards(players.get(0).getCards());
                if (gameCardVos.size() == 0) {
                    System.out.println("恭喜您获得胜利");
                } else {
                    System.out.println(imageService.sendCardImage(gameCardVos));
                }
            } else {
                System.out.println("输入格式错误");
            }
        }

    }
}
