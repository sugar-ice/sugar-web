package cn.wolfcode.web.modules.tbContract.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.tbContract.entity.TbContract;
import cn.wolfcode.web.modules.tbContract.entity.TbContractWithCust;
import cn.wolfcode.web.modules.tbContract.service.ITbContractService;
import cn.wolfcode.web.modules.tbCustomer.entity.TbCustomer;
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

import java.time.LocalDateTime;

/**
 * @author lmio
 * @since 2023-06-29
 */
@Controller
@RequestMapping("tbContract")
public class TbContractController extends BaseController {

    private static final String LogModule = "TbContract";
    @Autowired
    private ITbContractService entityService;

    @GetMapping("/list.html")
    public String list() {
        return "app/tbContract/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('app:tbContract:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("app/tbContract/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:tbContract:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/tbContract/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:tbContract:list')")
    public ResponseEntity page(LayuiPage layuiPage, String parameterName) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        MPJLambdaWrapper<TbContract> wrapper = new MPJLambdaWrapper<TbContract>()
                .selectAll(TbContract.class)
                .select(TbCustomer::getCustomerName)
                .select(SysUser::getUsername)
                .leftJoin(TbCustomer.class, TbCustomer::getId, TbCustLinkman::getCustId)
                .leftJoin(SysUser.class, SysUser::getUserId, TbContract::getInputUser)
                .like(!StringUtils.isEmpty(parameterName), TbContract::getContractName, parameterName)
                .or()
                .like(!StringUtils.isEmpty(parameterName), TbCustomer::getCustomerName, parameterName)
                .or()
                .like(!StringUtils.isEmpty(parameterName), SysUser::getUsername, parameterName)
                .or()
                .like(!StringUtils.isEmpty(parameterName), TbContract::getContractCode, parameterName);
        IPage<TbContractWithCust> page = entityService.getContractWithCust(layuiPage, wrapper);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('app:tbContract:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbContract entity) {
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('app:tbContract:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbContract entity) {
        entity.setInputTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        TbContract contract = entityService.getById(entity.getId());
        if (contract.getAuditStatus() != 0) {
            return ResponseEntity.badRequest().body(ApiModel.error("未通过审核不能修改！"));
        }
        if (entity.getNullifyStatus() == 1) {
            if (contract.getAffixSealStatus() == 1) {
                return ResponseEntity.badRequest().body(ApiModel.error("盖章的合同不可以作废！"));
            }
        }
        if (contract.getNullifyStatus() == 1) {
            return ResponseEntity.badRequest().body(ApiModel.error("作废的合同不能做任何操作！"));
        }
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('app:tbContract:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
