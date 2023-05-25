package com.example.qiuck_fix_sorter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IP extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        e1=findViewById(R.id.editTextTextPersonName4);
        b1=findViewById(R.id.button5);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ip=e1.getText().toString();
                if(ip.equalsIgnoreCase(""))
                {
                    e1.setError("Enter ip");
                }
                else {
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("ip", ip);
                    ed.commit();
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                }
            }

        });
    }
}