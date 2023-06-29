package cn.wolfcode.web.modules.zMessage.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessage;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUser;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessageWithName;
import cn.wolfcode.web.modules.zMessage.service.IZzMessageService;
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
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author 冰糖IO
 * @since 2023-06-29
 */
@Controller
@RequestMapping("message")
public class ZzMessageController extends BaseController {

    private static final String LogModule = "ZzMessage";

    @Autowired
    private IZzMessageService entityService;

    @GetMapping("/list.html")
    public String list() {
        return "app/message/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('app:message:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("app/message/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:message:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/message/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:message:list')")
    public ResponseEntity page(LayuiPage layuiPage, @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "startTime", required = false) String startTimeStr,
                               @RequestParam(value = "endTime", required = false) String endTimeStr) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = Optional.ofNullable(startTimeStr).map(s -> LocalDateTime.parse(s, formatter)).orElse(null);
        LocalDateTime endTime = Optional.ofNullable(endTimeStr).map(s -> LocalDateTime.parse(s, formatter)).orElse(null);
        MPJLambdaWrapper<ZzMessage> wrapper = new MPJLambdaWrapper<ZzMessage>()
                .selectAll(ZzMessage.class)
                .selectAll(ZzMessagePerUser.class)
                .leftJoin(ZzMessage.class, ZzMessage::getId, ZzMessagePerUser::getMessageId)
                .like(!StringUtils.isEmpty(title), ZzMessage::getMessageTitle, title)
                .between(startTime != null && endTime != null, ZzMessage::getPublishTime, startTime, endTime);
        IPage<ZzMessageWithName> page = entityService.findZzMessageWithNameMapper(layuiPage, wrapper);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('app:message:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody ZzMessagePerUser entity) {
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('app:message:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody ZzMessagePerUser entity) {
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('app:message:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
