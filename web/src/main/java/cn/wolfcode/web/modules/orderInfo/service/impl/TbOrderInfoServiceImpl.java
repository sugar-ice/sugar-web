package cn.wolfcode.web.modules.orderInfo.service.impl;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.orderInfo.entity.TbOrderInfo;
import cn.wolfcode.web.modules.orderInfo.entity.TbOrderInfoWithCust;
import cn.wolfcode.web.modules.orderInfo.mapper.TbOrderInfoMapper;
import cn.wolfcode.web.modules.orderInfo.service.ITbOrderInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
@Service
public class TbOrderInfoServiceImpl extends ServiceImpl<TbOrderInfoMapper, TbOrderInfo> implements ITbOrderInfoService {
    @Resource
    TbOrderInfoMapper orderInfoMapper;

    @Override
    public IPage<TbOrderInfoWithCust> getTbOrderInfoWithCust(LayuiPage layuiPage, MPJLambdaWrapper<TbOrderInfo> wrapper) {
        IPage<TbOrderInfoWithCust> iPage = orderInfoMapper.selectJoinPage(new Page<>(layuiPage.getPage(), layuiPage.getLimit()),
                TbOrderInfoWithCust.class,
                wrapper);
        return iPage;
    }
}
