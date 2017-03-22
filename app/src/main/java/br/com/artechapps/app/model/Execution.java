package br.com.artechapps.app.model;

/**
 * Created by acstapassoli on 21/03/17.
 */

public class Execution {

    private Integer codigo;
    private String data;
    private String produto;
    private String executor;
    private Integer codOs;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public Integer getCodOs() {
        return codOs;
    }

    public void setCodOs(Integer codOs) {
        this.codOs = codOs;
    }
}
