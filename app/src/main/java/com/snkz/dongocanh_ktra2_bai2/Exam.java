package com.snkz.dongocanh_ktra2_bai2;

import java.io.Serializable;

public class Exam implements Serializable {
    private int id;
    private String name;
    private String date;
    private String time;
    private String status;

    public Exam(int id, String name, String date, String time, String status) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public Exam() {
    }

    public Exam(String name, String date, String time, String status) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
