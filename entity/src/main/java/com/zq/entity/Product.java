package com.zq.entity;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Product implements Serializable {
    @Id
    private String id;
    private String name;
    //起投金额
    private BigDecimal thresholdAmount;
    //投资步长
    private BigDecimal stepAmount;
    //锁定期
    private Integer lockTerm;
    //收益率
    private BigDecimal rewardRate;
    /**
     * @see com.bawei.entity.enums.ProductStatus
     */
    private String status;
    private String memo;
    private Date createAt;
    private String createUser;
    private Date updateAt;
    private String updateUser;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public Product() {
    }

    public Product(String id, String name, BigDecimal thresholdAmount, BigDecimal stepAmount, BigDecimal rewardRate, String status) {
        this.id = id;
        this.name = name;
        this.thresholdAmount = thresholdAmount;
        this.stepAmount = stepAmount;
        this.rewardRate = rewardRate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(BigDecimal thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public BigDecimal getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(BigDecimal stepAmount) {
        this.stepAmount = stepAmount;
    }

    public Integer getLockTerm() {
        return lockTerm;
    }

    public void setLockTerm(Integer lockTerm) {
        this.lockTerm = lockTerm;
    }

    public BigDecimal getRewardRate() {
        return rewardRate;
    }

    public void setRewardRate(BigDecimal rewardRate) {
        this.rewardRate = rewardRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}