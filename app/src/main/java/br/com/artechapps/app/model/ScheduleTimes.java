package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 8/3/16.
 */
public class ScheduleTimes implements Serializable {

    private Integer codProduto;
    // dd-mm-yyyy
    private String data;

    public long getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(Integer codProduto) {
        this.codProduto = codProduto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
