package com.redisMysql.entity;

import java.util.Date;

/**
 * Created by lumi on 17-8-13.
 */
public class Weather {

    private long id;

    private String address;

    private String now;

    private Date time;

    public Weather() {
    }

    public Weather(String address, String now, Date time) {
        this.address = address;
        this.now = now;
        this.time = time;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public String getNow() {
        return now;
    }

    public Date getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "address='" + address + '\'' +
                ", now='" + now + '\'' +
                ", time=" + time +
                '}';
    }
}
