package com.example.alexandremguay.android;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alexandre M Guay on 2017-12-06.
 */

public class MessageFragment extends Fragment {



    protected static final String ACTIVITY_NAME = "MessageFragment";

    private View view;
    private TextView textView_message, textView_id;
    private Button button_delete, button_cancel;

    @Override  // you must use the onCreateView() callback to define the layout
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) { //This is where to inflate the UI
        view = inflater.inflate(R.layout.activity_message_details, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textView_message = view.findViewById(R.id.textView1);
        textView_id = view.findViewById(R.id.textView2);
        button_delete = view.findViewById(R.id.deleteButton);
        button_cancel = view.findViewById(R.id.cancelButton);

        String message = getArguments().getString("Message");
        final String id = getArguments().getString("ItemId");
        textView_message.setText(message);
        textView_id.setText(id);

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity().getLocalClassName().equals("MessageDetails")){
                    final Intent resultIntent = new Intent();
                    resultIntent.putExtra("ItemId", id);
                    getActivity().setResult(1,resultIntent);
                    getActivity().finish();
                }else{
            //        ((ChatWindow) getActivity()).deleteMessage(id);
                }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity().getLocalClassName().equals("MessageDetails")){
                    getActivity().finish();
                }else{

                }
            }
        });
    }
}

