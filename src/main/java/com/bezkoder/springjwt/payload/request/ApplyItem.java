package com.bezkoder.springjwt.payload.request;

public class ApplyItem{
    private int goods_id;
    private int num;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}