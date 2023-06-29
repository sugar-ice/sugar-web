package cn.wolfcode.web.modules.tbContract.service.impl;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.tbContract.entity.TbContract;
import cn.wolfcode.web.modules.tbContract.entity.TbContractWithCust;
import cn.wolfcode.web.modules.tbContract.mapper.TbContractMapper;
import cn.wolfcode.web.modules.tbContract.service.ITbContractService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 合同信息 服务实现类
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
@Service
public class TbContractServiceImpl extends ServiceImpl<TbContractMapper, TbContract> implements ITbContractService {

    @Resource
    TbContractMapper tbContractMapper;

    @Override
    public IPage<TbContractWithCust> getContractWithCust(LayuiPage layuiPage, MPJLambdaWrapper<TbContract> wrapper) {
        IPage<TbContractWithCust> iPage = tbContractMapper.selectJoinPage(new Page<>(layuiPage.getPage(), layuiPage.getLimit()),
                TbContractWithCust.class,
                wrapper);
        return iPage;
    }
}
