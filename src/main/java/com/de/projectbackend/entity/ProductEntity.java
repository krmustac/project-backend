package com.de.projectbackend.entity;


import lombok.Data;


import javax.persistence.*;


@Entity
@Data
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productName;
    private Integer amount;
    private String supplier;
    private String contact;
}
