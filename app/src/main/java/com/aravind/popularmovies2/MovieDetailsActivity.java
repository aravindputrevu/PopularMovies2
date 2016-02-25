package com.aravind.popularmovies2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, new MovieDetailsFragment()).commit();

        }

    }




}
