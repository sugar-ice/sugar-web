package cn.wolfcode.web.modules.tbCustomer.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.CityUtils;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.wolfcode.web.modules.tbCustomer.entity.TbCustomer;
import cn.wolfcode.web.modules.tbCustomer.service.ITbCustomerService;
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
 * @since 2023-06-27
 */
@Controller
@RequestMapping("tbCustomer")
public class TbCustomerController extends BaseController {

    @Autowired
    private ITbCustomerService entityService;

    private static final String LogModule = "TbCustomer";

    @GetMapping("/list.html")
    public String list() {
        return "app/tbCustomer/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('app:tbCustomer:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("app/tbCustomer/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:tbCustomer:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/tbCustomer/update");
        mv.addObject("citys", CityUtils.citys);
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:tbCustomer:list')")
    public ResponseEntity page(LayuiPage layuiPage, String parameterName) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        LambdaQueryChainWrapper<TbCustomer> wrapper = entityService.lambdaQuery();
        wrapper.like(!StringUtils.isEmpty(parameterName), TbCustomer::getCustomerName, parameterName)
                .or()
                .like(!StringUtils.isEmpty(parameterName), TbCustomer::getLegalLeader, parameterName);
        //把省份的信息由地区编码（数字）改成地区名称
        page = wrapper.page(page);
        List<TbCustomer> records =  page.getRecords();
        for (TbCustomer record : records) {
            String city_name = CityUtils.getCityValue(record.getProvince());
            //通过数字编码获取具体的地区名称
            record.setProvince(city_name);//把原本的地区的数字编码覆盖成具体的地区名称
        }
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module =LogModule)
    @PreAuthorize("hasAuthority('app:tbCustomer:add')")
    public ResponseEntity<ApiModel> save(HttpServletRequest request, @Validated({AddGroup.class}) @RequestBody TbCustomer entity) {
        SysUser user = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUserId(user.getUserId());
        entity.setInputTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('app:tbCustomer:update')")
    public ResponseEntity<ApiModel> update(HttpServletRequest request, @Validated({UpdateGroup.class}) @RequestBody TbCustomer entity) {
        SysUser user = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUserId(user.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('app:tbCustomer:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
