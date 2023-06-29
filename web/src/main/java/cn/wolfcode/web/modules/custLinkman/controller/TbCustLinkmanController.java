package cn.wolfcode.web.modules.custLinkman.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.log.LogModules;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.wolfcode.web.modules.custLinkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.custLinkman.service.ITbCustLinkmanService;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
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
@RequestMapping("custLinkman")
public class TbCustLinkmanController extends BaseController {

    @Autowired
    private ITbCustLinkmanService entityService;

    private static final String LogModule = "TbCustLinkman";

    @GetMapping("/list.html")
    public String list() {
        return "app/custLinkman/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('app:custLinkman:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("app/custLinkman/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:custLinkman:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/custLinkman/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:custLinkman:list')")
    public ResponseEntity page(LayuiPage layuiPage) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(entityService.page(page)));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module =LogModule)
    @PreAuthorize("hasAuthority('app:custLinkman:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbCustLinkman entity) {
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
