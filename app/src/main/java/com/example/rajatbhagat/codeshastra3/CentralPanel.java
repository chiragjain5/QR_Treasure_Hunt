package com.example.rajatbhagat.codeshastra3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CentralPanel extends AppCompatActivity {

    private Button scanButton;
    private Button logoutButton;
    private Button seeClueListButton;

    private ProgressDialog progressDialog;

    private String clueDetails;
    private String clueToken;

    private RequestQueue requestQueue;
    private ActionBar actionBar;
    private boolean isActive;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_panel);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Treasure Hunt");

        scanButton = (Button) findViewById(R.id.button_scan);
        logoutButton = (Button) findViewById(R.id.button_logout);
        seeClueListButton = (Button)findViewById(R.id.button_get_scanned_clues);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntentToScanner();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();

                Intent intent = new Intent(CentralPanel.this, MainActivity.class);
                startActivity(intent);
            }
        });

        seeClueListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CentralPanel.this, ClueListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void sendIntentToScanner() {
        try {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            InstallDialogFragment idf = new InstallDialogFragment();
            idf.show(this.getFragmentManager(), "Error");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == Activity.RESULT_OK) {
                clueDetails = data.getStringExtra("SCAN_RESULT");
                int colonIndex = clueDetails.indexOf(':');

                clueToken = clueDetails.substring(colonIndex + 1, clueDetails.length());

                String finalClue = clueDetails.substring(0, colonIndex);

                Log.e("Scanned Data: ", "on activity result clueDetails:" + finalClue);
                Log.e("Scan", "token: " + clueToken);

                progressDialog = new ProgressDialog(CentralPanel.this);
                progressDialog.setMessage("Getting the clue  ..");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CLUE_DETAILS_URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(CentralPanel.this, "Data sent to server", Toast.LENGTH_SHORT).show();
                        Log.e("on scanning", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(CentralPanel.this, "Some error occured in getting the clue..", Toast.LENGTH_SHORT).show();
                        Log.e("error in sending data", error.getMessage());
                    }
                }) {
                    //This method sends data to the server
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("team_name", Constants.getUsername(CentralPanel.this));
                        params.put("clue_token", clueToken);
//                        params.put("email", email);
//                        params.put("password", password);
                        return params;
                    }
                };

                requestQueue = Volley.newRequestQueue(CentralPanel.this);
                requestQueue.add(stringRequest);

                Intent intent = new Intent(this, ClueDetails.class);
                intent.putExtra("clue", finalClue);
                startActivity(intent);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Scanning Caancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void logout() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.KEY_LOGIN_STATUS, false);
        editor.commit();

    }
}
