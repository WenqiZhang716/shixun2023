package com.bezkoder.springjwt.payload.request;

import org.springframework.stereotype.Component;

@Component
public class ApplySaveRequest {
    private ApplyItem[] goods_list;

    public ApplyItem[] getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(ApplyItem[] goods_list) {
        this.goods_list = goods_list;
    }
}


