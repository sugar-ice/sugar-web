package cn.wolfcode.web.modules.tbContract.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import cn.wolfcode.web.modules.tbContract.entity.TbContract;
import cn.wolfcode.web.modules.tbContract.entity.TbContractWithCust;
import cn.wolfcode.web.modules.tbContract.service.ITbContractService;
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
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lmio
 * @since 2023-06-29
 */
@Controller
@RequestMapping("tbContract")
public class TbContractController extends BaseController {

    private static final String LogModule = "TbContract";
    @Resource
    ITbCustomerService customerService;
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
        List<TbCustomer> customers = customerService.list();
        mv.addObject("customers", customers);
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:tbContract:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/tbContract/update");
        List<TbCustomer> customers = customerService.list();
        mv.addObject("customers", customers);
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:tbContract:list')")
    public ResponseEntity page(LayuiPage layuiPage,
                               String parameterName,
                               String affixSealStatus,
                               String auditStatu,
                               String nullifyStatus) {
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
                .like(!StringUtils.isEmpty(parameterName), TbContract::getContractCode, parameterName)
                .or()
                .eq(!StringUtils.isEmpty(affixSealStatus), TbContract::getAffixSealStatus, affixSealStatus)
                .or()
                .eq(!StringUtils.isEmpty(auditStatu), TbContract::getAuditStatus, auditStatu)
                .or()
                .eq(!StringUtils.isEmpty(nullifyStatus), TbContract::getNullifyStatus, nullifyStatus);
        IPage<TbContractWithCust> page = entityService.getContractWithCust(layuiPage, wrapper);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('app:tbContract:add')")
    public ResponseEntity<ApiModel> save(HttpServletRequest request, @Validated({AddGroup.class}) @RequestBody TbContract entity) {
        SysUser user = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUser(user.getUserId());
        entity.setInputTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('app:tbContract:update')")
    public ResponseEntity<ApiModel> update(HttpServletRequest request, @Validated({UpdateGroup.class}) @RequestBody TbContract entity) {
        SysUser user = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUser(user.getUserId());
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
