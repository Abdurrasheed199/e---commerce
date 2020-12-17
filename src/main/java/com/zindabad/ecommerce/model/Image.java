package com.zindabad.ecommerce.model;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String url;

    @ManyToOne
    private Product product;

    public Image(){

    }

    public Image(String url, Product product) {
        this.url = url;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
