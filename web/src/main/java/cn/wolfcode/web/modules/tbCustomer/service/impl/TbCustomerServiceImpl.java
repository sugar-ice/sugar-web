package cn.wolfcode.web.modules.tbCustomer.service.impl;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.tbCustomer.entity.TbCustomer;
import cn.wolfcode.web.modules.tbCustomer.entity.TbCustomerWithUser;
import cn.wolfcode.web.modules.tbCustomer.mapper.TbCustomerMapper;
import cn.wolfcode.web.modules.tbCustomer.service.ITbCustomerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author lmio
 * @since 2023-06-27
 */
@Service
public class TbCustomerServiceImpl extends ServiceImpl<TbCustomerMapper, TbCustomer> implements ITbCustomerService {

    @Resource
    TbCustomerMapper tbCustomerMapper;

    @Override
    public IPage<TbCustomerWithUser> getCustomerWithUser(LayuiPage layuiPage, MPJLambdaWrapper<TbCustomer> wrapper) {
        IPage<TbCustomerWithUser> iPage = tbCustomerMapper.selectJoinPage(new Page<>(layuiPage.getPage(), layuiPage.getLimit()),
                TbCustomerWithUser.class,
                wrapper);
        return iPage;
    }
}
