package cn.wolfcode.web.modules.custLinkman.service;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkmanWithCust;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 客户联系人 服务类
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
public interface ITbCustLinkmanService extends IService<TbCustLinkman> {
    public IPage<TbCustLinkmanWithCust> getCustLinkmanWithCust(LayuiPage layuiPage);
}
