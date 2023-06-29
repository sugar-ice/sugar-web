package cn.wolfcode.web.modules.orderInfo.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.orderInfo.entity.TbOrderInfo;
import cn.wolfcode.web.modules.orderInfo.entity.TbOrderInfoWithCust;
import cn.wolfcode.web.modules.orderInfo.service.ITbOrderInfoService;
import cn.wolfcode.web.modules.tbCustomer.entity.TbCustomer;
import cn.wolfcode.web.modules.tbCustomer.service.ITbCustomerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lmio
 * @since 2023-06-29
 */
@Controller
@RequestMapping("orderInfo")
public class TbOrderInfoController extends BaseController {

    private static final String LogModule = "TbOrderInfo";
    @Resource
    ITbCustomerService customerService;
    @Autowired
    private ITbOrderInfoService entityService;

    @GetMapping("/list.html")
    public String list() {
        return "app/orderInfo/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('app:orderInfo:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("app/orderInfo/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:orderInfo:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/custLinkman/update");
        List<TbCustomer> customers = customerService.list();
        mv.addObject("customers", customers);
        mv.setViewName("app/orderInfo/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:orderInfo:list')")
    public ResponseEntity page(LayuiPage layuiPage, String parameterName) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        MPJLambdaWrapper<TbOrderInfo> wrapper = new MPJLambdaWrapper<TbOrderInfo>()
                .selectAll(TbOrderInfo.class)
                .select(TbCustomer::getCustomerName)
                .leftJoin(TbCustomer.class, TbCustomer::getId, TbOrderInfo::getCustId)
                .like(!StringUtils.isEmpty(parameterName), TbCustomer::getCustomerName, parameterName)
                .or()
                .like(!StringUtils.isEmpty(parameterName), TbOrderInfo::getProdName, parameterName)
                .or()
                .like(!StringUtils.isEmpty(parameterName), TbCustomer::getInputTime, parameterName);
        IPage<TbOrderInfoWithCust> page = entityService.getTbOrderInfoWithCust(layuiPage, wrapper);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('app:orderInfo:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbOrderInfo entity) {
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('app:orderInfo:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbOrderInfo entity) {
        TbOrderInfo orderinfo = entityService.getById(entity.getId());
        if (orderinfo.getStatus() == 1) {
            if (entity.getStatus() == 2) {
                orderinfo.setStatus(2);
                orderinfo.setReceiveTime(LocalDateTime.now());
                entityService.updateById(orderinfo);
                return ResponseEntity.ok(ApiModel.ok());
            } else {
                return ResponseEntity.badRequest().body(ApiModel.error("发货后不可以修改基本信息！"));
            }
        }
        if (orderinfo.getStatus() == 2) {
            return ResponseEntity.badRequest().body(ApiModel.error("收货后不可以进行其他操作！"));
        }
        if (entity.getStatus() == 2) {
            return ResponseEntity.badRequest().body(ApiModel.error("还未发货！"));
        }
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('app:orderInfo:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
