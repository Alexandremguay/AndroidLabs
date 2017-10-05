package com.example.alexandremguay.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


//http://www.vogella.com/tutorials/AndroidListView/article.html

public class ChatWindow extends Activity {

    protected static final String ACTIVITY_NAME = "ChatWindow";

    protected ListView listView;
    protected Button send;
    protected EditText edit;
    protected ArrayList<String> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        listView = (ListView)findViewById(R.id.list); ///why do we need to cast? android says it's redundant
        send = (Button)findViewById(R.id.send);
        edit = (EditText)findViewById(R.id.addtext);
        messages = new ArrayList<>();

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (edit.getText() != null) {
                    messages.add(edit.getText().toString().trim());
                }
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/ getView()
                edit.setText(""); //clears message box, ready for next input;
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

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override //This returns the number of rows that will be in your listView
        public int getCount() {
            return messages.size();
        }

        @Override //This returns the item to show in the list at the specified position
        public String getItem(int position) {
            return messages.get(position);
        }

        @Override //This returns the layout that will be positioned at the specified row in the list
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView)result.findViewById(R.id.message_text); //we have 2 xml files with this varialbe !!
            message.setText(getItem(position)); // get the string at position



            return result;

        }

    }
}

