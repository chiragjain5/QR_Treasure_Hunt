package com.example.rajatbhagat.codeshastra3;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ClueDetails extends AppCompatActivity {

    TextView clueDetailTextView;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue_details);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Clue Details");

        clueDetailTextView = (TextView) findViewById(R.id.clue_detail_text_view);

        clueDetailTextView.setText(getIntent().getStringExtra("clue"));
    }
}
