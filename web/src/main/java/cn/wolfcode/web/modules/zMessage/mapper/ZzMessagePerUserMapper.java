package cn.wolfcode.web.modules.zMessage.mapper;

import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUser;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUserDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ZzMessagePerUserMapper extends MPJBaseMapper<ZzMessagePerUser> {
    @Select({"<script>",
            "SELECT zmpu.user_id, zm.receiver_id, zmpu.is_read, zmpu.is_deleted, zm.id AS message_id, zmpu.id AS id, ",
            "zm.message_title, zm.top_priority, zm.publish_time, p.username AS publisher_username, ",
            "r.username AS receiver_username, ",
            "zm.message_type, zm.message_content ",
            "FROM zz_message AS zm ",
            "LEFT JOIN zz_message_per_user AS zmpu ON zm.id = zmpu.message_id AND (zm.receiver_id IS NULL OR zm.receiver_id = zmpu.user_id) ",
            "LEFT JOIN SYS_USER_INFO AS p ON p.user_id = zm.publisher_id ",
            "LEFT JOIN SYS_USER_INFO AS r ON r.user_id = zm.receiver_id ",
            "WHERE 1=1 ",
            "<if test='receiverId != null'>",
            "AND (zm.receiver_id IS NULL OR zm.receiver_id = #{receiverId}) ",
            "</if>",
            "<if test='isRead != null'>",
            "AND zmpu.is_read = #{isRead} ",
            "</if>",
            "<if test='isDeleted != null'>",
            "AND zmpu.is_deleted = #{isDeleted} ",
            "</if>",
            "ORDER BY zm.top_priority DESC, zm.publish_time DESC ",
            "LIMIT #{currentOffset}, #{size}",
            "</script>"
    })
    List<ZzMessagePerUserDetail> checkMsg(@Param("isRead") Integer isRead, @Param("isDeleted") Integer isDeleted, @Param("receiverId") String receiverId, @Param("currentOffset") int currentOffset, @Param("size") int size);

    @Select({"<script>",
            "SELECT zmpu.user_id, zm.receiver_id, zmpu.is_read, zmpu.is_deleted, zm.id AS message_id, zmpu.id AS id, ",
            "zm.message_title, zm.top_priority, zm.publish_time, p.username AS publisher_username, ",
            "r.username AS receiver_username, ",
            "zm.message_type, zm.message_content ",
            "FROM zz_message AS zm ",
            "LEFT JOIN zz_message_per_user AS zmpu ON zm.id = zmpu.message_id AND (zm.receiver_id IS NULL OR zm.receiver_id = zmpu.user_id) ",
            "LEFT JOIN SYS_USER_INFO AS p ON p.user_id = zm.publisher_id ",
            "LEFT JOIN SYS_USER_INFO AS r ON r.user_id = zm.receiver_id ",
            "WHERE 1=1 ",
            "<if test='receiverId != null'>",
            "AND (zm.receiver_id IS NULL OR zm.receiver_id = #{receiverId}) ",
            "</if>",
            "<if test='isRead != null'>",
            "AND zmpu.is_read = #{isRead} ",
            "</if>",
            "<if test='isDeleted != null'>",
            "AND zmpu.is_deleted = #{isDeleted} ",
            "</if>",
            "ORDER BY zm.top_priority DESC, zm.publish_time DESC ",
            "LIMIT #{currentOffset}, #{size}",
            "</script>"
    })
    IPage<ZzMessagePerUserDetail> checkMsgPage(@Param("isRead") Integer isRead, @Param("isDeleted") Integer isDeleted, @Param("receiverId") String receiverId, @Param("currentOffset") int currentOffset, @Param("size") int size);
}
