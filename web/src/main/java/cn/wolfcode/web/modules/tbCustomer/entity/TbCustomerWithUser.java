package cn.wolfcode.web.modules.tbCustomer.entity;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.ImportGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class TbCustomerWithUser extends TbCustomer {
    @NotBlank(message = "账号不能为空", groups = {AddGroup.class, ImportGroup.class})
    @Length(max = 30, message = "账号不能超出30个字符", groups = {AddGroup.class, ImportGroup.class})
    private String username;
}
