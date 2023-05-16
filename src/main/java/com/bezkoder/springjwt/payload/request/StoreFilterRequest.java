package com.bezkoder.springjwt.payload.request;

public class StoreFilterRequest {
    private int area_id;
    private int shelve_x;
    private int shelve_y;
    private int shelve_z;

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getShelve_x() {
        return shelve_x;
    }

    public void setShelve_x(int shelve_x) {
        this.shelve_x = shelve_x;
    }

    public int getShelve_y() {
        return shelve_y;
    }

    public void setShelve_y(int shelve_y) {
        this.shelve_y = shelve_y;
    }

    public int getShelve_z() {
        return shelve_z;
    }

    public void setShelve_z(int shelve_z) {
        this.shelve_z = shelve_z;
    }
}
