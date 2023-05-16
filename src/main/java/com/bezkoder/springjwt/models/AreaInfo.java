package com.bezkoder.springjwt.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "areaInfo",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "areaName")
        })
public class AreaInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 128)
    private String areaName;

    private int type;

    private double totalSpace;

    private double remainSpace;

    private double cost;

    //货架编号
    private  int shelveNum=4;

    //每个货架层数
    private  int shelveLayer=4;

    //每层格子数
    private int shelveLayerSpace=10;

    private int remain1=40;

    private int remain2=40;

    private int remain3=40;

    private int remain4=40;

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    private int totalSum=0;

    public AreaInfo(String areaName, int type, double totalSpace, double remainSpace, double cost, int shelveNum, int shelveLayer,int shelveLayerSpace,int remain1, int remain2, int remain3,int remain4,int totalSum) {
        this.areaName = areaName;
        this.type = type;
        this.totalSpace = totalSpace;
        this.remainSpace = remainSpace;
        this.cost = cost;
        this.shelveNum = shelveNum;
        this.shelveLayer = shelveLayer;
        this.shelveLayerSpace = shelveLayerSpace;
        this.remain1 = remain1;
        this.remain2 = remain2;
        this.remain3 = remain3;
        this.remain4 = remain4;
        this.totalSum = totalSum;
    }

    public AreaInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(double totalSpace) {
        this.totalSpace = totalSpace;
    }

    public double getRemainSpace() {
        return remainSpace;
    }

    public void setRemainSpace(double remainSpace) {
        this.remainSpace = remainSpace;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getShelveNum() {
        return shelveNum;
    }

    public void setShelveNum(int shelveNum) {
        this.shelveNum = shelveNum;
    }

    public int getShelveLayer() {
        return shelveLayer;
    }

    public int getRemain1() {
        return remain1;
    }

    public void setRemain1(int remain1) {
        this.remain1 = remain1;
    }

    public int getRemain2() {
        return remain2;
    }

    public void setRemain2(int remain2) {
        this.remain2 = remain2;
    }

    public int getRemain3() {
        return remain3;
    }

    public void setRemain3(int remain3) {
        this.remain3 = remain3;
    }

    public int getRemain4() {
        return remain4;
    }

    public void setRemain4(int remain4) {
        this.remain4 = remain4;
    }

    public void setShelveLayer(int shelveLayer) {
        this.shelveLayer = shelveLayer;
    }

    public int getShelveLayerSpace() {
        return shelveLayerSpace;
    }

    public void setShelveLayerSpace(int shelveLayerSpace) {
        this.shelveLayerSpace = shelveLayerSpace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AreaInfo areaInfo = (AreaInfo) o;
        return id == areaInfo.id && type == areaInfo.type && Double.compare(areaInfo.totalSpace, totalSpace) == 0 && Double.compare(areaInfo.remainSpace, remainSpace) == 0 && Double.compare(areaInfo.cost, cost) == 0 && shelveNum == areaInfo.shelveNum && shelveLayer == areaInfo.shelveLayer && areaName.equals(areaInfo.areaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, areaName, type, totalSpace, remainSpace, cost, shelveNum, shelveLayer);
    }

    @Override
    public String toString() {
        return "AreaInfo{" +
                "id=" + id +
                ", areaName='" + areaName + '\'' +
                ", type=" + type +
                ", totalSpace=" + totalSpace +
                ", remainSpace=" + remainSpace +
                ", cost=" + cost +
                ", shelveNum=" + shelveNum +
                ", shelveLayer=" + shelveLayer +
                '}';
    }

}
