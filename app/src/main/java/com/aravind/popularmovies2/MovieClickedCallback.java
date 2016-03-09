package com.aravind.popularmovies2;


import com.aravind.popularmovies2.adapter.MovieAdapter;

public interface MovieClickedCallback {

    void onMovieClicked(int moviePosition, MovieAdapter movieAdapter);
}
