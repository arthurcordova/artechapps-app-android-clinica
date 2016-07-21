package br.com.artechapps.app.model;

import java.io.Serializable;

/**
 * Created by arthurcordova on 7/20/16.
 */
public class Message implements Serializable{

    private long id;
    private String title;
    private String author;
    private String message;
    private String type;
    private String sentDate;
    private String seeDate;
    private boolean see;
    private long idClient;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getSeeDate() {
        return seeDate;
    }

    public void setSeeDate(String seeDate) {
        this.seeDate = seeDate;
    }

    public boolean isSee() {
        return see;
    }

    public void setSee(boolean see) {
        this.see = see;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }
}
