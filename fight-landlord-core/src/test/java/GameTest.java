import entity.Game;
import entity.GameCardVo;
import entity.Player;
import service.ImageServiceImpl;
import service.RobotServiceImpl;
import util.Print;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/11 10:47
 */
public class GameTest {
    public static void main(String[] args) {
        RobotServiceImpl robotService = new RobotServiceImpl();
        ImageServiceImpl imageService = new ImageServiceImpl();
        List<Player> players = new ArrayList<>(3);
        for (int i = 1; i < 4; i++) {
            Player player = new Player();
            player.setId((long) i);
            player.setPlayerName("玩家" + i);
            players.add(player);
        }
        Game game = new Game();
        game.setGroupId(1L);
        game.setCards(robotService.initCard());
        game.setPlayers(players);
        robotService.licensing(game);
        List<Player> gamePlayers = game.getPlayers();
        for (Player gamePlayer : gamePlayers) {
            System.out.println(gamePlayer.getPlayerName() + "的牌是：");
            System.out.println(imageService.gameCardsImage(gamePlayer.getCards()));
        }
        System.out.println("===========场上剩余卡牌===========");
        for (GameCardVo card : game.getCards()) {
            System.out.print(imageService.gameCardImage(card));
        }



    }
}