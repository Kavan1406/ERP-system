package com.abc.pointofsale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    static String r;
    String  p1;
    EditText price2;
    private static String inserturl = //url
    private static String dinserturl = //url
    private static String durl = //url
    private static String wurl = //url
    com.android.volley.RequestQueue requestQueue;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        price2 = (EditText) findViewById(R.id.e1);
        Button btnscan = (Button)findViewById(R.id.scan);
        btnscan.setOnClickListener(this);

        SwipeButton sb = (SwipeButton)findViewById(R.id.sb);

        sb.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                gotodist();
            }
        });

        SwipeButton wb = (SwipeButton)findViewById(R.id.w1);

        wb.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                gotoware();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this,"Result Not Found", Toast.LENGTH_SHORT).show();
            }
            else{

                r=result.getContents();



                JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.POST, inserturl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray students = response.getJSONArray("students");

                            for(int i=0; i< students.length();i++) {

                                JSONObject student= students.getJSONObject(i);
                                String id = student.getString("id");

                                if(r.equals(id))
                                {
                                    count++;
                                }

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(JsonObjectRequest);


            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    public void scanow(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(Portrait.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan Your Barcode");
        integrator.initiateScan();
    }



    @Override
    public void onClick(View view) {
        scanow();

    }
    public void gotodist() {
        if(r!=null) {

            if (price2.getText().length()>0) {
                if (count > 0) {

                    StringRequest request = new StringRequest(Request.Method.POST, dinserturl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("name", Main2Activity.r);
                            parameters.put("price", price2.getText().toString());

                            return parameters;
                        }
                    };
                    requestQueue.add(request);

                    StringRequest request1 = new StringRequest(Request.Method.POST, durl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("name", Main2Activity.r);

                            return parameters;
                        }
                    };
                    requestQueue.add(request1);
                    Toast.makeText(this, "Product Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Product Doesnot Exist", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Distributor id is Required", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Scan Your Barcode", Toast.LENGTH_SHORT).show();
        }

    }

    public void gotoware() {

        if(r!=null) {
            StringRequest request = new StringRequest(Request.Method.POST, wurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("name", Main2Activity.r);


                    return parameters;
                }
            };
            requestQueue.add(request);

            Toast.makeText(this, "Product Updated", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Scan your Barcode", Toast.LENGTH_SHORT).show();
        }
    }


}
