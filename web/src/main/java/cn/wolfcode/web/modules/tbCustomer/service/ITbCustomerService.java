package cn.wolfcode.web.modules.tbCustomer.service;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.tbCustomer.entity.TbCustomer;
import cn.wolfcode.web.modules.tbCustomer.entity.TbCustomerWithUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

/**
 * <p>
 * 客户信息 服务类
 * </p>
 *
 * @author lmio
 * @since 2023-06-27
 */
public interface ITbCustomerService extends IService<TbCustomer> {
    public IPage<TbCustomerWithUser> getCustomerWithUser(LayuiPage layuiPage, MPJLambdaWrapper<TbCustomer> wrapper);
}
