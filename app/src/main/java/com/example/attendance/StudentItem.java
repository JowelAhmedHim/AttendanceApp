package com.example.attendance;

public class StudentItem {

    private long sid;
    private String roll;
    private String name;
    private String status;


    public StudentItem(long sid,String roll, String name) {
        this.sid = sid;
        this.roll = roll;
        this.name = name;
        this.status = "";
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
