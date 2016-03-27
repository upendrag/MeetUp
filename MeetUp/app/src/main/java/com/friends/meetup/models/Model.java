package com.friends.meetup.models;

/**
 * Created by shruthi on 27/3/16.
 */
public class Model {
    private String to;

    public String getTo() {

        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String from;
    private String date;
    private long id;

    public long getId() {
        return id;
    }

    public Model(String date, String from, String to){
        this.date = date;
        this.from = date;
        this.to = date;
    }

    public Model(){

    }
}
