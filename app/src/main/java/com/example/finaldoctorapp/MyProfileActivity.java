package com.example.finaldoctorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyProfileActivity extends AppCompatActivity {

    //This Class is About user to see their profile details and edit them as also and the history of their apoiments
    private String amka;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String password;

    private List<String> apHistory;

    private String fullname;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView amkaTextView;
    private TextView emailTextView;
    private TextView mobileNumberTextView;

    private TextView fullnameTextView;

    private boolean isBackPressed;

    private String emailChildString;

    //init all the details and the UI components
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        fullnameTextView = findViewById(R.id.fullnameTextview);
        firstNameTextView = findViewById(R.id.textView15);
        lastNameTextView = findViewById(R.id.textView16);
        amkaTextView = findViewById(R.id.textView17);
        emailTextView = findViewById(R.id.textView18);
        mobileNumberTextView = findViewById(R.id.textView20);

        // Retrieve the values from the intent that started MyProfileActivity
        Intent intent = getIntent();
        emailChildString = intent.getStringExtra("userID");
        fullname = intent.getStringExtra("fullname");
        amka = intent.getStringExtra("amka");
        email = intent.getStringExtra("email");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        mobileNumber = intent.getStringExtra("mobileNumber");
        password = intent.getStringExtra("password");
        apHistory = intent.getStringArrayListExtra("listExtra");
        isBackPressed = false;

        // Set the retrieved values to the respective TextView fields
        fullnameTextView.setText(fullname);
        firstNameTextView.setText(firstName);
        lastNameTextView.setText(lastName);
        amkaTextView.setText(amka);
        emailTextView.setText(email);
        mobileNumberTextView.setText(mobileNumber);
    }


    //functions to open previous activity
    public void openBack(View view) {

        Intent main2Intent = new Intent(this,MainActivity2.class);
        main2Intent.putExtra("userID",emailChildString);
        main2Intent.putExtra("fullname", firstName+" "+lastName);
        main2Intent.putExtra("amka", amka);
        main2Intent.putExtra("email", email);
        main2Intent.putExtra("firstName", firstName);
        main2Intent.putExtra("lastName", lastName);
        main2Intent.putExtra("mobileNumber", mobileNumber);
        main2Intent.putExtra("password", password);
        ArrayList<String> array = new ArrayList<>(apHistory);
        main2Intent.putExtra("listExtra",array);
        startActivity(main2Intent);
        finish();
    }

    //open activity of history
    public void openHistory(View view) {
        Intent intent = new Intent(this,HistoryAppointments.class);
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
        startActivity(intent);
        finish();
    }

    //open edit details activity
    public void openEdit(View view) {

        Intent EditIntent = new Intent(this,EditActivity.class);
        EditIntent.putExtra("userID",emailChildString);
        EditIntent.putExtra("fullname", firstName+" "+lastName);
        EditIntent.putExtra("amka", amka);
        EditIntent.putExtra("email", email);
        EditIntent.putExtra("firstName", firstName);
        EditIntent.putExtra("lastName", lastName);
        EditIntent.putExtra("mobileNumber", mobileNumber);
        EditIntent.putExtra("password", password);
        ArrayList<String> array = new ArrayList<>(apHistory);
        EditIntent.putExtra("listExtra",array);
        startActivity(EditIntent);
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