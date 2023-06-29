package cn.wolfcode.web.modules.tbVisit.service;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.tbVisit.entity.TbVisit;
import cn.wolfcode.web.modules.tbVisit.entity.TbVisitWithLinkman;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

/**
 * <p>
 * 拜访信息表 服务类
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
public interface ITbVisitService extends IService<TbVisit> {
    public IPage<TbVisitWithLinkman> getTbVisitWithLinkman(LayuiPage layuiPage, MPJLambdaWrapper<TbVisit> wrapper);

}
