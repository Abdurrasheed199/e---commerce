package com.zindabad.ecommerce.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Customer customer;
    @OneToOne
    private Order order;
    private String reference;
    private Double amount;
    private Date date;
    private Boolean paid;

    public Payment() {

    }

    public Payment(Customer customer, Order order, String reference, Double amount, Date date, Boolean paid) {
        this.customer = customer;
        this.order = order;
        this.reference = reference;
        this.amount = amount;
        this.date = date;
        this.paid = paid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}



