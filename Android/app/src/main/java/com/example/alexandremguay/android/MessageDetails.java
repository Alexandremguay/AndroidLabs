package com.example.alexandremguay.android;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import static com.example.alexandremguay.android.ChatDatabaseHelper.c;

public class MessageDetails extends Activity {

  //  protected Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle info = getIntent().getExtras();

        info.putString("Key", "From phone");
        FragmentTransaction ft =  getFragmentManager().beginTransaction();
        MessageFragment df = new MessageFragment();
        df.setArguments(info);
        ft.add(R.id.frame, df );
        ft.commit();

//        delete = findViewById(R.id.deleteButton);
//        delete.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                getActivity().setResult(int resultCode, Intent data)
//            }
//        });

        Bundle bundle = new Bundle();
        bundle.putString("Message", getIntent().getStringExtra("Message"));
        bundle.putString("ItemId", getIntent().getStringExtra("ItemId"));

        Fragment fragment = new MessageFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.phoneLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        finish();
    }
}