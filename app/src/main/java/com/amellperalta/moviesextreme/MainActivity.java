package com.amellperalta.moviesextreme;

/**
 * Movies Extreme is an Android app that retrieves the most popular movies, displays them in a list,
 * and allows the user to click a movie in the list to display more information about it.
 *
 * @author Amell Peralta
 * @date 3/28/2019
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> movies;
    private List<String> dates;
    private List<String> overviews;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        movies = new ArrayList<>();
        dates = new ArrayList<>();
        overviews = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movies);
        listView.setAdapter(arrayAdapter);

        new MovieInfoDownloader().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                intent.putExtra("title", movies.get(position));
                intent.putExtra("release_date", dates.get(position));
                intent.putExtra("overview", overviews.get(position));
                startActivity(intent);
            }
        });
    }

    private class MovieInfoDownloader extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String out = "";
            try {
                String url = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=YOUR_API_KEY";
                out = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return out;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject1 = new JSONObject(s);
                JSONArray results = jsonObject1.getJSONArray("results");
                for (int i = 0; i < results.length(); ++i) {
                    JSONObject jsonObject2 = (JSONObject) results.get(i);
                    String title = jsonObject2.getString("title");
                    String date = jsonObject2.getString("release_date");
                    String overview = jsonObject2.getString("overview");
                    movies.add(title);
                    dates.add(date);
                    overviews.add(overview);
                }
                arrayAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
