package com.bezkoder.springjwt.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "storeInfo")
public class StoreInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int goodsId;
    @Column(name = "apply_id",columnDefinition="DEFAULT 0")
    private int applyId;

    private int areaId;

    private int shelveX;

    private int shelveY;

    private int shelveZ;

    private int time = 1;

    private double fee;

    public StoreInfo(int goodsId, int areaId,int shelveX, int shelveY, int shelveZ,int time, double fee,int applyId) {
        this.goodsId = goodsId;
        this.areaId = areaId;
        this.shelveX = shelveX;
        this.shelveY = shelveY;
        this.shelveZ = shelveZ;
        this.time = time;
        this.fee = fee;
        this.applyId = applyId;
    }

    public StoreInfo() {

    }

    public int getShelveZ() {
        return shelveZ;
    }

    public void setShelveZ(int shelveZ) {
        this.shelveZ = shelveZ;
    }

    public int getId() {
        return id;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }


    public int getShelveX() {
        return shelveX;
    }

    public void setShelveX(int shelveX) {
        this.shelveX = shelveX;
    }

    public int getShelveY() {
        return shelveY;
    }

    public void setShelveY(int shelveY) {
        this.shelveY = shelveY;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StoreInfo storeInfo = (StoreInfo) o;
        return id == storeInfo.id &&
                goodsId == storeInfo.goodsId &&
                areaId == storeInfo.areaId &&
                shelveX == storeInfo.shelveX &&
                shelveY == storeInfo.shelveY &&
                time == storeInfo.time &&
                Double.compare(storeInfo.fee, fee) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, goodsId, areaId, shelveX, shelveY, time, fee);
    }
}
