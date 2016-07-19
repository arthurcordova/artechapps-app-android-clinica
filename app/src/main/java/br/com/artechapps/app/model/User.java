package br.com.artechapps.app.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by arthurcordova on 7/5/16.
 */
public class User implements Serializable{

    @Expose
    private String cpfcnpj;
    @Expose
    private String nome;
    private String lastName;
    @Expose
    private String senha;
    @Expose
    private String tipopessoa;
    @Expose
    private Long codFilial;
    private Long code;
    @Expose
    private Long opcad;
    private boolean active;

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipopessoa() {
        return tipopessoa;
    }

    public void setTipopessoa(String tipopessoa) {
        this.tipopessoa = tipopessoa;
    }

    public long getCodFilial() {
        return codFilial;
    }

    public void setCodFilial(Long codFilial) {
        this.codFilial = codFilial;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getOpcad() {
        return opcad;
    }

    public void setOpcad(Long opcad) {
        this.opcad = opcad;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
