package com.example.alexandremguay.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static String pref_emails = "myemails"; //pref_emails is our STRING KEY - we initialize it to wtv, just to start off.
    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    protected Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

//https://developer.android.com/training/basics/data-storage/shared-preferences.html#ReadSharedPreference

        sharedPref = getSharedPreferences(pref_emails, Context.MODE_PRIVATE);

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.editText);
                String lastEmail = text.getText().toString();
                sharedPref = getSharedPreferences(pref_emails, Context.MODE_PRIVATE);
                editor = sharedPref.edit();
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

        sharedPref = getSharedPreferences(pref_emails, Context.MODE_PRIVATE) ;
        String savedEmail = sharedPref.getString("DefaultEmail", "email@domain.com"); //getting the input value of email address using sharedpreferences, to populate email address field on login screen
        ((EditText)findViewById(R.id.editText)).setText(savedEmail);
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
