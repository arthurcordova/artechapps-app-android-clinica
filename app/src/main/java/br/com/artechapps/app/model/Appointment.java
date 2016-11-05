package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 8/23/16.
 */
public class Appointment implements Serializable {

    private long codCliente;
    private String data;
    private String horario;
    private long codFilial;
    private long codProcedimento;

    public long getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(long codCliente) {
        this.codCliente = codCliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public long getCodFilial() {
        return codFilial;
    }

    public void setCodFilial(long codFilial) {
        this.codFilial = codFilial;
    }

    public long getCodProcedimento() {
        return codProcedimento;
    }

    public void setCodProcedimento(long codProcedimento) {
        this.codProcedimento = codProcedimento;
    }
}
