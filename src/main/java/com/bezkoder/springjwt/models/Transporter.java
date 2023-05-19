package com.bezkoder.springjwt.models;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;


/**
 * @author zhangwq
 */
@Entity
@Table(name = "transporter")
public class Transporter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;//对应user表
    @NotBlank
    @Size(max = 20)
    private String phone;

    @NotBlank
    @Size(max = 20)
    private String name;

    private int belongId;//市站id


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Transporter() {
    }

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }
}