package com.bezkoder.springjwt.models;

import javax.persistence.*;

@Entity
@Table(name = "goodsType")
public class GoodsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @Column(name = "name",nullable = false,columnDefinition = "varchar(128) comment '类别名，例如易爆炸品'")
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
