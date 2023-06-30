package cn.wolfcode.web.modules.zMessage.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUser;
import cn.wolfcode.web.modules.zMessage.entity.ZzMessagePerUserDetail;
import cn.wolfcode.web.modules.zMessage.service.IZzMessagePerUserService;
import cn.wolfcode.web.modules.zMessage.service.IZzMessageService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.entitys.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 冰糖IO
 * @since 2023-06-29
 */
@Controller
@RequestMapping("message")
public class ZzMessageController extends BaseController {

    private static final String LogModule = "ZzMessage";

    @Autowired
    private IZzMessageService messageService;

    @Autowired
    private IZzMessagePerUserService messagePerUserService;


    @GetMapping("/msgList.html")
    public String list() {
        return "app/message/list";
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('app:message:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("app/message/read");
        mv.addObject("obj", messageService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('app:message:list')")
    public ResponseEntity list(LayuiPage layuiPage, HttpServletRequest request) {
        String userId = ((SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY)).getUserId();
        layuiPage = layuiPage == null ? new LayuiPage() : layuiPage;
        List<ZzMessagePerUserDetail> msgs = messagePerUserService.checkMsg(null, null, userId, layuiPage.getPage(), layuiPage.getLimit());
        for (ZzMessagePerUserDetail msg : msgs) {
            if (msg.getIsRead() == null) {
                ZzMessagePerUser newMsg = new ZzMessagePerUser();
                newMsg.setUserId(userId);
                newMsg.setMessageId(msg.getMessageId());
                newMsg.setIsRead(0);
                newMsg.setIsDeleted(0);

                messagePerUserService.save(newMsg);
                System.out.println(newMsg);
            }
        }
        Page<ZzMessagePerUserDetail> page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        page.setRecords(msgs);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @RequestMapping("raw")
    @PreAuthorize("hasAuthority('app:message:list')")
    @ResponseBody
    public List<ZzMessagePerUserDetail> raw(LayuiPage layuiPage, HttpServletRequest request) {
        String userId = ((SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY)).getUserId();
        layuiPage = layuiPage == null ? new LayuiPage() : layuiPage;
        List<ZzMessagePerUserDetail> msgs = messagePerUserService.checkMsg(null, 0, userId, layuiPage.getPage(), layuiPage.getLimit());
        return msgs;
    }


    @RequestMapping("checkMsg")
    @PreAuthorize("hasAuthority('app:message:list')")
    @ResponseBody
    public boolean checkMsg(HttpServletRequest request) {
        String userId = ((SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY)).getUserId();
        return !messagePerUserService.checkMsg(0, 0, userId, 1, 1).isEmpty();
    }

    @PostMapping("read/{id}")
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PreAuthorize("hasAuthority('app:message:update')")
    public ResponseEntity<ApiModel> read(HttpServletRequest request, @PathVariable("id") String id) {
        String userId = ((SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY)).getUserId();
        ZzMessagePerUser entity = messagePerUserService.getById(id);
        if (entity == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiModel.error("数据不存在"));

        if (!entity.getUserId().equals(userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiModel.error("无权操作"));

        if (entity.getIsRead() == 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiModel.error("你已经读过这条消息了"));

        entity.setIsRead(1);
        // 执行更新操作
        boolean updated = messagePerUserService.updateById(entity);
        if (updated) {
            return ResponseEntity.ok(ApiModel.ok());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiModel.error("更新失败"));
        }
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('app:message:update')")
    public ResponseEntity<ApiModel> delete(HttpServletRequest request, @PathVariable("id") String id) {
        String userId = ((SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY)).getUserId();
        ZzMessagePerUser entity = messagePerUserService.getById(id);
        if (entity == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiModel.error("数据不存在"));

        entity.setIsDeleted(1);
        // 执行更新操作
        boolean updated = messagePerUserService.updateById(entity);
        if (updated) {
            return ResponseEntity.ok(ApiModel.ok());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiModel.error("更新失败"));
        }
    }

}
