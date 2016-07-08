package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 7/5/16.
 */
public class User implements Serializable{

    private String cpfcnpj;
    private String senha;
    private Long codFilial;

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public long getCodFilial() {
        return codFilial;
    }

    public void setCodFilial(long codFilial) {
        this.codFilial = codFilial;
    }
}
