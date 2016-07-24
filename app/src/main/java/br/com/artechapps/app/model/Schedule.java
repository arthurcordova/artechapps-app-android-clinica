package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 7/22/16.
 */
public class Schedule implements Serializable{

    public static String JSON_CODE = "codAgenda";
    public static String JSON_PRODUCT_CODE = "codProcedimento";
    public static String JSON_PRODUCT_DESC = "descProcedimento";
    public static String JSON_TIME = "horario";
    public static String JSON_REPEAT = "repetido";
    public static String JSON_STATUS = "status";

    private long id;
    private long code;
    private Product product;
    private String time;
    private String repeat;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Product getProduct() {
        if (product == null){
            product = new Product();
        }
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
