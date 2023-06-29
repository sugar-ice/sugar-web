package cn.wolfcode.web.modules.tbVisit.entity;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class TbVisitWithLinkman extends TbVisit {
    /**
     * 联系人名字
     */
    @NotBlank(message = "联系人名字不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 30, message = "联系人名字不能超过30字", groups = {AddGroup.class, UpdateGroup.class})
    private String linkman;
}
