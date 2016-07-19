package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 7/18/16.
 */
public class Shop implements Serializable {

    private long id;
    private Product product;

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
}
