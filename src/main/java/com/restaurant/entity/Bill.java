package com.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "Bill.getAllBills", query = "select b from Bill b order by b.id desc")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "rest_bill")
public class Bill {

    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "total")
    private Integer total;

    @Column(name = "productDetails", columnDefinition = "CLOB")
    private String productDetails;

    @Column(name = "createdBy")
    private String createdBy;



}
