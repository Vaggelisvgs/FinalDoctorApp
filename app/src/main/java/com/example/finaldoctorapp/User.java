package com.example.finaldoctorapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    //Class of Object user that will be inserted in Firebase Database
    private String firstName;
    private String email;
    private String lastName;
    private String amka;
    private String mobileNumber;
    private String password;
    private List<String> appointmentHistory;
    private List<String> likedDoctors;

    public User() {
        // Default constructor required for Firebase
    }

    //Constructor with user details
    public User(String firstName, String lastName, String amka, String mobileNumber, String email, String passowrd, List<String> appointmentHistory, List<String> likedDoctors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.amka = amka;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = passowrd;
        this.appointmentHistory = appointmentHistory;
        this.likedDoctors = likedDoctors;
    }

    //getter and setters for user's details.
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAmka() {
        return amka;
    }

    public void setAmka(String amka) {
        this.amka = amka;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String passowrd) {this.password = passowrd;}

    public List<String> getAppointmentHistory() {
        return appointmentHistory;
    }

    public void setAppointmentHistory(List<String> appointmentHistory) {
        this.appointmentHistory = appointmentHistory;
    }
    public List<String> getLikedDoctors() {
        return likedDoctors;
    }
    public void setLikedDoctors(List<String> likedDoctors) {
        this.likedDoctors = likedDoctors;
    }
}
