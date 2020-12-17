package com.zindabad.ecommerce.model;

import javax.persistence.*;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Order Order;
    private int quantity;
    private Double price;

    public OrderProduct(){

    }

    public OrderProduct(Product product, com.zindabad.ecommerce.model.Order order, int quantity, Double price) {
        this.product = product;
        Order = order;
        this.quantity = quantity;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public com.zindabad.ecommerce.model.Order getOrder() {
        return Order;
    }

    public void setOrder(com.zindabad.ecommerce.model.Order order) {
        Order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
