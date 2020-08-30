package com.HWI.pharmacy;

public class prescModel {
    private String url, title, medicine, req;

    public prescModel(String url, String title, String medicine, String req)
    {
        this.url=url;
        this.title=title;
        this.medicine=medicine;
        this.req=req;
    }

    public prescModel(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }
}
