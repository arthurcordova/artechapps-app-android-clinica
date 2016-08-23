package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 8/23/16.
 */
public class Appointment implements Serializable {

    private int codCliente;
    private String data;
    private String horario;
    private int codFilial;
    private int codProcedimento;

    public Appointment(int codCliente, String data, String horario, int codFilial, int codProcedimento) {
        this.codCliente = codCliente;
        this.data = data;
        this.horario = horario;
        this.codFilial = codFilial;
        this.codProcedimento = codProcedimento;
    }

    public Appointment() {
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
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

    public int getCodFilial() {
        return codFilial;
    }

    public void setCodFilial(int codFilial) {
        this.codFilial = codFilial;
    }

    public int getCodProcedimento() {
        return codProcedimento;
    }

    public void setCodProcedimento(int codProcedimento) {
        this.codProcedimento = codProcedimento;
    }
}
