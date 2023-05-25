package com.example.qiuck_fix_sorter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    RadioButton r1,r2,r3;
    Button b1;
    SharedPreferences sh;
    String fname,lname,place,post,pin,phone,gender,dob,email,username,password,url;
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        e1=findViewById(R.id.editTextTextPersonName3);
        e2=findViewById(R.id.editTextTextPersonName5);
        e3=findViewById(R.id.editTextTextPersonName6);
        e4=findViewById(R.id.editTextTextPersonName7);
        e5=findViewById(R.id.editTextTextPersonName8);
        e6=findViewById(R.id.editTextTextPersonName9);
        e7=findViewById(R.id.editTextDate2);
        e8=findViewById(R.id.editTextTextPersonName10);
        e9=findViewById(R.id.editTextTextPersonName11);
        e10=findViewById(R.id.editTextTextPassword2);
        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton2);
        r3=findViewById(R.id.radioButton3);
        b1=findViewById(R.id.button3);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        e7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Signup.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = e1.getText().toString();
                lname = e2.getText().toString();
                place = e3.getText().toString();
                post = e4.getText().toString();
                pin = e5.getText().toString();
                phone = e6.getText().toString();
                if (r1.isChecked()) {
                    gender = r1.getText().toString();
                } else if (r2.isChecked()) {
                    gender = r2.getText().toString();
                } else {
                    gender = r3.getText().toString();
                }
                dob = e7.getText().toString();
                email = e8.getText().toString();
                username = e9.getText().toString();
                password = e10.getText().toString();
                if (fname.equalsIgnoreCase("")) {
                    e1.setError("Enter first Name");
                } else if (!fname.matches("^[a-z ,A-Z]*$")) {
                    e1.setError("Characters Allowed");
                    e1.requestFocus();
                } else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Enter Second Name");
                } else if (!lname.matches("^[a-z ,A-Z]*$")) {
                    e2.setError("Characters Allowed");
                    e2.requestFocus();
                } else if (place.equalsIgnoreCase("")) {
                    e3.setError("Enter Place");
                } else if (!place.matches("^[a-z ,A-Z]*$")) {
                    e3.setError("Characters Allowed");
                    e3.requestFocus();
                } else if (post.equalsIgnoreCase("")) {
                    e4.setError("Enter Post");
                } else if (!post.matches("^[a-z ,A-Z]*$")) {
                    e4.setError("Characters Allowed");
                    e4.requestFocus();
                } else if (pin.equalsIgnoreCase("")) {
                    e5.setError("Enter Pin");
                } else if (pin.length() != 6) {
                    Toast.makeText(getApplicationContext(),pin.length() +"",Toast.LENGTH_LONG).show();
                    e5.setError(" Minimum 6 No.s required");
                    e5.requestFocus();
                } else if (email.equalsIgnoreCase("")) {
                    e8.setError("Enter E-mail");
                } else if (dob.equalsIgnoreCase("")) {
                    e7.setError("Enter Date of birth");
                } else if (phone.equalsIgnoreCase("")) {
                    e6.setError("Enter Phone Number");
                } else if (phone.length() != 10) {
                    e6.setError("Minimum 10 No.s Required");
                    e6.requestFocus();
                } else if (username.equalsIgnoreCase("")) {
                    e9.setError("Enter Username");
                } else if (!username.matches("^[a-z,A-Z]*$")) {
                    e9.setError("Characters Allowed");
                    e9.requestFocus();
                } else if (password.equalsIgnoreCase("")) {
                    e10.setError("Enter Password");
                } else {

                    RequestQueue queue = Volley.newRequestQueue(Signup.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/registration";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {
                                    Toast.makeText(Signup.this, "success", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), Login.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(Signup.this, "Failed", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Fname", fname);
                            params.put("Lname", lname);
                            params.put("place", place);
                            params.put("post", post);
                            params.put("pin_code", pin);
                            params.put("phone_number", phone);
                            params.put("gender", gender);
                            params.put("dob", dob);
                            params.put("email_id", email);
                            params.put("username", username);
                            params.put("password", password);

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }

        });


    }

    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        e7.setText(dateFormat.format(myCalendar.getTime()));
    }

}