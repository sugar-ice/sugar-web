package cn.wolfcode.web.modules.zMessage.service.impl;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessage;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUserDetail;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessageWithName;
import cn.wolfcode.web.modules.zMessage.mapper.ZzMessageMapper;
import cn.wolfcode.web.modules.zMessage.service.IZzMessageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ZzMessageServiceImpl extends ServiceImpl<ZzMessageMapper, ZzMessage> implements IZzMessageService {

    @Resource
    ZzMessageMapper zzMessageMapper;

    @Override
    public IPage<ZzMessage> findZzMessagePage(LayuiPage layuiPage, MPJLambdaWrapper<ZzMessage> wrapper) {
        IPage<ZzMessage> iPage = zzMessageMapper.selectJoinPage(new Page<>(layuiPage.getPage(), layuiPage.getLimit()),
                ZzMessage.class,
                wrapper);
        return iPage;
    }

    @Override
    public IPage<ZzMessagePerUserDetail> findZzMessagePerUserPage(LayuiPage layuiPage, MPJLambdaWrapper<ZzMessage> wrapper) {
        IPage<ZzMessagePerUserDetail> iPage = zzMessageMapper.selectJoinPage(new Page<>(layuiPage.getPage(), layuiPage.getLimit()),
                ZzMessagePerUserDetail.class,
                wrapper);
        return iPage;
    }

    @Override
    public IPage<ZzMessageWithName> findZzMessageWithNameMapper(LayuiPage layuiPage, MPJLambdaWrapper<ZzMessage> wrapper) {
        IPage<ZzMessageWithName> iPage = zzMessageMapper.selectJoinPage(new Page<>(layuiPage.getPage(), layuiPage.getLimit()),
                ZzMessageWithName.class,
                wrapper);
        return iPage;
    }


}
