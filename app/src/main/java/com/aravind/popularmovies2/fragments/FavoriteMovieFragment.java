package com.aravind.popularmovies2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aravind.popularmovies2.Constants;
import com.aravind.popularmovies2.MovieClickedCallback;
import com.aravind.popularmovies2.R;
import com.aravind.popularmovies2.adapter.MovieAdapter;
import com.aravind.popularmovies2.model.Movie;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FavoriteMovieFragment extends Fragment {

    private GridView gridView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<Movie>();

    public FavoriteMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("FavoriteMovieFragment", "In Fragment OnCreateView");
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        populateFavMovieListFromPrefs();
        movieAdapter = new MovieAdapter(getActivity(), movieList);
        gridView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MovieClickedCallback) getActivity()).onMovieClicked(position, movieAdapter);
            }
        });
        return rootView;
    }

    /**
     * This method retrieves the movie from shared preferences
     */
    private void populateFavMovieListFromPrefs() {
        SharedPreferences mPref = getActivity().getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        Map<String, String> movieMap = (Map<String, String>) mPref.getAll();
        Gson gson = new Gson();
        for (Map.Entry<String, String> entry : movieMap.entrySet()) {
            Movie movie = gson.fromJson(entry.getValue(), Movie.class);
            movieList.add(movie);
        }
    }
}
