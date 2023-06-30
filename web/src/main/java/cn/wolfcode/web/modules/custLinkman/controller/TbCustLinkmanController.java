package cn.wolfcode.web.modules.custLinkman.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkmanWithCust;
import cn.wolfcode.web.modules.custLinkman.service.ITbCustLinkmanService;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.LoginForm;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lmio
 * @since 2023-06-29
 */
@Controller
@RequestMapping("custLinkman")
public class TbCustLinkmanController extends BaseController {

    private static final String LogModule = "TbCustLinkman";
    @Autowired
    private ITbCustomerService customerService;
    @Autowired
    private ITbCustLinkmanService entityService;

    @GetMapping("/list.html")
    public String list() {
        return "app/custLinkman/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('app:custLinkman:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("app/custLinkman/add");
        List<TbCustomer> customers = customerService.list();
        mv.addObject("customers", customers);
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:custLinkman:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/custLinkman/update");
        List<TbCustomer> customers = customerService.list();
        mv.addObject("customers", customers);
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:custLinkman:list')")
    public ResponseEntity page(LayuiPage layuiPage, String parameterName, String sex) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        MPJLambdaWrapper<TbCustLinkman> wrapper = new MPJLambdaWrapper<TbCustLinkman>()
                .selectAll(TbCustLinkman.class)
                .select(TbCustomer::getCustomerName)
                .select(SysUser::getUsername)
                .leftJoin(TbCustomer.class, TbCustomer::getId, TbCustLinkman::getCustId)
                .leftJoin(SysUser.class, SysUser::getUserId, TbCustLinkman::getInputUser)
                .like(!StringUtils.isEmpty(parameterName), TbCustomer::getCustomerName, parameterName)
                .or()
                .eq(!StringUtils.isEmpty(sex), TbCustLinkman::getSex, sex);
        IPage<TbCustLinkmanWithCust> page = entityService.getCustLinkmanWithCust(layuiPage, wrapper);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('app:custLinkman:add')")
    public ResponseEntity<ApiModel> save(HttpServletRequest request, @Validated({AddGroup.class}) @RequestBody TbCustLinkman entity) {
        SysUser user = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUser(user.getUserId());
        entity.setInputTime(LocalDateTime.now());
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('app:custLinkman:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbCustLinkman entity) {
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('app:custLinkman:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
