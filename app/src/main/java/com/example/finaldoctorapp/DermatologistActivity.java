package com.example.finaldoctorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DermatologistActivity extends AppCompatActivity {

    //This Class is About Dermatologis doctors
    private boolean isBackPressed;

    private String emailChildString;

    private String amka;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String password;

    private List<String> apHistory;

    private List<String> likedDoctors;

    //init all the info about the xml activity that will be see
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dermatologist);

        Intent intent = getIntent();
        emailChildString = intent.getStringExtra("userID");
        amka = intent.getStringExtra("amka");
        email = intent.getStringExtra("email");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        mobileNumber = intent.getStringExtra("mobileNumber");
        password = intent.getStringExtra("password");
        apHistory = intent.getStringArrayListExtra("listExtra");
        likedDoctors = intent.getStringArrayListExtra("likedListExtra");
        isBackPressed = false;
    }

    //functions to open user profile
    public void openMyProfileActivity(View view) {

        Intent intent = new Intent(this,MyProfileActivity.class);
        intent.putExtra("userID",emailChildString);
        intent.putExtra("fullname", firstName+" "+lastName);
        intent.putExtra("amka", amka);
        intent.putExtra("email", email);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("mobileNumber", mobileNumber);
        intent.putExtra("password", password);
        ArrayList<String> array = new ArrayList<>(apHistory);
        intent.putExtra("listExtra",array);
        ArrayList<String> likedArray = new ArrayList<>(likedDoctors);
        intent.putExtra("likedListExtra",likedArray);
        startActivity(intent);
        finish();
    }

    //functions to open  find doctor activity
    public void openMainActivity2(View view) {

        Intent intent = new Intent(this,MainActivity2.class);
        intent.putExtra("userID",emailChildString);
        intent.putExtra("fullname", firstName+" "+lastName);
        intent.putExtra("amka", amka);
        intent.putExtra("email", email);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("mobileNumber", mobileNumber);
        intent.putExtra("password", password);
        ArrayList<String> array = new ArrayList<>(apHistory);
        intent.putExtra("listExtra",array);
        ArrayList<String> likedArray = new ArrayList<>(likedDoctors);
        intent.putExtra("likedListExtra",likedArray);
        startActivity(intent);
        finish();
    }

    //open previous activity
    public void openBack(View view) {

        Intent intent = new Intent(this,MainActivity2.class);
        intent.putExtra("userID",emailChildString);
        intent.putExtra("fullname", firstName+" "+lastName);
        intent.putExtra("amka", amka);
        intent.putExtra("email", email);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("mobileNumber", mobileNumber);
        intent.putExtra("password", password);
        ArrayList<String> array = new ArrayList<>(apHistory);
        intent.putExtra("listExtra",array);
        ArrayList<String> likedArray = new ArrayList<>(likedDoctors);
        intent.putExtra("likedListExtra",likedArray);
        startActivity(intent);
        finish();
    }
    //functions to open doctors activity
    public void openFotogiannisActivity(View view) {

        Intent intent = new Intent(this,FotogiannisActivity.class);
        intent.putExtra("userID",emailChildString);
        intent.putExtra("fullname", firstName+" "+lastName);
        intent.putExtra("amka", amka);
        intent.putExtra("email", email);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("mobileNumber", mobileNumber);
        intent.putExtra("password", password);
        ArrayList<String> array = new ArrayList<>(apHistory);
        intent.putExtra("listExtra",array);
        ArrayList<String> likedArray = new ArrayList<>(likedDoctors);
        intent.putExtra("likedListExtra",likedArray);
        startActivity(intent);
        finish();
    }
    public void openTasoulaActivity(View view) {

        Intent intent = new Intent(this,TasoulaActivity.class);
        intent.putExtra("userID",emailChildString);
        intent.putExtra("fullname", firstName+" "+lastName);
        intent.putExtra("amka", amka);
        intent.putExtra("email", email);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("mobileNumber", mobileNumber);
        intent.putExtra("password", password);
        ArrayList<String> array = new ArrayList<>(apHistory);
        intent.putExtra("listExtra",array);
        ArrayList<String> likedArray = new ArrayList<>(likedDoctors);
        intent.putExtra("likedListExtra",likedArray);
        startActivity(intent);
        finish();
    }

    //if user push back button of the phone
    @Override
    public void onBackPressed() {
        if(!isBackPressed){
            isBackPressed = true;
            Toast.makeText(this,"If you click back button again you will exit from the app",Toast.LENGTH_LONG).show();
        }else{
            finish();
            System.exit(0);
        }
    }
}