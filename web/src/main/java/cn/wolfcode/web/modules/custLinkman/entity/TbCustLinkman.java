package cn.wolfcode.web.modules.custLinkman.entity;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.UpdateGroup;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 客户联系人
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
public class TbCustLinkman implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 客户id
     */
    @NotBlank(message = "客户id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String custId;

    /**
     * 联系人名字
     */
    @NotBlank(message = "联系人名字不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 30, message = "联系人名字不能超过30字", groups = {AddGroup.class, UpdateGroup.class})
    private String linkman;

    /**
     * 性别 1 男 0 女
     */
    @NotNull(message = "性别不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "性别错误", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 1, message = "性别错误", groups = {AddGroup.class, UpdateGroup.class})
    private Integer sex;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "年龄不能小于0", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 100, message = "年龄不能超过100", groups = {AddGroup.class, UpdateGroup.class})
    private Integer age;

    /**
     * 联系人电话
     */
    @NotBlank(message = "联系人电话不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "\\d{1,20}", message = "联系人电话格式错误", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 20, message = "联系人电话不能超过20字", groups = {AddGroup.class, UpdateGroup.class})
    private String phone;

    /**
     * 职位
     */
    @Length(max = 20, message = "职位不能超过20字", groups = {AddGroup.class, UpdateGroup.class})
    private String position;

    /**
     * 部门
     */
    @Length(max = 20, message = "部门不能超过20字", groups = {AddGroup.class, UpdateGroup.class})
    private String department;

    /**
     * 任职状态
     */
    @NotNull(message = "任职状态不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "任职状态错误", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 1, message = "任职状态错误", groups = {AddGroup.class, UpdateGroup.class})
    private Integer employmentStatus = 0;

    /**
     * 录入人
     */
    private String inputUser;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;

    /**
     * 备注信息
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getInputUser() {
        return inputUser;
    }

    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }
    public LocalDateTime getInputTime() {
        return inputTime;
    }

    public void setInputTime(LocalDateTime inputTime) {
        this.inputTime = inputTime;
    }

    @Override
    public String toString() {
        return "TbCustLinkman{" +
            "id=" + id +
            ", custId=" + custId +
            ", linkman=" + linkman +
            ", sex=" + sex +
            ", age=" + age +
            ", phone=" + phone +
            ", position=" + position +
            ", department=" + department +
            ", remark=" + remark +
            ", inputUser=" + inputUser +
            ", inputTime=" + inputTime +
        "}";
    }
}
