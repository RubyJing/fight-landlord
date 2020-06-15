package org.view.coolq.listener;

import cc.moecraft.icq.event.events.message.EventMessage;
import lombok.SneakyThrows;
import org.view.coolq.entity.Response;
import org.view.coolq.output.OutputInfo;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 16:10
 */
public class ReadMessageWriteTask implements Runnable {

    private EventMessage eventMessage;

    public ReadMessageWriteTask(EventMessage eventMessage) {
        this.eventMessage = eventMessage;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true){
            String message = OutputInfo.messageQueue.take();
            eventMessage.respond(message);

            Response response = OutputInfo.privateMessageQueue.take();
            eventMessage.getHttpApi().sendPrivateMsg(response.getPlayerQq(),response.getMessage());
        }
    }
}
