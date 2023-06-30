package cn.wolfcode.web.modules.zMessageMgr.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import cn.wolfcode.web.modules.sys.service.UserService;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessage;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUserDetail;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessageWithName;
import cn.wolfcode.web.modules.zMessage.service.IZzMessageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 冰糖IO
 * @since 2023-06-29
 */
@Controller
@RequestMapping("messageMgr")
public class ZzMessageMgrController extends BaseController {

    private static final String LogModule = "ZzMessageMgr";
    @Autowired
    private IZzMessageService entityService;
    @Autowired
    private UserService userService;

    @GetMapping("/list.html")
    public String list() {
        return "app/messageMgr/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('app:messageMgr:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        mv.setViewName("app/messageMgr/add");
        List<UserInfo> users = userService.list().stream().map(user -> new UserInfo(user.getUserId(), user.getUsername())).collect(Collectors.toList());
        mv.addObject("users", users);
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:messageMgr:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/messageMgr/update");
        List<UserInfo> users = userService.list().stream().map(user -> new UserInfo(user.getUserId(), user.getUsername())).collect(Collectors.toList());
        mv.addObject("users", users);
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:messageMgr:list')")
    public ResponseEntity page(LayuiPage layuiPage, @RequestParam(value = "parameterName", required = false) String parameterName,
                               @RequestParam(value = "startTime", required = false) String startTimeStr,
                               @RequestParam(value = "endTime", required = false) String endTimeStr) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = Optional.ofNullable(startTimeStr).map(s -> LocalDateTime.parse(s, formatter)).orElse(null);
        LocalDateTime endTime = Optional.ofNullable(endTimeStr).map(s -> LocalDateTime.parse(s, formatter)).orElse(null);
        MPJLambdaWrapper<ZzMessage> wrapper = new MPJLambdaWrapper<ZzMessage>()
                .selectAll(ZzMessage.class)
                .select("p.username as publisher_username")
                .select("r.username as receiver_username")
                .leftJoin(SysUser.class, "p", SysUser::getUserId, ZzMessage::getPublisherId)
                .leftJoin(SysUser.class, "r", SysUser::getUserId, ZzMessage::getReceiverId)
                .like(!StringUtils.isEmpty(parameterName), ZzMessage::getMessageTitle, parameterName);
//                .between(startTime != null && endTime != null, ZzMessage::getPublishTime, startTime, endTime);
        IPage<ZzMessageWithName> page = entityService.findZzMessageWithNameMapper(layuiPage, wrapper);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('app:messageMgr:add')")
    public ResponseEntity<ApiModel> save(HttpServletRequest request, @Validated({AddGroup.class}) @RequestBody ZzMessagePerUserDetail entity) {
        SysUser user = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setPublisherId(user.getUserId());
        entity.setPublishTime(LocalDateTime.now());
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('app:messageMgr:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody ZzMessagePerUserDetail entity) {
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('app:messageMgr:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @Data
    public class UserInfo implements Serializable {
        private static final long serialVersionUID = 1L;
        private String userId;
        private String username;

        public UserInfo(String userId, String username) {
            this.userId = userId;
            this.username = username;
        }
    }

}
