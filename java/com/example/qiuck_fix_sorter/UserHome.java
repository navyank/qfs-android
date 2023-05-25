package com.example.qiuck_fix_sorter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class UserHome extends AppCompatActivity {
    Button b1,b2,b3,b4;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        b1=findViewById(R.id.button4);
        b2=findViewById(R.id.button7);
        b3=findViewById(R.id.button8);
        b4=findViewById(R.id.button9);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(), SendComplaint.class);
                startActivity(i);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i1= new Intent(getApplicationContext(), ViewComplaintReply.class);
               startActivity(i1);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2= new Intent(getApplicationContext(), SendFeedback.class);
                startActivity(i2);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i3= new Intent(getApplicationContext(), Login.class);
              startActivity(i3);

            }
        });
    }
}