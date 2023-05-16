package com.bezkoder.springjwt.models;

import java.util.Date;

public class Ticket {
    private String name;
    private int size;
    private int num;
    private Date date;
    private int code;

    public Ticket(String name, int size, int num, Date date, int code) {
        this.name = name;
        this.size = size;
        this.num = num;
        this.date = date;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
