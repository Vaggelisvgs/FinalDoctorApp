package com.example.finaldoctorapp;

public class DateOfApoiment {
    //Futured class for appoiments

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    private String userId;
    private String dateFormat;

    public DateOfApoiment(){}

    public DateOfApoiment(String userName, String dateFormat){
        this.userId = userName;
        this.dateFormat = dateFormat;
    }
}
