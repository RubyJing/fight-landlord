package org.view.coolq.listener;

import cc.moecraft.icq.event.events.message.EventMessage;
import org.view.coolq.entity.Response;
import org.view.coolq.output.OutputInfo;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 16:10
 */
public class MessageWriteTask implements Runnable {

    private EventMessage eventMessage;

    public MessageWriteTask(EventMessage eventMessage) {
        this.eventMessage = eventMessage;
    }


    @Override
    public void run() {
        while (true) {
            Response responseGroup = OutputInfo.messageQueue.poll();
            if (responseGroup != null) {
                eventMessage.getHttpApi().sendGroupMsg(responseGroup.getGroupId(), responseGroup.getMessage());
            }

            Response response = OutputInfo.privateMessageQueue.poll();
            if (response != null) {
                eventMessage.getHttpApi().sendPrivateMsg(response.getPlayerQq(), response.getMessage());
            }
        }
    }
}
