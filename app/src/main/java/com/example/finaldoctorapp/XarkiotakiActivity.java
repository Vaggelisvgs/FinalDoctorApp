package com.example.finaldoctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class XarkiotakiActivity extends AppCompatActivity {

    //This Class is About doctor Xarkiotaki
    private DatabaseReference userRef;
    private boolean isBackPressed;

    private String emailChildString;

    private String amka;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String password;

    private List<String> apHistory;
    private List<String> closeDates;

    private final String classID="ΧΑΡΚΙΩΤΑΚΗ ΕΙΡΗΝΗ";

    private final String doctorCategory="ΚΑΡΔΙΟΛΟΓΟΣ";

    private String selectedDate="";
    private String selectedTime="";

    private List<String> likedDoctors;

    private ImageView likebutton;

    private Boolean likedDoctorBool;

    //init all the info about the xml activity that will be see
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xarkiotaki);

        likebutton = findViewById(R.id.likeButton);

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
        likedDoctors = intent.getStringArrayListExtra("likedListExtra");
        checkIfDoctorInLiked();
        initLikeButtonUI();
        initCalendarUI();
        getClosedDates();
    }

    private void checkIfDoctorInLiked() {
        if(likedDoctors.contains(classID+", "+doctorCategory)){
            likebutton.setImageResource(R.drawable.images_red);
            likedDoctorBool=true;
        }else{
            likebutton.setImageResource(R.drawable.images);
            likedDoctorBool=false;
        }

    }

    private void initLikeButtonUI() {
        likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                String userKey = emailChildString;

                if(!likedDoctorBool) {
                    usersRef.child(userKey).child("likedDoctors").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                                GenericTypeIndicator<List<String>> listType = new GenericTypeIndicator<List<String>>() {
                                };
                                List<String> likedDoctor = dataSnapshot.getValue(listType);

                                if (likedDoctor == null) {
                                    likedDoctor = new ArrayList<>();
                                }

                                likedDoctor.add(classID + ", " + doctorCategory);
                                likedDoctors = likedDoctor;

                                usersRef.child(userKey).child("likedDoctors").setValue(likedDoctor)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    likebutton.setImageResource(R.drawable.images_red);
                                                    likedDoctorBool=true;
                                                    Toast.makeText(XarkiotakiActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(XarkiotakiActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    usersRef.child(userKey).child("likedDoctors").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                                GenericTypeIndicator<List<String>> listType = new GenericTypeIndicator<List<String>>() {
                                };
                                List<String> likedDoctor = dataSnapshot.getValue(listType);

                                if (likedDoctor == null) {
                                    likedDoctor = new ArrayList<>();
                                }

                                likedDoctor.remove(classID + ", " + doctorCategory);
                                likedDoctors = likedDoctor;

                                usersRef.child(userKey).child("likedDoctors").setValue(likedDoctor)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(XarkiotakiActivity.this, "Succesfull", Toast.LENGTH_SHORT).show();
                                                    likebutton.setImageResource(R.drawable.images);
                                                    likedDoctorBool=false;
                                                }else {
                                                    Toast.makeText(XarkiotakiActivity.this, "Failed to remove: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(XarkiotakiActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    //init the calendar details
    private void initCalendarUI() {
        CalendarView calendarView = findViewById(R.id.calendarView);
        CheckBox vcheckBox = findViewById(R.id.checkBox);
        CheckBox vcheckBox2 = findViewById(R.id.checkBox2);
        CheckBox vcheckBox3 = findViewById(R.id.checkBox3);
        CheckBox vcheckBox4 = findViewById(R.id.checkBox4);
        CheckBox vcheckBox5 = findViewById(R.id.checkBox5);
        CheckBox vcheckBox6 = findViewById(R.id.checkBox6);
        CheckBox vcheckBox7 = findViewById(R.id.checkBox7);
        CheckBox vcheckBox8 = findViewById(R.id.checkBox8);

        if(selectedDate.equals("")){
            selectedDate = getFormattedDate();
        }
        Button bookDateButton = findViewById(R.id.button21);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            selectedDate = formattedDate;
        });
        CheckBox[] checkBoxes = {vcheckBox, vcheckBox2, vcheckBox3, vcheckBox4, vcheckBox5, vcheckBox6, vcheckBox7, vcheckBox8};
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    for (CheckBox otherCheckBox : checkBoxes) {
                        if (otherCheckBox != checkBox) {
                            otherCheckBox.setChecked(false);
                        }
                    }
                    selectedTime = checkBox.getText().toString();
                }
            });
        }
        bookDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookTheDate(view);
            }
        });
    }

    //Function to connect functionality with UI of calendar
    public void bookTheDate(View view) {
        if (!selectedDate.isEmpty() && !selectedTime.isEmpty() && !isPastDate(selectedDate)) {
            String value1 = selectedDate + "," + selectedTime;
            String value2 = classID + "," + doctorCategory + "," + selectedDate + "," + selectedTime;

            if (closeDates.contains(value1)) {
                showValidationDialog("Selected date is already closed");
            } else {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                String userKey = emailChildString;
                usersRef.child(userKey).child("appointmentHistory").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                            GenericTypeIndicator<List<String>> listType = new GenericTypeIndicator<List<String>>() {
                            };
                            List<String> appointmentHistory = dataSnapshot.getValue(listType);

                            if (appointmentHistory == null) {
                                appointmentHistory = new ArrayList<>();
                            }

                            appointmentHistory.add(value2);

                            usersRef.child(userKey).child("appointmentHistory").setValue(appointmentHistory)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                updateCloseDate(value1);
                                            }
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(XarkiotakiActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            showValidationDialog("Please select a valid date and time");
        }
    }

    //update the firebase database about the closed date
    private void updateCloseDate(String newClosedDate) {
        DatabaseReference closeDateRef = FirebaseDatabase.getInstance().getReference("closeDate");
        DatabaseReference doctorRef = closeDateRef.child(classID);
        doctorRef.push().setValue(newClosedDate)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(XarkiotakiActivity.this, "Successfully add the new appointment of user.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    showValidationDialog("Failed to add the new appointment of user.");
                });
    }

    //get all the closed dates
    private void getClosedDates() {
        closeDates = new ArrayList<>();
        DatabaseReference closeDateRef = FirebaseDatabase.getInstance().getReference("closeDate");
        closeDateRef.child(classID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                closeDates.clear();
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    for (DataSnapshot doctorSnapshot : dataSnapshot.getChildren()) {
                        String dateAndTime = doctorSnapshot.getValue(String.class);
                        closeDates.add(dateAndTime);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(XarkiotakiActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date currentDate = new Date();
        return sdf.format(currentDate);
    }

    //if the selected date is past
    private boolean isPastDate(String selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = sdf.parse(selectedDate);
            Date currentDate = new Date();
            return date.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //custom dialog
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

    //functions to open other activities
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
    public void openBack(View view) {

        Intent intent = new Intent(this,CardiologistActivity.class);
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