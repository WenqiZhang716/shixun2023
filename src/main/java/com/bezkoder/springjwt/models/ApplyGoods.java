package com.bezkoder.springjwt.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "applyGoods")
public class ApplyGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "apply_id",columnDefinition = "int comment '申请人'")
    private int applyId;

    @Column(name = "store_id")
    private int storeId;

    public ApplyGoods() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplyGoods that = (ApplyGoods) o;
        return id == that.id &&
                applyId == that.applyId &&
                storeId == that.storeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applyId, storeId);
    }

    @Override
    public String toString() {
        return "ApplyGoods{" +
                "id=" + id +
                ", applyId=" + applyId +
                ", storeId=" + storeId +
                '}';
    }
}
