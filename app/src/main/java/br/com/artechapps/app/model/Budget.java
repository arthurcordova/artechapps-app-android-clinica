package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 7/22/16.
 */
public class Budget implements Serializable {

    private long code;
    private String status;
    private String date;
    private double valueBudget;
    private double valueDiscount;
    private double valueTotal;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValueBudget() {
        return valueBudget;
    }

    public void setValueBudget(double valueBudget) {
        this.valueBudget = valueBudget;
    }

    public double getValueDiscount() {
        return valueDiscount;
    }

    public void setValueDiscount(double valueDiscount) {
        this.valueDiscount = valueDiscount;
    }

    public double getValueTotal() {
        return valueTotal;
    }

    public void setValueTotal(double valueTotal) {
        this.valueTotal = valueTotal;
    }
}
