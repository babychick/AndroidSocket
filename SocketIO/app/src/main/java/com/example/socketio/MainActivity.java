package com.example.socketio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    private EditText txt_IP;
    private EditText txt_Port;
    private Button btn_Connect;
    private Button btn_Send;
    private Socket mSocket;

    private String signal = "on";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_IP = findViewById(R.id.txt_IP);
        txt_Port = findViewById(R.id.txt_Port);
        btn_Send = findViewById(R.id.btn_Send);
        btn_Connect = findViewById(R.id.btn_Connect);

        btn_Connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (txt_IP.getText().toString() == ""
                    || txt_Port.getText().toString() == "") {
                    Toast.makeText(getBaseContext() , "Please input IP and Port", Toast.LENGTH_LONG).show();
                } else {
                    final String urlString = "http://" + txt_IP.getText().toString() + ":" + txt_Port.getText().toString();
                    try {
                        mSocket = IO.socket(urlString);
                        mSocket.connect();

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getBaseContext(), "Connected", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signal == "on")
                {
                    mSocket.emit("signal", signal);
                    signal = "off";
                    Toast.makeText( getBaseContext(), "Sent ON", Toast.LENGTH_LONG).show();
                }
                else {
                    mSocket.emit("signal", signal);
                    signal = "on";
                    Toast.makeText(getBaseContext() , "Sent OFF", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
