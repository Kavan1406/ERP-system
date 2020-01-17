package com.abc.pointofsale;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    static String r;
    static String price = "100" ;
    int c1=0;
    private static String url =//url
    private static String inserturl = //url
    com.android.volley.RequestQueue requestQueue;
    ImageView add1;
    ImageView add2,add3,add4;
    ImageView bgapp, clover;

    Animation frombottom1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frombottom1 = AnimationUtils.loadAnimation(this, R.anim.frombottom);


        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);

        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        add1=(ImageView)findViewById(R.id.imageView5);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoac1();
            }
        });
        add2=(ImageView)findViewById(R.id.imageView6);
        add3=(ImageView)findViewById(R.id.imageView8);
        add4=(ImageView)findViewById(R.id.imageView2);

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoac2();
            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoac3();
            }
        });

        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoac4();
            }
        });

    }



    public void gotoac1() {
        Intent intent = new Intent (this, add.class);
        startActivity(intent);
    }

    public void gotoac2() {
       Intent intent = new Intent (this, Main2Activity.class);
       startActivity(intent);
    }
    public void gotoac3() {
        Intent intent = new Intent (this, Main3Activity.class);
        startActivity(intent);
    }
    public void gotoac4() {
        Intent intent = new Intent (this, Activity4.class);
        startActivity(intent);
    }


}


