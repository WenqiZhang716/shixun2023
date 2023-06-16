package com.bezkoder.springjwt.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author zhangwq
 */
@Entity
@Table(name = "bill",uniqueConstraints = {
        @UniqueConstraint(columnNames = "manifestId")
})
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;

    private int manifestId;
    private double payoff;
    private double amount;
    private double payment; //amount*payoff
    private int bankCardId;
    private int status=0;//0未支付，1已支付，2已取消,3无需支付
    //先付后到是银行卡，先到后付是现金
    private int payWay=0;//0银行卡，1其他方式
    @NotBlank
    private String payName;//支付人姓名
    private String payPhone;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Bill() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayPhone() {
        return payPhone;
    }

    public void setPayPhone(String payPhone) {
        this.payPhone = payPhone;
    }

    public int getManifestId() {
        return manifestId;
    }

    public void setManifestId(int manifestId) {
        this.manifestId = manifestId;
    }

    public double getPayoff() {
        return payoff;
    }

    public void setPayoff(double payoff) {
        this.payoff = payoff;
    }

    public int getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(int bankCardId) {
        this.bankCardId = bankCardId;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
