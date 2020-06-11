package org.view.coolq;

import card.CardFactory;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import org.view.coolq.listener.CoolGroupListener;
import org.view.coolq.listener.CoolPrivateListener;

/**
 * 斗地主QQ启动类
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/8 14:26
 */
public class FightLandlordApplication {
    public static void main(String[] args) {
        // 创建机器人配置 ( 传入Picq端口 )
        PicqConfig config = new PicqConfig(31092);
        config.setNoVerify(true);

        PicqBotX bot = new PicqBotX(config);
        bot.addAccount("fight-landlord", "127.0.0.1", 31091);

        // 注册事件监听器, 可以注册多个监听器
        bot.getEventManager().registerListeners(new CoolPrivateListener(),new CoolGroupListener());
        CardFactory cardFactory = new CardFactory();
        bot.startBot();

    }
}
