package com.aravind.popularmovies2;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by DELL on 23-Feb-16.
 */
public class MovieDetailsFragment extends Fragment implements View.OnClickListener {

    Movie movie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_detail, container, false);
       // movie = getActivity().getIntent().getParcelableExtra(getString(R.string.moveObject));
         movie = (Movie)this.getArguments().get("movie_object");
        if(movie != null){
            String trailerURL = String.format(Util.constructAPIURL(Constants.FETCH_MOVIE_TRAILERS), movie.getId());
            String reviewURL = String.format(Util.constructAPIURL(Constants.FETCH_MOVIE_REVIEWS), movie.getId());
            try {
                movie = new CallMovieDBAPI(getActivity(), movie).execute(trailerURL).get();
                movie = new CallMovieDBAPI(getActivity(), movie).execute(reviewURL).get();
            } catch (InterruptedException e) {
                Log.d("MovieDetailsActivity", "Interrupted exception while getting review/trailers details", e);
            } catch (ExecutionException e) {
                Log.d("MovieDetailsActivity", "Execution exception while getting review/trailers details");
            }

            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
            collapsingToolbarLayout.setTitle(movie.getTitle());

            ImageView big_poster = (ImageView) rootView.findViewById(R.id.movie_big_image);
            Picasso.with(getActivity().getApplicationContext()).load(Constants.MOVIE_POSTER_PATH_BIG + movie.getBackdropPath())
                    .placeholder(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.movie)).error(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.movie)).into(big_poster);

            TextView releaseDateTV = (TextView) rootView.findViewById(R.id.release_date);
            releaseDateTV.setText(movie.getReleaseDate());

            TextView voteAvgTV = (TextView) rootView.findViewById(R.id.vote_count);
            voteAvgTV.setText(movie.getVoteAvg());


            TextView plotTV = (TextView) rootView.findViewById(R.id.plot);
            String noOverView = getString(R.string.no_overview);
            if (!TextUtils.isEmpty(movie.getOverview())) {
                noOverView = movie.getOverview();
            }
            plotTV.setText(noOverView);

            TextView reviewTV = (TextView) rootView.findViewById(R.id.user_review);
            TextView authorTV = (TextView) rootView.findViewById(R.id.author);
            String noUserReview = getString(R.string.no_user_review);

            if (!movie.getReviewList().isEmpty()) {
                Review review = movie.getReviewList().get(0);
                authorTV.setText(review.getAuthor());
                noUserReview = review.getContent();
            }
            reviewTV.setText(noUserReview);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.ytb_linear_layout);

            for (YTBTrailer trailer : movie.getTrailerList()) {
                ImageView ytbTrailerIV = new ImageView(getActivity());
                ytbTrailerIV.setLayoutParams(layoutParams);
                ytbTrailerIV.setImageResource(R.drawable.ytb_ic);
                ytbTrailerIV.setTag(YTBTrailer.getUrl(trailer));
                ytbTrailerIV.setOnClickListener(this);
                linearLayout.addView(ytbTrailerIV);
            }


            FloatingActionButton myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            myFab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    markAsFavorite(v);
                }
            });


        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        String ytbUrl = (String) v.getTag();
        Intent ytbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ytbUrl));
        startActivity(ytbIntent);
    }

    private void markAsFavorite(View view) {
        SharedPreferences mPrefs = getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movie);
        prefsEditor.putString(movie.getId(), json);
        prefsEditor.apply();
        Toast.makeText(getActivity().getApplicationContext(), R.string.fav_toast_string, Toast.LENGTH_LONG).show();
    }

    public class CallMovieDBAPI extends AsyncTask<String, Void, Movie> {

        private Activity activity;
        ProgressDialog dialog;
        Movie movie;

        public CallMovieDBAPI(Activity activity, Movie movie) {
            this.activity = activity;
            this.movie = movie;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(activity);
            dialog.setMessage(getString(R.string.load_movie_details_dialog));
            dialog.show();
        }

        @Override
        protected Movie doInBackground(String... params) {
            InputStream in = null;
            String jsonResponse = null;
            String apiURL = params[0];
            try {

                URL url = new URL(apiURL);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                jsonResponse = getJsonObject(in);
                if (apiURL.contains("reviews")) {
                    List<Review> reviewList;
                    reviewList = JsonParser.parseReviews(jsonResponse);
                    movie.setReviewList(reviewList);
                } else {
                    List<YTBTrailer> trailerList;
                    trailerList = JsonParser.parseTrailers(jsonResponse);
                    movie.setTrailerList(trailerList);
                }

            } catch (Exception e) {

                Log.e("CallMovieDBAPI", "Exception occurred in AsyncTask - CallMovieDBAPI", e);
            }

            return movie;


        }

        @Override
        protected void onPostExecute(Movie movie) {
            Log.d("Popular Movies", "Got Review/Trailer data successfully");
            if (dialog.isShowing()) {
                dialog.dismiss();
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
