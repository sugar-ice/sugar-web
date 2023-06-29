package cn.wolfcode.web.modules.tbContract.entity;

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
 * 合同信息
 * </p>
 *
 * @author lmio
 * @since 2023-06-29
 */
public class TbContract implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    private String id;

    /**
     * 客户id
     */
    @NotBlank(message = "客户id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String custId;

    /**
     * 合同名称
     */
    @NotBlank(message = "合同名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 100, message = "合同名称不能超过100字", groups = {AddGroup.class, UpdateGroup.class})
    private String contractName;

    /**
     * 合同编码
     */
    @NotBlank(message = "合同编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 50, message = "合同编码不能超过50字", groups = {AddGroup.class, UpdateGroup.class})
    private String contractCode;

    /**
     * 合同金额
     */
    @NotNull(message = "合同金额不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "合同金额必须为正数", groups = {AddGroup.class, UpdateGroup.class})
    private Integer amounts;

    /**
     * 合同生效开始时间
     */
    @NotNull(message = "合同开始日期不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private LocalDate startDate;

    /**
     * 合同生效结束时间
     */
    @NotNull(message = "合同结束日期不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private LocalDate endDate;

    /**
     * 合同内容
     */
    @NotBlank(message = "合同内容不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String content;

    /**
     * 是否盖章确认 0 否 1 是
     */
    @NotNull(message = "是否盖章不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "是否盖章错误", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 1, message = "是否盖章错误", groups = {AddGroup.class, UpdateGroup.class})
    private Integer affixSealStatus;

    /**
     * 审核状态 0 未审核 1 审核通过 -1 审核不通过
     */
    @NotNull(message = "审核状态不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = -1, message = "审核状态错误", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 1, message = "审核状态错误", groups = {AddGroup.class, UpdateGroup.class})
    private Integer auditStatus;

    /**
     * 是否作废 1 作废 0 在用
     */
    @NotNull(message = "是否作废不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "是否作废错误", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 1, message = "是否作废错误", groups = {AddGroup.class, UpdateGroup.class})
    private Integer nullifyStatus;

    /**
     * 录入人
     */
    private String inputUser;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

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
    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getAffixSealStatus() {
        return affixSealStatus;
    }

    public void setAffixSealStatus(Integer affixSealStatus) {
        this.affixSealStatus = affixSealStatus;
    }
    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
    public Integer getNullifyStatus() {
        return nullifyStatus;
    }

    public void setNullifyStatus(Integer nullifyStatus) {
        this.nullifyStatus = nullifyStatus;
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
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TbContract{" +
            "id=" + id +
            ", custId=" + custId +
            ", contractName=" + contractName +
            ", contractCode=" + contractCode +
            ", amounts=" + amounts +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", content=" + content +
            ", affixSealStatus=" + affixSealStatus +
            ", auditStatus=" + auditStatus +
            ", nullifyStatus=" + nullifyStatus +
            ", inputUser=" + inputUser +
            ", inputTime=" + inputTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
