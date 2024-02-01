package com.example.finaldoctorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    //Class for edit user details
    private DatabaseReference userRef;

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAmka;
    private EditText editTextEmail;
    private EditText editTextMobileNumber;
    private EditText editTextPassword;
    private String amka;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String password;

    private List<String> apHistory;

    private boolean isBackPressed;

    private String emailChildString;


    //Init UI and details of user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        emailChildString = intent.getStringExtra("userID");
        amka = intent.getStringExtra("amka");
        email = intent.getStringExtra("email");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        mobileNumber = intent.getStringExtra("mobileNumber");
        password = intent.getStringExtra("password");
        apHistory = intent.getStringArrayListExtra("listExtra");
        isBackPressed = false;
        // Initialize the EditText fields
        editTextFirstName = findViewById(R.id.editTextTextPersonName2);
        editTextLastName = findViewById(R.id.editTextTextPassword7);
        editTextAmka = findViewById(R.id.editTextTextPassword8);
        editTextEmail = findViewById(R.id.editTextTextPassword10);
        editTextMobileNumber = findViewById(R.id.editTextTextPassword9);
        editTextPassword = findViewById(R.id.editTextTextPassword6);


        // Initialize the EditText fields
        editTextFirstName.setText(firstName);
        editTextLastName.setText(lastName);
        editTextAmka.setText(amka);
        editTextEmail.setText(email);
        editTextMobileNumber.setText(mobileNumber);
        editTextPassword.setText(password);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        List<String> emailChild = Arrays.asList(email.split("@"));
        String emailChildString = emailChild.get(0).toString();
        userRef = database.getReference("users").child(emailChildString);



    }

    //open user profile but first check all the placeholders with their data
    public void openMyProfileActivity(View view) {
        // Get updated values from the EditText fields
        String updatedFirstName = editTextFirstName.getText().toString();
        String updatedLastName = editTextLastName.getText().toString();
        String updatedAmka = editTextAmka.getText().toString();
        String updatedEmail = editTextEmail.getText().toString();
        String updatedMobileNumber = editTextMobileNumber.getText().toString();
        String updatedPassword = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(updatedFirstName) || TextUtils.isEmpty(updatedLastName)
                || TextUtils.isEmpty(updatedAmka) || TextUtils.isEmpty(updatedEmail)
                || TextUtils.isEmpty(updatedMobileNumber) || TextUtils.isEmpty(updatedPassword)) {
            showValidationErrorDialog("Please fill in all the fields.");
        } else if (!isValidEmail(updatedEmail)) {
            showValidationErrorDialog("Please enter a valid email address.");
        } else if (!isValidMobileNumber(updatedMobileNumber)) {
            showValidationErrorDialog("Please enter a valid mobile number.");
        } else {
            if (userRef != null) {
                // Update the user's data in Firebase
                userRef.child("firstName").setValue(updatedFirstName);
                userRef.child("lastName").setValue(updatedLastName);
                userRef.child("amka").setValue(updatedAmka);
                userRef.child("email").setValue(updatedEmail);
                userRef.child("mobileNumber").setValue(updatedMobileNumber);
                userRef.child("password").setValue(updatedPassword);

                // Create the intent and pass the updated data
                Intent profileIntent = new Intent(this, MyProfileActivity.class);
                profileIntent.putExtra("userID",emailChildString);
                profileIntent.putExtra("fullname", updatedFirstName + " " + updatedLastName);
                profileIntent.putExtra("amka", updatedAmka);
                profileIntent.putExtra("email", updatedEmail);
                profileIntent.putExtra("firstName", updatedFirstName);
                profileIntent.putExtra("lastName", updatedLastName);
                profileIntent.putExtra("mobileNumber", updatedMobileNumber);
                profileIntent.putExtra("password", updatedPassword);
                profileIntent.putStringArrayListExtra("listExtra", new ArrayList<>(apHistory));
                startActivity(profileIntent);
                finish();
            }
        }
    }

    //custom validation dialog for error
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

    //check palceholders values
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        return TextUtils.isDigitsOnly(mobileNumber) && mobileNumber.length() == 10;
    }

    //functions to open  find doctor activity
    public void openMainActivity2(View view) {
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