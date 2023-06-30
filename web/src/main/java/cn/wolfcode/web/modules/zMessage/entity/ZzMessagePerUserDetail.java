package cn.wolfcode.web.modules.zMessage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZzMessagePerUserDetail extends ZzMessageWithName {
    private Integer isRead;
    private Integer isDeleted;
    private String userId;
    private String messageId;

    @Override
    public String toString() {
        return super.toString() + ", isRead=" + isRead + ", isDeleted=" + isDeleted +
                ", userId=" + userId + ", messageId=" + messageId;
    }
}
