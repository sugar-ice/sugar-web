package cn.wolfcode.web.modules.tbCustomer.entity;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.UpdateGroup;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author lmio
 * @since 2023-06-27
 */
public class TbCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 企业名称
     */
    @NotBlank(message = "企业名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 100, message = "企业名称不能超过100字", groups = {AddGroup.class, UpdateGroup.class})
    private String customerName;

    /**
     * 法定代表人
     */
    @NotBlank(message = "法定代表人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 30, message = "法定代表人不能超过30字", groups = {AddGroup.class, UpdateGroup.class})
    private String legalLeader;

    /**
     * 成立时间
     */
    @NotNull(message = "成立日期不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private LocalDate registerDate;

    /**
     * 经营状态, 0 开业、1 注销、2 破产
     */
    @NotNull(message = "经营状态不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "经营状态错误", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 2, message = "经营状态错误", groups = {AddGroup.class, UpdateGroup.class})
    private Integer openStatus = 0;


    /**
     * 所属地区省份
     */
    @NotBlank(message = "所属省份不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String province;

    /**
     * 注册资本,(万元)
     */
    @NotBlank(message = "注册资本不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 20, message = "注册资本不能超过20字", groups = {AddGroup.class, UpdateGroup.class})
    private String regCapital;

    /**
     * 所属行业
     */
    @NotBlank(message = "所属行业不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 30, message = "所属行业不能超过30字", groups = {AddGroup.class, UpdateGroup.class})
    private String industry;

    /**
     * 经营范围
     */
    @NotBlank(message = "经营范围不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 500, message = "经营范围不能超过500字", groups = {AddGroup.class, UpdateGroup.class})
    private String scope;

    /**
     * 注册地址
     */
    @NotBlank(message = "注册地址不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 500, message = "注册地址不能超过500字", groups = {AddGroup.class, UpdateGroup.class})
    private String regAddr;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 录入人
     */
    private String inputUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getLegalLeader() {
        return legalLeader;
    }

    public void setLegalLeader(String legalLeader) {
        this.legalLeader = legalLeader;
    }
    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }
    public Integer getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }
    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }
    public LocalDateTime getInputTime() {
        return inputTime;
    }

    public void setInputTime(LocalDateTime inputTime) {
        this.inputTime = inputTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId;
    }

    @Override
    public String toString() {
        return "TbCustomer{" +
            "id=" + id +
            ", customerName=" + customerName +
            ", legalLeader=" + legalLeader +
            ", registerDate=" + registerDate +
            ", openStatus=" + openStatus +
            ", province=" + province +
            ", regCapital=" + regCapital +
            ", industry=" + industry +
            ", scope=" + scope +
            ", regAddr=" + regAddr +
            ", inputTime=" + inputTime +
            ", updateTime=" + updateTime +
            ", inputUserId=" + inputUserId +
        "}";
    }
}
