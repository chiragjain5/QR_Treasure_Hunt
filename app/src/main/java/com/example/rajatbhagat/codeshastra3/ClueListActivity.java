package com.example.rajatbhagat.codeshastra3;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

public class ClueListActivity extends AppCompatActivity {

//    private ListView clueListView;
    private ProgressDialog progressDialog;
//    public String[] clueDetailArray;
    private RequestQueue requestQueue;
    private ActionBar actionBar;
    private List<Clue> clueList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ClueAdapter clueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue_list);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Scanned Clues");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_all_clues);
//        getAllCluesFromServer();
    }

    public void getClueData() {

    }

//    public void getAllCluesFromServer() {
//        String url = Constants.GET_ALL_CLUES;
//        String finalUrl = url + Constants.getUsername(ClueListActivity.this);
//
//        progressDialog = new ProgressDialog(ClueListActivity.this);
//        progressDialog.setMessage("Getting data...");
//        progressDialog.show();
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalUrl, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    Log.e("onResponse", "works");
//                    clueDetailArray = new String[response.length()];
//
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject clue = (JSONObject) response.get(i);
//                        clueDetailArray[i] = clue.getString("clue_detail");
//                        Log.e("Game Titles", clueDetailArray[i]);
//                    }
//                    Log.e("The array", clueDetailArray[0]);
//                    clueListView = (ListView) findViewById(R.id.list_view_all_clues);
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClueListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, clueDetailArray);
//                    clueListView.setAdapter(adapter);
//                } catch (Exception e) {
//                    Log.e("Error in response", e.getMessage());
//                }
//                progressDialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ClueListActivity.this, "Error in server:" + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
}
