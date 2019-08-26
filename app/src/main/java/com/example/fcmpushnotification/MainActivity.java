package com.example.fcmpushnotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button send;
    EditText title, message;
    public String token1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            Intent intent = new Intent(this, MessageReceiver.class);
            intent.putExtra("message_title", getIntent().getExtras().getString("message_title"));
            intent.putExtra("message_body", getIntent().getExtras().getString("message_body"));
            startActivity(intent);
        }
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("fcm_token", "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                token1 = task.getResult().getToken();
                                String token = getString(R.string.msg_token_fmt, token1).substring(18);
                                Log.d("fcm_token", token);
                                MessageData messageData = new MessageData(title.getText().toString(), message.getText().toString());
                                ApiInstance.getInstance().sendMessage(messageData, token).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(getApplicationContext(), "Message send", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.i("fcm_", t.getMessage());
                                        Toast.makeText(getApplicationContext(), "Message send failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
            }
        });


        // To get token any time use below commented code

//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("fcm_tokenfcm_token", "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//
//                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d("fcm_token", msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

        // To subscribe to some channel

//        FirebaseMessaging.getInstance().subscribeToTopic("weather")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = getString(R.string.msg_token_fmt);
//                        if (!task.isSuccessful()) {
//                            msg = getString(R.string.msg_token_fmt);
//                        }
//                        Log.d("fcm_token", msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });


// To unsubscribe from a channel

//        FirebaseMessaging.getInstance().unsubscribeFromTopic("weather");


    }

}
