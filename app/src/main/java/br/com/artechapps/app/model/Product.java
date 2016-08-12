package br.com.artechapps.app.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by arthurcordova on 7/17/16.
 */
public class Product implements Serializable {

    public static final String JSON_CODE = "codProduto";
    public static final String JSON_DESC = "descricao";
    public static final String JSON_VALUE = "valorProduto";
    public static final String JSON_TYPE = "tipoExame";

    private long id;
    private String Description;
    private Double value;
    private String type;
    private byte[] image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public static String formatValue(Double value){
        DecimalFormat moneyReal = new DecimalFormat("###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        String formated = moneyReal.format(value);
        return formated;
   }

}
