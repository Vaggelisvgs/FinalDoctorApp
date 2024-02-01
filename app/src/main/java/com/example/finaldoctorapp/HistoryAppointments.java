package com.example.finaldoctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryAppointments extends AppCompatActivity {
    //Activity that shows user's history with appoiments
    private boolean isBackPressed;

    private String emailChildString;

    private String amka;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String password;

    private List<String> apHistory;

    //UI init and functionality for history list view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_appointments);
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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(emailChildString);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot listsnapshot = dataSnapshot.child("appointmentHistory");
                    List<String> list = new ArrayList<>();
                    for (DataSnapshot childSnapshot : listsnapshot.getChildren()) {
                        String item = childSnapshot.getValue(String.class);
                        list.add(item);
                    }
                    // Convert the List<String> to an array
                    ArrayList<String> array = new ArrayList<>(list);
                    apHistory = array;

                    // Populate the LinearLayout with TextView elements for each appointment
                    populateLinearLayout(apHistory);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HistoryAppointments.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to populate the LinearLayout with TextView elements for each appointment
    private void populateLinearLayout(List<String> apHistory) {
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.removeAllViews(); // Clear any existing views
        // Wrap the LinearLayout inside a ScrollView
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));

        linearLayout.addView(scrollView);

        LinearLayout linearLayoutContent = new LinearLayout(this);
        linearLayoutContent.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayoutContent.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayoutContent);
        for (String appointment : apHistory) {
            if (!"-".equals(appointment)) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                textView.setText(appointment);
                textView.setTextSize(20);
                textView.setPadding(16, 10, 16, 10);
                linearLayoutContent.addView(textView);
            }
        }
    }

    //open previous activity
    public void openBack(View view) {
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