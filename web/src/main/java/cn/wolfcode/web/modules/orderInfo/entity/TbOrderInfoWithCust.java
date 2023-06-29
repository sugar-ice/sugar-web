package cn.wolfcode.web.modules.orderInfo.entity;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class TbOrderInfoWithCust extends TbOrderInfo {
    /**
     * 企业名称
     */
    @NotBlank(message = "企业名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 100, message = "企业名称不能超过100字", groups = {AddGroup.class, UpdateGroup.class})
    private String customerName;
}