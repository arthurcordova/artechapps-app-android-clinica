package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 7/5/16.
 */
public class User implements Serializable{

    private String cpfcnpj;
    private String name;
    private String senha;
    private Long codFilial;
    private boolean active;

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setCodFilial(Long codFilial) {
        this.codFilial = codFilial;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
