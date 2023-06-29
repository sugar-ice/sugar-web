package cn.wolfcode.web.modules.tbVisit.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.tbCustomer.entity.TbCustomer;
import cn.wolfcode.web.modules.tbVisit.entity.TbVisit;
import cn.wolfcode.web.modules.tbVisit.entity.TbVisitWithLinkman;
import cn.wolfcode.web.modules.tbVisit.service.ITbVisitService;
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

/**
 * @author lmio
 * @since 2023-06-29
 */
@Controller
@RequestMapping("tbVisit")
public class TbVisitController extends BaseController {

    private static final String LogModule = "TbVisit";
    @Autowired
    private ITbVisitService entityService;

    @GetMapping("/list.html")
    public String list() {
        return "app/tbVisit/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('app:tbVisit:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("app/tbVisit/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:tbVisit:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/tbVisit/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:tbVisit:list')")
    public ResponseEntity page(LayuiPage layuiPage, String parameterName) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        MPJLambdaWrapper<TbVisit> wrapper = new MPJLambdaWrapper<TbVisit>()
                .selectAll(TbVisitWithLinkman.class)
                .select(TbCustLinkman::getLinkman)
                .leftJoin(TbCustLinkman.class, TbCustLinkman::getId, TbVisit::getLinkmanId)
                .leftJoin(TbCustomer.class, TbCustomer::getId, TbVisit::getCustId)
                .like(!StringUtils.isEmpty(parameterName), TbCustLinkman::getLinkman, parameterName)
                .or()
                .like(!StringUtils.isEmpty(parameterName), TbCustomer::getCustomerName, parameterName);
        IPage<TbVisitWithLinkman> page = entityService.getTbVisitWithLinkman(layuiPage, wrapper);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('app:tbVisit:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbVisit entity) {
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('app:tbVisit:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbVisit entity) {
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('app:tbVisit:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
