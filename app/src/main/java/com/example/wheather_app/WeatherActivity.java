package com.example.wheather_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WeatherActivity extends AppCompatActivity {
    private EditText geo;
    private TextView weather;
    private Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        geo = (EditText) findViewById(R.id.location);
        weather = (TextView) findViewById(R.id.weather1);
        search = (Button) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                findWeather(geo.getText().toString());
            }
        });
    }

    public void findWeather(final String city) {
        String registerApiUrl =
                "http://192.168.43.106:3000/api/weather?city=" + city ;

        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();

        StringRequest postRequest = new StringRequest(Request.Method.GET, registerApiUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        weather.setText(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        weather.setText("Error.Response");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue.add(postRequest);
    }
}










