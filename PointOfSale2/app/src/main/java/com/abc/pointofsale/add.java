package com.abc.pointofsale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Timer;
import java.util.TimerTask;

public class add extends AppCompatActivity {

    static String r;
    static String price = "100" ;
    int c1=0;
    private static String url = //url
    private static String inserturl = //link to php script for inserting
    com.android.volley.RequestQueue requestQueue;
    Button scannow;
    EditText name,price1,weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        scannow =(Button) findViewById(R.id.scan);
        name =(EditText) findViewById(R.id.name);
        price1=(EditText) findViewById(R.id.price);
        weight=(EditText)findViewById(R.id.weight);
        scannow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanow();
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



                JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray students = response.getJSONArray("students");

                            for(int i=0; i< students.length();i++) {

                                JSONObject student= students.getJSONObject(i);
                                String id = student.getString("id");

                                if(r.equals(id))
                                {
                                    c1++;
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

                final ProgressDialog dialog = new ProgressDialog(add.this);

                dialog.setMessage("Creating Product...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                long delayInMillis = 2000;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, delayInMillis);


                if(c1==0) {
                    StringRequest request = new StringRequest(Request.Method.POST, inserturl, new Response.Listener<String>() {
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
                            parameters.put("id", r);
                            parameters.put("price", price1.getText().toString());
                            parameters.put("weight", weight.getText().toString());
                            parameters.put("name", name.getText().toString());

                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                    Toast.makeText(this,"Product Created", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(this,"Product Already Exists ", Toast.LENGTH_SHORT).show();
                }
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



}
