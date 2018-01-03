package com.example.alexandremguay.androidlabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolBar extends AppCompatActivity {

    protected FloatingActionButton fab;
    protected AlertDialog.Builder builder;
    protected EditText message;
    protected int k;
    protected String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        builder = new AlertDialog.Builder(this);
        message = findViewById(R.id.newMessage);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You clicked the mail icon.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, m);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {

        k = mi.getItemId();
        switch (k) {
            case R.id.action_one:
                Snackbar.make(this.findViewById(k), "You chose option 1, the User!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

            case R.id.action_two: {
                builder.setTitle(R.string.title).setPositiveButton(R.string.clickOK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(TestToolBar.this, StartActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton(R.string.clickCancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
            break;

            case R.id.action_three: {
                LayoutInflater inflater = this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.customlayout, null)).setPositiveButton(R.string.clickOK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        msg = message.getText().toString();
                        snacker();
                    }
                }).setNegativeButton(R.string.clickCancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
            break;

            case R.id.about: {
                CharSequence text = "";
                int duration;

                text = "Version 1.0 by Alexandre Guay";
                duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
            break;

            default:
                return super.onOptionsItemSelected(mi);
        }

        return true;
    }


    public void snacker () {
        Snackbar.make(this.findViewById(k), msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
