package com.example.clientapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText txtMsg1;
    TextView output;
    Button Sender;
    Button connection;
    public Socket clientSocket ;
    public DataOutputStream outToServer;
    public DataInputStream dln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMsg1= (EditText) findViewById(R.id.txtMsg1);
        output=(TextView) findViewById(R.id.output);
        Sender = (Button) findViewById(R.id.Sender);
        connection=(Button) findViewById(R.id.connection);

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            clientSocket = new Socket("192.168.100.12", 8770);
                            outToServer = new DataOutputStream(clientSocket.getOutputStream());
                            dln = new DataInputStream(clientSocket.getInputStream());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                Sender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                                try {
                                    outToServer.writeUTF(txtMsg1.getText().toString());
                                    String modifiedSentence=dln.readUTF();
                                    output.setText(modifiedSentence);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                    }
                });
            }
        });

    }

}