package com.example.alexandremguay.androidlabs;

import android.app.Activity;
import android.os.Bundle;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start); //R is the java file that gets created from XML files;
    }
}

