package com.bezkoder.springjwt.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
/**
 * @author zhangwq
 */
@Entity
@Table(name = "manifest")
public class Manifest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;
    private int goodTypeId; //对应goodType表
    private double weight;
    private int transportType; //0普通件，1快件，2超快件
    @NotBlank
    @Size(max = 200)
    private String beginAddress;
    @NotBlank
    @Size(max = 200)
    private String endAddress;
    private int payType;   //0先付后到，1先到后付
    private double amount;
    @NotBlank
    @Size(max = 20)
    private String receiverPhone;
    @NotBlank
    @Size(max = 20)
    private String receiverName;
    private int status=0;//0未发货、1进行中、2已完成、3已取消

    private Date createDate;
    private int isPay=0;  //是否支付, 默认未支付


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public Manifest() {
        this.createDate = new Date();
        this.status = 0;
        this.isPay=0;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getGoodTypeId() {
        return goodTypeId;
    }

    public void setGoodTypeId(int goodTypeId) {
        this.goodTypeId = goodTypeId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    public String getBeginAddress() {
        return beginAddress;
    }

    public void setBeginAddress(String beginAddress) {
        this.beginAddress = beginAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
