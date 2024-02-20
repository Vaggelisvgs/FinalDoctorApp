package com.example.finaldoctorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    //Class to sign up user in database
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText amkaEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText mobileNumberEditText;
    private Button signUpButton;

    private DatabaseReference databaseReference;

    //on create we ara init the placeholders and we check about their values. If all are okey we push the data to Firebase database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstNameEditText = findViewById(R.id.editTextTextPersonName);
        lastNameEditText = findViewById(R.id.editTextTextPassword);
        amkaEditText = findViewById(R.id.editTextTextPassword2);
        emailEditText = findViewById(R.id.editTextTextPassword3);
        passwordEditText = findViewById(R.id.editTextTextPassword4);
        mobileNumberEditText = findViewById(R.id.editTextTextPassword5);
        signUpButton = findViewById(R.id.button);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String amka = amkaEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String mobileNumber = mobileNumberEditText.getText().toString().trim();
                List<String> emptyList = new ArrayList<>();
                List<String> emptyLikedList = new ArrayList<>();
                emptyLikedList.add("-");
                emptyList.add("-");


                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)
                        || TextUtils.isEmpty(amka) || TextUtils.isEmpty(email)
                        || TextUtils.isEmpty(password) || TextUtils.isEmpty(mobileNumber)) {
                    showValidationErrorDialog("Please fill in all the fields.");
                } else if (!isValidEmail(email)) {
                    showValidationErrorDialog("Please enter a valid email address.");
                } else if (!isValidPassword(password)) {
                    showValidationErrorDialog("Please enter a valid password. Password must be at least 6 characters long.");
                } else if (!isValidMobileNumber(mobileNumber)) {
                    showValidationErrorDialog("Please enter a valid mobile number.");
                }  else {
                    User user = new User(firstName, lastName, amka, mobileNumber, email,password,emptyList,emptyLikedList);

                    List<String> splittedEmail = Arrays.asList(email.split("@"));

                    databaseReference.child(splittedEmail.get(0)).setValue(user);

                    Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                   }
                firstNameEditText.setText("");
                lastNameEditText.setText("");
                amkaEditText.setText("");
                emailEditText.setText("");
                passwordEditText.setText("");
                mobileNumberEditText.setText("");
            }
        });
    }

    //dialog if some details are not correct
    private void showValidationErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //if some placeholders are valid
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private boolean isValidMobileNumber(String mobileNumber) {
      return TextUtils.isDigitsOnly(mobileNumber) && mobileNumber.length() == 10;
    }
}