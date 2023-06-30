package cn.wolfcode.web.modules.zMessage.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ZzMessagePerUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private String messageId;
    private Integer isRead;
    private Integer isDeleted;

    // Getters and Setters

    @Override
    public String toString() {
        return "ZzMessagePerUser{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", isRead=" + isRead +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
