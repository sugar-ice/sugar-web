package cn.wolfcode.web.modules.zMessage.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统消息中心
 * </p>
 *
 * @author 冰糖IO
 * @since 2023-06-29
 */
@Data
public class ZzMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String messageTitle;

    private Integer messageType;

    private LocalDateTime publishTime;

    private String publisherId;

    private String receiverId;

    private String messageContent;

    private Integer topPriority;

    @Override
    public String toString() {
        return "ZzMessage{" +
                "id=" + id +
                ", messageTitle=" + messageTitle +
                ", messageType=" + messageType +
                ", publishTime=" + publishTime +
                ", publisherId=" + publisherId +
                ", receiverId=" + receiverId +
                ", messageContent=" + messageContent +
                ", topPriority=" + topPriority +
                "}";
    }
}
