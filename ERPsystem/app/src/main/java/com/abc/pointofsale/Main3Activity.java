package com.abc.pointofsale;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Main3Activity extends AppCompatActivity implements View.OnClickListener{

    static String r;
    private static String inserturl = //url
    com.android.volley.RequestQueue requestQueue;
    TextView show;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        show = (TextView) findViewById(R.id.text1);
        Button btnscan = (Button)findViewById(R.id.status);
        btnscan.setOnClickListener(this);

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

                final ProgressDialog dialog = new ProgressDialog(Main3Activity.this);

                dialog.setMessage("Loading...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                long delayInMillis = 2300;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, delayInMillis);




                JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.POST, inserturl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray students = response.getJSONArray("students");

                            for(int i=0; i< students.length();i++) {

                                JSONObject student= students.getJSONObject(i);
                                String id = student.getString("id");
                                String price = student.getString("price");
                                String date = student.getString("mfg_date");
                                String location = student.getString("location");
                                String weight = student.getString("Weight");

                                if(r.equals(id))
                                    show.append("Id : " + id + "\nPrice : " + price + "\nDate : " + date + "\nLocation : "+location+"\nWeight : " +weight +" gm");

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
}
