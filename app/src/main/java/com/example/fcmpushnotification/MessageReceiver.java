package com.example.fcmpushnotification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MessageReceiver extends AppCompatActivity {
    TextView body, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_receiver);

        title = findViewById(R.id.message_title);
        body = findViewById(R.id.message_body);

        if (getIntent().getExtras() != null) {
            title.setText(getIntent().getExtras().getString("message_title"));
            body.setText(getIntent().getExtras().getString("message_body"));
        }
    }
}
