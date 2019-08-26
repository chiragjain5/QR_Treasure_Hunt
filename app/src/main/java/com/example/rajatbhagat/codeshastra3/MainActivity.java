package com.example.rajatbhagat.codeshastra3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    public String USERNAME;

    private Button loginButton;
    private Button registerButton;

    private RequestQueue requestQueue;

    private String email;
    private String savedEmail;
    private String savedName;
    private boolean savedLoginStatus = false;

    private ActionBar actionBar;

    private ProgressDialog progressDialog;

    final static int PASSWORD_MIN_LENGTH = 6;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOurSharedPreferences();
        Log.e("login stat", "" + savedLoginStatus);
        if (savedLoginStatus) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loggin in...");
            progressDialog.show();

            Intent intent = new Intent(this, CentralPanel.class);
            startActivity(intent);
            progressDialog.dismiss();

        } else {

            actionBar = getSupportActionBar();
            actionBar.setTitle("Login");

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            if (savedLoginStatus) {
                progressDialog.dismiss();
                Intent intent = new Intent(this, CentralPanel.class);
                intent.putExtra("teamName", savedName);
                intent.putExtra("teamEmail", savedEmail);
                startActivity(intent);
            } else {
                progressDialog.dismiss();
                usernameEditText = (EditText) findViewById(R.id.edit_text_username);
                passwordEditText = (EditText) findViewById(R.id.edit_text_password);

                loginButton = (Button) findViewById(R.id.button_login);
                registerButton = (Button) findViewById(R.id.button_register);

                progressDialog = new ProgressDialog(MainActivity.this);

                loginButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
//                 Form Validator and Intent start
                        if (!false) {
                            Log.e("button clicke", "chala");
                            progressDialog.setMessage("Logging in...");
                            progressDialog.show();
                            login();
                            Log.e("username", savedName);
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid entry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                registerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                });
            }
        }
    }

    private void login() {
        String url = Constants.LOGIN_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Email", response);
                email = response;
//                name = response.substring(response.indexOf('-') + 1, response.length() - 1);
//                firstName = name.substring(0, name.indexOf('-'));
//                lastName = name.substring(name.indexOf('-') + 1, name.length());
                setSharedPreferences();
                USERNAME = usernameEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this, CentralPanel.class);
                intent.putExtra("emailUser", email);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;

                if (networkResponse != null && error.networkResponse.statusCode == 401)
                    Toast.makeText(MainActivity.this, "Wrong username/password", Toast.LENGTH_SHORT).show();
                else if (networkResponse != null && error.networkResponse.statusCode == 400)
                    Toast.makeText(MainActivity.this, "Error logging in", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("Error: ", error.getMessage());
            }
        }) {
            //This method sends data to the server
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernameEditText.getText().toString());
                params.put("password", passwordEditText.getText().toString());
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void setLoginStatus() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.KEY_LOGIN_STATUS, false);
        editor.commit();
    }

    public void setSharedPreferences() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_USERNAME, usernameEditText.getText().toString().trim());
        editor.putBoolean(Constants.KEY_LOGIN_STATUS, true);
        editor.putString(Constants.KEY_EMAIL, email);
//        editor.putString(Constants.KEY_FIRST_NAME, firstName);
//        editor.putString(Constants.KEY_LAST_NAME, lastName);
        editor.apply();
        editor.commit();
    }

    public void getOurSharedPreferences() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        savedName = preferences.getString(Constants.KEY_USERNAME, "username");
        savedEmail = preferences.getString(Constants.KEY_EMAIL, email);
        savedLoginStatus = preferences.getBoolean(Constants.KEY_LOGIN_STATUS, true);
    }
}
