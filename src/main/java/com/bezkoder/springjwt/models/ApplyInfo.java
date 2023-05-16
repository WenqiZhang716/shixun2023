package com.bezkoder.springjwt.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * @author zhangwq
 */

@Entity
@Table(	name = "applyInfo")
public class ApplyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private int userId;
    @NotNull
    private int goodsId;
    @NotNull
    private int type = 0;
    //0默认入库，1是出库

    @NotNull
    private int num=0;

    private Date date;

    public ApplyInfo() {
        this.date = new Date();
    }

    public ApplyInfo(int userId,int goodsId,int type,int num) {
        this.userId = userId;
        this.goodsId = goodsId;
        this.type = type;
        this.date = new Date();
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplyInfo applyInfo = (ApplyInfo) o;
        return id == applyInfo.id &&
                userId == applyInfo.userId &&
                goodsId == applyInfo.goodsId &&
                type == applyInfo.type &&
                num == applyInfo.num &&
                date.equals(applyInfo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, goodsId, type, num, date);
    }

    @Override
    public String toString() {
        return "ApplyInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", type=" + type +
                ", num=" + num +
                ", date=" + date +
                '}';
    }
}
