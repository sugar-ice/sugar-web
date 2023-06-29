package cn.wolfcode.web.modules.tbContract.service;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.tbContract.entity.TbContract;
import cn.wolfcode.web.modules.tbContract.entity.TbContractWithCust;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

/**
 * <p>
 * 合同信息 服务类
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
public interface ITbContractService extends IService<TbContract> {
    public IPage<TbContractWithCust> getContractWithCust(LayuiPage layuiPage, MPJLambdaWrapper<TbContract> wrapper);

}
