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
@Table(name = "transport_step")
public class TransportStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Size(max = 200)
    private String name;
    private int type; //0大站 1省站 2市站
    private String provinces; //所在省或包含省

    public TransportStep() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }
}
