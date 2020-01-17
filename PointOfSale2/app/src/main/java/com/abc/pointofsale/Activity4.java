package com.abc.pointofsale;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;



public class Activity4 extends AppCompatActivity  {


    static String r;
    int c1=0,total=0;
    private static String iurl = //link to php script for inserting values
    private static String gurl = //link to php script to get values
    EditText cid;
    EditText g1,cname;
    com.android.volley.RequestQueue requestQueue;
    Button bt,bt2;
    String quantity,t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        cid=(EditText)findViewById(R.id.ecid);
        g1=(EditText)findViewById(R.id.egst);
        cname=(EditText)findViewById(R.id.cid);
       bt=(Button)findViewById(R.id.scan);
       bt2=findViewById(R.id.invoice);

       bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               scanow();
           }
       });

       bt2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               gen();
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
                StringRequest request = new StringRequest(Request.Method.POST, iurl, new Response.Listener<String>() {
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
                        parameters.put("name", r);
                        parameters.put("price",cname.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);




               c1++;



                scanow();



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

    public void gen(){

        if(cname.getText().length()>0) {

            if(cid.getText().length()>0) {


                if(g1.getText().length()>0) {

                    if(c1>0) {



                        quantity = String.valueOf(c1);
                        total = c1 * 100;
                        t1 = String.valueOf(total);



                        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
                        alertdialogbuilder.setMessage("Customer Id : " +cid.getText() +"\nGST no : " +g1.getText()+"\nQuantity : "+quantity+"\nTotal : "+t1 );
                        alertdialogbuilder.setTitle("Invoice");
                        alertdialogbuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                StringRequest request1 = new StringRequest(Request.Method.POST, gurl, new Response.Listener<String>() {
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
                                        parameters.put("name", cid.getText().toString());
                                        parameters.put("price", g1.getText().toString());
                                        parameters.put("cname", cname.getText().toString());
                                        parameters.put("quantity", quantity);
                                        parameters.put("total", t1);


                                        return parameters;
                                    }
                                };
                                requestQueue.add(request1);
                            }
                        });
                        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        AlertDialog alertDialog = alertdialogbuilder.create();
                        alertDialog.show();



                    }
                    else{
                        Toast.makeText(this, "Please scan your product", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(this, "Please enter GST number", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Please enter customer name", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Please enter customer id", Toast.LENGTH_SHORT).show();
        }


    }

}


