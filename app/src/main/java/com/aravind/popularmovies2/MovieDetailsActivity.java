package com.aravind.popularmovies2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aravind.popularmovies2.fragments.MovieDetailsFragment;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        if (savedInstanceState == null) {
            MovieDetailsFragment mf = new MovieDetailsFragment();
            Bundle args = new Bundle();
            args.putParcelable("movie_object", getIntent().getParcelableExtra(getString(R.string.moveObject)));
            mf.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, mf).commit();

        }

    }


}
