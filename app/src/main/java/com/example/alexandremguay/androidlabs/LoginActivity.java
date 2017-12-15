package com.example.alexandremguay.androidlabs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static String pref_emails = "myemails"; //pref_emails is our STRING KEY - we initialize it to wtv, just to start off.
    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    private static EditText text;
    protected Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        sharedPref = getSharedPreferences(pref_emails, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        text = findViewById(R.id.editText);
        text.setText(sharedPref.getString("DefaultEmail","email@domain.com"));

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                String lastEmail = text.getText().toString();
                editor.putString("DefaultEmail", lastEmail); //setting value of "lastEmail" into sharedpreferences
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

        String savedEmail = sharedPref.getString("DefaultEmail", null); //getting the input value of email address using sharedpreferences, to populate email address field on login screen
        text.setText(savedEmail);
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}
