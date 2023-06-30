package cn.wolfcode.web.modules.zMessage.service;

import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUser;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUserDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IZzMessagePerUserService extends IService<ZzMessagePerUser> {
    List<ZzMessagePerUserDetail> checkMsg(Integer isRead, Integer isDeleted, String receiverId, int currentPage, int pageSize);

    IPage<ZzMessagePerUserDetail> checkMsgPage(Integer isRead, Integer isDeleted, String receiverId, int currentPage, int pageSize);
}
