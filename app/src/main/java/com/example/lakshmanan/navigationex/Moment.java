package com.example.lakshmanan.navigationex;

/**
 * Created by Lakshmanan on 14/11/15.
 */
public class Moment {
    private int id;
    private String moment;

    public Moment(){
    }

    public Moment(String data){
        super();
        this.moment = data;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setMoment(String data){
        this.moment = data;
    }

    public String getMoment(){
        return this.moment;
    }
}
