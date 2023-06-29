package cn.wolfcode.web.modules.orderInfo.service;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.orderInfo.entity.TbOrderInfo;
import cn.wolfcode.web.modules.orderInfo.entity.TbOrderInfoWithCust;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
public interface ITbOrderInfoService extends IService<TbOrderInfo> {
    public IPage<TbOrderInfoWithCust> getTbOrderInfoWithCust(LayuiPage layuiPage, MPJLambdaWrapper<TbOrderInfo> wrapper);

}
