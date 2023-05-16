package com.bezkoder.springjwt.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="goodsInfo",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class GoodsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false,columnDefinition = "varchar(128)")
    private String name;

    @Column(name = "type",columnDefinition = "int comment '货物种类'")
    private int type;

    @Column(name = "sum",columnDefinition = "integer default 0 comment '货物总数，默认为零'")
    private int sum;

    @Column(name = "size",columnDefinition = "integer comment 't体积'")
    private int size;
    // 0，1，2，3,分别对应小，中，大，特大
    @Size(max=20)
    private String description;

    public GoodsInfo() {
    }

    public GoodsInfo(String name, int type, int sum, int size,String description) {
        this.name = name;
        this.type = type;
        this.sum = sum;
        this.size = size;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
