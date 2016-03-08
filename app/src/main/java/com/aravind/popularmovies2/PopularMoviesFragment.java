package com.aravind.popularmovies2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PopularMoviesFragment extends Fragment {

    private static final String LOG = PopularMoviesFragment.class.getSimpleName();
    GridView gridView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<Movie>();
    private boolean isTablet = false;
    public PopularMoviesFragment() {
    }

    public void callAPI(Activity activity, GridView view, String url) {
        new CallMovieDBAPI(activity, view).execute(url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("PopularMovieFragment", "Fragment OnCreateView");
        isTablet = (boolean)this.getArguments().get("isTablet");
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        callAPI(getActivity(), gridView, Util.constructAPIURL(Constants.SORT_BY_POPULARITY, "1"));
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((MovieClickedCallback) getActivity()).onMovieClicked(position, movieAdapter);
            }
        });
        return rootView;
    }

    public class CallMovieDBAPI extends AsyncTask<String, Void, List<Movie>> {

        ProgressDialog dialog;
        private Activity activity;
        private GridView gridView;

        public CallMovieDBAPI(Activity activity, GridView gridView) {
            this.activity = activity;
            this.gridView = gridView;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(activity.getString(R.string.progressDialogText));
            dialog.show();
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            InputStream in = null;
            String jsonResponse = null;
            String apiURL = params[0];
            try {

                URL url = new URL(apiURL);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                jsonResponse = getJsonObject(in);
                movieList = JsonParser.parse(jsonResponse);

            } catch (Exception e) {

                Log.e("CallMovieDBAPI", "Exception occurred in AsyncTask - CallMovieDBAPI", e);
            }

            return movieList;


        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            Log.d("Popular Movies", "Got data successfully");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            movieAdapter = new MovieAdapter(activity, movieList);
            gridView.setAdapter(movieAdapter);
            movieAdapter.notifyDataSetChanged();
            if(isTablet){
                ((MovieClickedCallback) getActivity()).onMovieClicked(0, movieAdapter);
            }

        }

        private String getJsonObject(InputStream in) throws IOException {
            String line = null;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        }
    }

}
