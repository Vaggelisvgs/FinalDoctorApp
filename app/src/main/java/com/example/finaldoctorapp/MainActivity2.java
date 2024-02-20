package com.example.finaldoctorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    //open activity to find the doctor that user whant
    private String amka;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String password;

    private List<String> apHistory;

    private List<String> likedDoctors;

    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private List<String> doctorNames;

    private String emailChildString;

    private TextView headerText;
    private AppCompatButton Bbutton3;
    private AppCompatButton Bbutton4;
    private AppCompatButton Bbutton5;
    private AppCompatButton Bbutton6;
    private AppCompatButton Bbutton7;
    private AppCompatButton Bbutton8;

    private ArrayList<String> buttonNames = new ArrayList<>();

    private boolean isBackPressed;


    //init UI components
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initSearch();
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

    //init search functionality and UI
    private void initSearch(){
        relativeLayout = findViewById(R.id.relativeLayout);
        headerText = findViewById(R.id.header_text);
        linearLayout = findViewById(R.id.myLayout);

        doctorNames = new ArrayList<>();
        doctorNames.add("Xarkiotaki-Χαρκιωτάκη");
        doctorNames.add("Kostantinidis-Κωνσαντινίδης");
        doctorNames.add("Fotogiannis-Φωτογιάννης");
        doctorNames.add("Tasoula-Τασούλα");
        doctorNames.add("Rigakis G.-Ρηγάκης Γ.");
        doctorNames.add("Sakellar-Σακελλαρίδης");
        doctorNames.add("Papoutsis-Παπουτσής");
        doctorNames.add("Rigakis N.-Ρηγάκης Ν.");
        doctorNames.add("Rozaki-Ροζάκη");
        doctorNames.add("Nikolaidis-Νικολαίδης");
        doctorNames.add("Siouzios-Σιούζιος");
        doctorNames.add("Dimitrellos-Δημητρέλλος");

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterDoctors(newText);
                return true;
            }
        });

        // Initialize the button texts
        Bbutton3 = findViewById(R.id.button3);
        Bbutton4 = findViewById(R.id.button4);
        Bbutton5 = findViewById(R.id.button5);
        Bbutton6 = findViewById(R.id.button6);
        Bbutton7 = findViewById(R.id.button7);
        Bbutton8 = findViewById(R.id.button8);
        saveDefaultButtonssTexts();
    }

    //save the default buttons of the UI
    private void saveDefaultButtonssTexts() {
        buttonNames.add(Bbutton3.getText().toString());
        buttonNames.add(Bbutton4.getText().toString());
        buttonNames.add(Bbutton5.getText().toString());
        buttonNames.add(Bbutton6.getText().toString());
        buttonNames.add(Bbutton7.getText().toString());
        buttonNames.add(Bbutton8.getText().toString());
    }

    //set the default names for te above buttons
    private void setButtonNamesDefault(ArrayList<String> buttonNames) {
        if (buttonNames.isEmpty()) {
            relativeLayout.setVisibility(View.INVISIBLE);
        } else {
            for (String doctor : buttonNames) {
                AppCompatButton searchButton = new AppCompatButton(this);
                searchButton.setText(doctor);
                searchButton.setTextSize(20);
                searchButton.setPadding(16, 10, 16, 10);
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String doctorName = ((TextView) v).getText().toString();
                        switch (doctorName) {
                            case "Internist":
                                openInternist(v);
                                break;
                            case "Cardiologist":
                                openCardiologist(v);
                                break;
                            case "Pneumonologist":
                                openPneumonologist(v);
                                break;
                            case "Dermatologist":
                                openDermatologist(v);
                                break;
                            case "Otolaryngologist":
                                openOtolaryngologist(v);
                                break;
                        }
                    }
                });
                linearLayout.addView(searchButton);
            }
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

//filter the doctor result in search
private void filterDoctors(String query) {
        linearLayout.removeAllViews();
        if (TextUtils.isEmpty(query)) {
            headerText.setText("Categories");
            relativeLayout.setVisibility(View.VISIBLE);
            setButtonNamesDefault(buttonNames);
        }else {
            List<String> filteredDoctors = new ArrayList<>();
            for (String doctor : doctorNames) {
                if (doctor.toLowerCase().contains(query.toLowerCase())) {
                    filteredDoctors.add(doctor);
                }
            }

            if (filteredDoctors.isEmpty()) {
                relativeLayout.setVisibility(View.VISIBLE);
                setButtonNamesDefault(buttonNames);
            } else {
                for (String doctor : filteredDoctors) {
                    TextView textView = new TextView(this);
                    textView.setText(doctor);
                    textView.setTextSize(20);
                    textView.setPadding(16, 10, 16, 10);
                    textView.setOnClickListener(new View.OnClickListener() {
                        //TODO: check doctor's xmls error bugs
                        @Override
                        public void onClick(View v) {
                            String doctorName = ((TextView) v).getText().toString().trim();

                            if ("Xarkiotaki-Χαρκιωτάκη".equalsIgnoreCase(doctorName)) {
                                openXarkiotakis(v);
                            } else if ("Kostantinidis-Κωνσταντινιδης".equalsIgnoreCase(doctorName)) {
                                openKonstantinidis(v);
                            } else if ("Fotogiannis-Φωτογιάννης".equalsIgnoreCase(doctorName)) {
                                openFotogiannis(v);
                            } else if ("Tasoula-Τασούλα".equalsIgnoreCase(doctorName)) {
                                openTasoula(v);
                            } else if ("Rigakis G.-Ρηγάκης Γ.".equalsIgnoreCase(doctorName)) {
                                openRigakisG(v);
                            } else if ("Sakellar-Σακελλαρίδης".equalsIgnoreCase(doctorName)) {
                                openSakellar(v);
                            } else if ("Papoutsis-Παπουτσής".equalsIgnoreCase(doctorName)) {
                                openPapoutsis(v);
                            } else if ("Rigakis N.-Ρηγάκης Ν.".equalsIgnoreCase(doctorName)) {
                                openRigakisN(v);
                            } else if ("Rozaki-Ροζάκη".equalsIgnoreCase(doctorName)) {
                                openRozakis(v);
                            } else if ("Nikolaidis-Νικολαίδης".equalsIgnoreCase(doctorName)) {
                                openNikolaidis(v);
                            } else if ("Siouzios-Σιούζιος".equalsIgnoreCase(doctorName)) {
                                openSiouzios(v);
                            } else if ("Dimitrellos-Δημητρέλλος".equalsIgnoreCase(doctorName)) {
                                openDimitrellos(v);
                            }
                        }
                    });
                    linearLayout.addView(textView);
                }
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }
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

    //open liked doctors
    public void openLikedDoctors(View view){
        Intent intent = new Intent(this, HistoryAppointments.class);
        intent.putExtra("usage","likedDoctors");
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

    //open main activity
    public void openMainActivity(View view) {

        Intent intent = new Intent(this,MainActivity.class);
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

    //open doctor's category activity
    public void openInternist(View view) {

        Intent intent = new Intent(this,InternistActivity.class);
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
    public void openCardiologist(View view) {

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
    public void openPneumonologist(View view) {

        Intent intent = new Intent(this,PneumonologistActivity.class);
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
    public void openDermatologist(View view) {

        Intent intent = new Intent(this,DermatologistActivity.class);
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
    public void openOtolaryngologist(View view) {

        Intent intent = new Intent(this,OtolaryngologistActivity.class);
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
    public void openOptician(View view) {

        Intent intent = new Intent(this,OpticianActivity.class);
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

    //open doctor's activity
    public void openXarkiotakis(View view){
        Intent intent = new Intent(this, XarkiotakiActivity.class);
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

    public void openDimitrellos(View view){
        Intent intent = new Intent(this, DimitrellosActivity.class);
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
    public void openRozakis(View view){
        Intent intent = new Intent(this, RozakiActivity.class);
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
    public void openFotogiannis(View view){
        Intent intent = new Intent(this, FotogiannisActivity.class);
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

    public void openKonstantinidis(View view){
        Intent intent = new Intent(this, KonstantinidisActivity.class);
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

    public void openNikolaidis(View view){
        Intent intent = new Intent(this, NikolaidisActivity.class);
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

    public void openPapoutsis(View view){
        Intent intent = new Intent(this, PapoutsisActivity.class);
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

    public void openRigakisG(View view){
        Intent intent = new Intent(this, RigakisGActivity.class);
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
    public void openRigakisN(View view){
        Intent intent = new Intent(this, RigakisNActivity.class);
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

    public void openSakellar(View view){
        Intent intent = new Intent(this, SakellarActivity.class);
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

    public void openSiouzios(View view){
        Intent intent = new Intent(this, SiouziosActivity.class);
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
    public void openTasoula(View view){
        Intent intent = new Intent(this, TasoulaActivity.class);
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

    //open user activity profile
    public void openMyProfileActivity(View view) {
        Intent profileIntent = new Intent(this, MyProfileActivity.class);
        profileIntent.putExtra("userID",emailChildString);
        profileIntent.putExtra("fullname", firstName+" "+lastName);
        profileIntent.putExtra("amka", amka);
        profileIntent.putExtra("email", email);
        profileIntent.putExtra("firstName", firstName);
        profileIntent.putExtra("lastName", lastName);
        profileIntent.putExtra("mobileNumber", mobileNumber);
        profileIntent.putExtra("password", password);
        ArrayList<String> array = new ArrayList<>(apHistory);
        profileIntent.putExtra("listExtra",array);
        ArrayList<String> likedArray = new ArrayList<>(likedDoctors);
        profileIntent.putExtra("likedListExtra",likedArray);
        startActivity(profileIntent);
        finish();
    }
}