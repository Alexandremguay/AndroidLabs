package com.example.alexandremguay.android;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.alexandremguay.android.ChatDatabaseHelper.c;
//http://www.vogella.com/tutorials/AndroidListView/article.html

public class ChatWindow extends Activity {

    protected static final String ACTIVITY_NAME = "ChatWindow";
    protected ListView listView;
    protected Button send;
    protected EditText edit;
    protected ArrayList<String> messages;
    private SQLiteDatabase db;
    protected FrameLayout frame;
    protected boolean frameCheck = false;
    protected Cursor z;
    long curID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        listView = findViewById(R.id.list); ///why do we need to cast? android says it's redundant
        send = findViewById(R.id.send);
        edit = findViewById(R.id.addtext);
        messages = new ArrayList<>();
        frame = findViewById(R.id.frame);

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        final ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        z = db.query(false, ChatDatabaseHelper.c, new String[]{ChatDatabaseHelper.i, ChatDatabaseHelper.m}, null, null, null, null, null, null);
        int columnIndex = z.getColumnIndex(ChatDatabaseHelper.m);
        z.moveToFirst();

        while (!z.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + z.getString(z.getColumnIndex(ChatDatabaseHelper.m)));
            Log.i(ACTIVITY_NAME, "column name=" + z.getColumnName(columnIndex));
            String msg = z.getString(columnIndex);
            messages.add(msg);
            z.moveToNext();
        }

        Log.i(ACTIVITY_NAME, "Cursor's column count = " + z.getColumnCount());

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String x = edit.getText().toString().trim();
                ContentValues cValues = new ContentValues();
                cValues.put(ChatDatabaseHelper.m, x);
                db.insert(c, "NullColumnName", cValues);
                messages.add(x);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/ getView()
                edit.setText("");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int frameType = 0;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle info = new Bundle();
                curID = messageAdapter.getItemId(position);
                Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                info.putString("msg", messages.get(position));
                info.putString("id", String.valueOf(curID));
                info.putBoolean("isTablet", frameCheck);
                info.putString("type", String.valueOf(frameType));
                info.putString("pos", String.valueOf(position));
                info.putStringArrayList("msgList", messages);
                intent.putExtras(info);

                if (frameCheck) {
                    MessageFragment mf1 = new MessageFragment();
                    mf1.setArguments(info);
                    getFragmentManager().beginTransaction().add(R.id.frame, mf1).commit();
                } else {
                    startActivityForResult(intent, 2, info);

                }

            }
        });

    }

//                String item = parent.getItemAtPosition(position).toString();
//                Long itemId = parent.getItemIdAtPosition(position);
//
//                if (frame == null) {
//                    Log.i(ACTIVITY_NAME, "We are using a phone");
//                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
//                    intent.putExtra("Message", item);
//                    intent.putExtra("ItemId", itemId + "");
//                    startActivityForResult(intent, 1);
//                } else {
//                    frameCheck = true;
//                    Log.i(ACTIVITY_NAME, "FrameLayout has been loaded; We are using Tablet Layout");
//                    Fragment fragment = new MessageFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("Message", item);
//                    bundle.putString("ItemId", itemId + "");
//                    fragment.setArguments(bundle);
//                    FragmentTransaction fragmentTransaction =
//                            getFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.frame, fragment);
//                    fragmentTransaction.commit();
//                }
//            }


        @Override
        protected void onStart () {
            super.onStart();
            Log.i(ACTIVITY_NAME, "In onStart()");
        }

        @Override
        protected void onResume () {
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
            db.close();
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

            @Override
            public long getItemId(int position) {
                z.moveToPosition(position);
                return z.getColumnIndex(ChatDatabaseHelper.i);
            }

            @Override
            //This returns the layout that will be positioned at the specified row in the list
            public View getView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = getLayoutInflater();
                View result;

                if (position % 2 == 0) {
                    result = inflater.inflate(R.layout.chat_row_incoming, null);
                } else {
                    result = inflater.inflate(R.layout.chat_row_outgoing, null);
                }

                TextView message = result.findViewById(R.id.message_text); //we have 2 xml files with this variable !!
                message.setText(getItem(position)); // get the string at position

                return result;

            }
        }
    }

//    public void closeSideBar() {
//        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.frame)).commit();
//    }






