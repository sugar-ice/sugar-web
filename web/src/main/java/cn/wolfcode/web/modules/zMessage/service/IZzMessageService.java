package cn.wolfcode.web.modules.zMessage.service;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessage;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUser;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessageWithName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

public interface IZzMessageService extends IService<ZzMessage> {
    IPage<ZzMessage> findZzMessagePage(LayuiPage layuiPage, MPJLambdaWrapper<ZzMessage> wrapper);

    IPage<ZzMessagePerUser> findZzMessagePerUserPage(LayuiPage layuiPage, MPJLambdaWrapper<ZzMessage> wrapper);

    IPage<ZzMessageWithName> findZzMessageWithNameMapper(LayuiPage layuiPage, MPJLambdaWrapper<ZzMessage> wrapper);
}
