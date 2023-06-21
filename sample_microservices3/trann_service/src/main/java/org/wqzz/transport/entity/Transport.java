package org.wqzz.transport.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;


/**
 * @author zhangwq
 */
@Entity
@Table(name = "transport")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int manifestId;
    @NotBlank
    @Size(max = 200)
    private String step;
    private int stepId=0;
    private int status=0;//0未抵达，1已抵达，2已离开
    private Date arriveTime;
    private Date leaveTime;
    private int type=0;//0系统自动，1人工
    private int transporterId=0;//系统自动时为0，人工为运送者id
    private int orders=0;
    private int isValid=0; //0未进入定时模拟，1已经入定时模拟

    public Transport() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrders() {
        return orders;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getManifestId() {
        return manifestId;
    }

    public void setManifestId(int manifestId) {
        this.manifestId = manifestId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(int transporterId) {
        this.transporterId = transporterId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }
}
