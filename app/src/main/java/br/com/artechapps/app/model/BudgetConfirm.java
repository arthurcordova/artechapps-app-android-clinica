package br.com.artechapps.app.model;

import java.io.Serializable;

import br.com.artechapps.app.BuildConfig;

/**
 * Created by arthurcordova on 7/22/16.
 */
public class BudgetConfirm implements Serializable {

    private long codAngariador = 42;
    private long codCliente;
    private long codVendedor= 42;
    private String status = "P";
    private double valorOrcamento;
    private double valorDesconto = 0;
    private double valorTotal;
    private long opcad = 42;
    private long codFilial = BuildConfig.FILIAL;
    private String dataOrcamento;

    public long getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(long codCliente) {
        this.codCliente = codCliente;
    }

    public double getValorOrcamento() {
        return valorOrcamento;
    }

    public void setValorOrcamento(double valorOrcamento) {
        this.valorOrcamento = valorOrcamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDataOrcamento() {
        return dataOrcamento;
    }

    public void setDataOrcamento(String dataOrcamento) {
        this.dataOrcamento = dataOrcamento;
    }
}
