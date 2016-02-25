package com.aravind.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FavoriteMovieFragment extends Fragment {

    private static final String LOG = FavoriteMovieFragment.class.getSimpleName();

    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<Movie>();
    GridView gridView;

    public FavoriteMovieFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("FavoriteMovieFragment","In Fragment OnCreateView");
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        populateFavMovieListFromPrefs();
        movieAdapter = new MovieAdapter(getActivity(), movieList);
        gridView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MovieAdapter", "Intent Started !!!");
                Movie movieObject =  movieAdapter.getItem(position);
                Intent i = new Intent();
                i.setClassName("com.aravind.popularmovies2", "com.aravind.popularmovies2.MovieDetailsActivity");
                i.putExtra(getString(R.string.moveObject), movieObject);
                startActivity(i);
            }
        });
        return rootView;
    }

    /**
     * This method retrieves the movie from shared preferences
     */
    private void populateFavMovieListFromPrefs() {
        SharedPreferences mPref = getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        Map<String,String> movieMap =  (Map<String,String>)mPref.getAll();
        Gson gson = new Gson();
        for(Map.Entry<String,String> entry : movieMap.entrySet()){
            Movie movie = gson.fromJson(entry.getValue(),Movie.class);
            movieList.add(movie);
        }
    }
}
