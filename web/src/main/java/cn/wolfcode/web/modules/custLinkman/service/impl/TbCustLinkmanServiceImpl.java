package cn.wolfcode.web.modules.custLinkman.service.impl;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkmanWithCust;
import cn.wolfcode.web.modules.custLinkman.mapper.TbCustLinkmanMapper;
import cn.wolfcode.web.modules.custLinkman.service.ITbCustLinkmanService;
import cn.wolfcode.web.modules.tbCustomer.entity.TbCustomer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 客户联系人 服务实现类
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
@Service
public class TbCustLinkmanServiceImpl extends ServiceImpl<TbCustLinkmanMapper, TbCustLinkman> implements ITbCustLinkmanService {

    @Resource
    TbCustLinkmanMapper tbCustLinkmanMapper;

    @Override
    public IPage<TbCustLinkmanWithCust> getCustLinkmanWithCust(LayuiPage layuiPage, MPJLambdaWrapper<TbCustLinkman> wrapper) {
        IPage<TbCustLinkmanWithCust> iPage = tbCustLinkmanMapper.selectJoinPage(new Page<>(layuiPage.getPage(), layuiPage.getLimit()), TbCustLinkmanWithCust.class,
                wrapper);
        return iPage;
    }
}
