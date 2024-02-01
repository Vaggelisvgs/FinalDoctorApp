package com.example.finaldoctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Main activity class. Is the activity of user login
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private DatabaseReference databaseReference;

    private boolean isBackPressed;

    //Init UI components, functionality for login and make the firebase check for the user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.editTextTextPersonName8);
        passwordEditText = findViewById(R.id.editText);
        signInButton = findViewById(R.id.button2);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailtemp = emailEditText.getText().toString().trim();
                final String passwordtemp = passwordEditText.getText().toString().trim();

                if (!isValidEmail(emailtemp)) {
                    showValidationDialog("Invalid email address");
                    return;
                }

                if (passwordtemp.isEmpty()) {
                    showValidationDialog("Please enter a password");
                    return;
                }

                final String email = emailEditText.getText().toString().trim();
                List<String> emailChild = Arrays.asList(email.split("@"));
                String emailChildString = emailChild.get(0);
                final String password = passwordEditText.getText().toString().trim();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(emailChildString).exists()) {
                            String savedPassword = dataSnapshot.child(emailChildString).child("password").getValue(String.class);
                            if (password.equals(savedPassword)) {
                                // Password is correct, login successful
                                Intent mainIntent = new Intent(getApplicationContext(),MainActivity2.class);
                                mainIntent.putExtra("userID",emailChildString);
                                mainIntent.putExtra("amka",dataSnapshot.child(emailChildString).child("amka").getValue(String.class));
                                mainIntent.putExtra("email",dataSnapshot.child(emailChildString).child("email").getValue(String.class));
                                mainIntent.putExtra("firstName",dataSnapshot.child(emailChildString).child("firstName").getValue(String.class));
                                mainIntent.putExtra("lastName",dataSnapshot.child(emailChildString).child("lastName").getValue(String.class));
                                mainIntent.putExtra("mobileNumber",dataSnapshot.child(emailChildString).child("mobileNumber").getValue(String.class));
                                mainIntent.putExtra("password",dataSnapshot.child(emailChildString).child("password").getValue(String.class));

                                DataSnapshot listsnapshot = dataSnapshot.child(emailChildString).child("appointmentHistory");
                                List<String> list = new ArrayList<>();
                                for (DataSnapshot childSnapshot : listsnapshot.getChildren()) {
                                    String item = childSnapshot.getValue(String.class);
                                    list.add(item);
                                }
                                // Convert the List<String> to an array
                                ArrayList<String> array = new ArrayList<>(list);
                                mainIntent.putStringArrayListExtra("listExtra",array);
                                startActivity(mainIntent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                        }
                        emailEditText.setText("");
                        passwordEditText.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        isBackPressed = false;
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

    //custom dialog for valid data on the placeholders
    private void showValidationDialog(String message) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_validation); // Create a custom layout for the dialog
        TextView messageTextView = dialog.findViewById(R.id.textViewMessage);
        messageTextView.setText(message);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // Set animation for the dialog
        dialog.show();
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //open activity to sign up user
    public void openSignupActivity(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}