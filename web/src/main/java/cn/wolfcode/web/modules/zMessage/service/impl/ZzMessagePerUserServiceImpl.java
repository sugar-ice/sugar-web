package cn.wolfcode.web.modules.zMessage.service.impl;

import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUser;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUserDetail;
import cn.wolfcode.web.modules.zMessage.mapper.ZzMessagePerUserMapper;
import cn.wolfcode.web.modules.zMessage.service.IZzMessagePerUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ZzMessagePerUserServiceImpl extends ServiceImpl<ZzMessagePerUserMapper, ZzMessagePerUser> implements IZzMessagePerUserService {

    @Resource
    ZzMessagePerUserMapper zzMessagePerUserMapper;

    @Override
    public List<ZzMessagePerUserDetail> checkMsg(Integer isRead, Integer isDeleted, String receiverId, int currentPage, int pageSize) {
        return zzMessagePerUserMapper.checkMsg(isRead, isDeleted, receiverId, (currentPage - 1) * pageSize, pageSize);
    }

    public IPage<ZzMessagePerUserDetail> checkMsgPage(Integer isRead, Integer isDeleted, String receiverId, int currentPage, int pageSize) {
        return zzMessagePerUserMapper.checkMsgPage(isRead, isDeleted, receiverId, (currentPage - 1) * pageSize, pageSize);
    }
}
