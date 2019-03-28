package com.amellperalta.moviesextreme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        String title = getIntent().getStringExtra("title");
        String releaseDate = getIntent().getStringExtra("release_date");
        String overview = getIntent().getStringExtra("overview");

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView overviewTextView = findViewById(R.id.overviewTextView);

        titleTextView.setText("Title: " + title);
        dateTextView.setText("Released Date: " + releaseDate);
        overviewTextView.setText("Overview: " + overview);
    }
}
