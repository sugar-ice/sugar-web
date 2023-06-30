package cn.wolfcode.web.modules.zMessage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ZzMessageWithName extends ZzMessage {
    private String publisherUsername;
    private String receiverUsername;
    private String messageId;

    @Override
    public String toString() {
        return super.toString() + ", publisherUsername=" + publisherUsername + ", receiverUsername=" + receiverUsername;
    }
}
