package br.com.artechapps.app.model;

import java.io.Serializable;

import br.com.artechapps.app.BuildConfig;

/**
 * Created by arthurcordova on 8/22/16.
 */
public class BudgetConfirmProduct implements Serializable {

    private long codProduto;
    private long duracao = 0;
    private double valorProduto;
    private long codAngariador = 42;
    private long codIndicador = 0;
    private long opcad = 42;

    public BudgetConfirmProduct() {
    }

    public BudgetConfirmProduct(long codProduto, double valorProduto) {
        this.codProduto = codProduto;
        this.valorProduto = valorProduto;
    }

    public long getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(long codProduto) {
        this.codProduto = codProduto;
    }

    public double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(double valorProduto) {
        this.valorProduto = valorProduto;
    }
}
