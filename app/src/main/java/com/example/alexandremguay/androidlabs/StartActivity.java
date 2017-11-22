package com.example.alexandremguay.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    protected Button startButton;
    protected Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start); //R is the java file that gets created from XML files;
        Log.i(ACTIVITY_NAME, "In onCreate()");

        startButton = (Button) findViewById(R.id.startButton);
        chat = (Button) findViewById(R.id.alex);

        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) { //similar to lambda expressions
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
                onActivityResult(10, 10, intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User Clicked Start Chat");
            }
        });

    }
//auto indent 1) Ctrl+A , 2) Ctrl+Alt+L
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        int duration;

        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                String messagePassed = data.getStringExtra("Response");
                duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, "ListItemsActivity passed: " + messagePassed, duration);
                toast.show();
            }
        }


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

