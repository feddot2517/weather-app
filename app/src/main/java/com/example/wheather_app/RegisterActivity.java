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

public class RegisterActivity extends AppCompatActivity {
    private EditText email, password;
    private TextView responseRes;
    private Button signButton, gotoLogin;

    private static final int CONNECTION_TIMEOUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.email2);
        password = (EditText) findViewById(R.id.password2);
        signButton = (Button) findViewById(R.id.button);
        responseRes = (TextView) findViewById(R.id.response_res2);
        gotoLogin = (Button) findViewById(R.id.button3);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                loginUser(email.getText().toString(), password.getText().toString());
            }
        });
    }

    public void loginUser(final String email, final String password) {
        String registerApiUrl =
                "http://192.168.43.106:3000/api/register";

        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();

        StringRequest postRequest = new StringRequest(Request.Method.POST, registerApiUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("user already registred")) {
                            Log.d("register api response", response);
                            responseRes.setText(response);
                        }else {
                            Intent intent = new Intent(RegisterActivity.this, WeatherActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseRes.setText("Error.Response");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", email);
                params.put("password", password);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }
}
