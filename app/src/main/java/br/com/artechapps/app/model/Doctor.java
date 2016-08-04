package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 8/3/16.
 */
public class Doctor implements Serializable {

    private long code;
    private String name;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
