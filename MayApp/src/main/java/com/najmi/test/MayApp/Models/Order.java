package com.najmi.test.MayApp.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "ordertable")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "order_product", nullable = false)
    private Product product;

    @Column(name = "order_phoneno", nullable = false)
    private String phoneNumber;

    public Order(int id, Product product, String phoneNumber) {
        this.id = id;
        this.product = product;
        this.phoneNumber = phoneNumber;
    }

    public Order() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
