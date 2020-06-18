package org.view.coolq.entity;

/**
 * 返回消息
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 17:16
 */
public class Response {

    private Long groupId;

    private Long playerQq;

    private String message;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getPlayerQq() {
        return playerQq;
    }

    public void setPlayerQq(Long playerQq) {
        this.playerQq = playerQq;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response(Long groupId, Long playerQq, String message) {
        this.groupId = groupId;
        this.playerQq = playerQq;
        this.message = message;
    }
}
