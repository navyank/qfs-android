package com.example.qiuck_fix_sorter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ViewComplaintReply extends AppCompatActivity implements AdapterView.OnItemClickListener {
     ListView l1;
     SharedPreferences sh;
     ArrayList<String>comp,date,reply,cid,status;
     String url,cdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint_reply);
        l1=findViewById(R.id.list1);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/view_reply_comp";
        RequestQueue queue = Volley.newRequestQueue(ViewComplaintReply.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    cid= new ArrayList<>();
                    comp= new ArrayList<>();
                    date= new ArrayList<>();
                    reply= new ArrayList<>();
                    status= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                       cid.add(jo.getString("id"));
                       comp.add(jo.getString("complaint"));
                        date.add(jo.getString("date"));
                        reply.add(jo.getString("reply"));
                        status.add(jo.getString("status"));


                  }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new customeimage(ViewComplaintReply.this,comp,date,reply));
                    l1.setOnItemClickListener(ViewComplaintReply.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ViewComplaintReply.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        cdate = date.get(i);
        Date userDob = null;
        try {
            userDob = new SimpleDateFormat("yyyy-MM-dd").parse(cdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date today = new Date();
        long diff = today.getTime() - userDob.getTime();
        int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
        Toast.makeText(ViewComplaintReply.this, "days: " + numOfDays, Toast.LENGTH_SHORT).show();

        if (numOfDays >= 0) {
            if ((numOfDays <= 2) && (reply.get(i).equals("pending")))
            {

                AlertDialog.Builder ald = new AlertDialog.Builder(ViewComplaintReply.this);
                ald.setTitle("File")
                        .setPositiveButton(" Delete ", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {

                                    RequestQueue queue = Volley.newRequestQueue(ViewComplaintReply.this);
                                    url = "http://" + sh.getString("ip", "") + ":5000/deletecomplaint";

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

                                                    Intent ik1 = new Intent(getApplicationContext(), ViewComplaintReply.class);
                                                    startActivity(ik1);

                                                } else {

                                                    Toast.makeText(ViewComplaintReply.this, "Invalid ", Toast.LENGTH_SHORT).show();

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
                                            params.put("cid", cid.get(i));

                                            return params;
                                        }
                                    };
                                    queue.add(stringRequest);


                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        });

                AlertDialog al = ald.create();
                al.show();
            }
        }
    }
}