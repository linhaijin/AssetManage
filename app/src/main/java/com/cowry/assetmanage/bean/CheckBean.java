package com.cowry.assetmanage.bean;

/**
 * Created by acer on 2016/6/27.
 */
public class CheckBean{
    private int number;
    private String category;
    private String date;
    private String status;
    private String startId;
    private String endId;

    public String getBeans() {
        return beans;
    }

    public void setBeans(String beans) {
        this.beans = beans;
    }

    private String beans;




    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
