package cn.wolfcode.web.modules.tbVisit.service.impl;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.modules.tbVisit.entity.TbVisit;
import cn.wolfcode.web.modules.tbVisit.entity.TbVisitWithLinkman;
import cn.wolfcode.web.modules.tbVisit.mapper.TbVisitMapper;
import cn.wolfcode.web.modules.tbVisit.service.ITbVisitService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 拜访信息表 服务实现类
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
@Service
public class TbVisitServiceImpl extends ServiceImpl<TbVisitMapper, TbVisit> implements ITbVisitService {

    @Resource
    TbVisitMapper tbVisitMapper;

    @Override
    public IPage<TbVisitWithLinkman> getTbVisitWithLinkman(LayuiPage layuiPage, MPJLambdaWrapper<TbVisit> wrapper) {
        IPage<TbVisitWithLinkman> iPage = tbVisitMapper.selectJoinPage(new Page<>(layuiPage.getPage(), layuiPage.getLimit()),
                TbVisitWithLinkman.class,
                wrapper);
        return iPage;
    }
}
