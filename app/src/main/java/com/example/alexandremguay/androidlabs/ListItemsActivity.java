package com.example.alexandremguay.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    protected ImageButton imageButton;
    protected Switch switchButton;
    protected CheckBox checkBox;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        //https://developer.android.com/training/camera/photobasics.html
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //implicit intent!!
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    onActivityResult(10,10,takePictureIntent);
                }



            }
        });

        switchButton = findViewById(R.id.switchButton);
        switchButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                setOnCheckedChanged(switchButton);
            }
        });

        checkBox = findViewById(R.id.checkBox);
        checkBox.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                onCheckedChanged(checkBox);
            }
        });
    }

@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        //https://developer.android.com/training/camera/photobasics.html#TaskPhotoView
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        imageButton.setImageBitmap(imageBitmap);
    }
}

    protected void setOnCheckedChanged(Switch switchButton) {
        CharSequence text = "";
        int duration;

        if (switchButton.isChecked() == true) {
                text = "Switch is On";
                duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }

            if (switchButton.isChecked() == false) {
                text = "Switch is Off";
                duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
        }
    protected void onCheckedChanged(CheckBox checkBox) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
        builder.setMessage(R.string.dialog_message)
        .setTitle(R.string.dialog_title)
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { //we do not use dialog or id anywhere
                Intent resultIntent = new Intent(  );
                resultIntent.putExtra("Response", "Here you go bud!");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        })
        .show(); // all one long line of code. No repeat "builder.(...)" and no ";" --> BUILDER PATTERN
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");


    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
