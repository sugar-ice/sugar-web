package cn.wolfcode.web.modules.demoapp.service.impl;

import cn.wolfcode.web.modules.demoapp.entity.DemoApp;
import cn.wolfcode.web.modules.demoapp.mapper.DemoAppMapper;
import cn.wolfcode.web.modules.demoapp.service.IDemoAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmio
 * @since 2023-06-26
 */
@Service
public class DemoAppServiceImpl extends ServiceImpl<DemoAppMapper, DemoApp> implements IDemoAppService {

}
