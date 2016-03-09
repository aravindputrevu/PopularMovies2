package com.aravind.popularmovies2.util;


import android.util.Log;

import com.aravind.popularmovies2.model.Movie;
import com.aravind.popularmovies2.model.Review;
import com.aravind.popularmovies2.model.YTBTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonParser {

    private static final String ID = "id";
    private static final String RESULTS = "results";
    private static final String TITLE = "title";
    private static final String RELEASE_DATE = "release_date";
    private static final String RATING = "vote_average";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String PLOT = "overview";
    private static final String VOTE_AVG = "vote_average";
    //Constants for parsing YTB trailer
    private static final String NAME = "name";
    private static final String KEY = "key";
    //Constants for parsing Review
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String URL = "url";

    public static ArrayList<Movie> parse(String jsonData) {

        ArrayList<Movie> movieList = new ArrayList<>();

        if (jsonData == null)
            return movieList;

        try {
            JSONArray movieArray = new JSONObject(jsonData).getJSONArray(RESULTS);
            int length = movieArray.length();

            for (int i = 0; i < length; i++) {

                Movie movie = new Movie();
                JSONObject movieVO = movieArray.getJSONObject(i);

                movie.setTitle(movieVO.getString(TITLE));
                movie.setId(movieVO.getString(ID));
                movie.setOverview(movieVO.getString(PLOT));
                movie.setPosterPath(movieVO.getString(POSTER_PATH));
                movie.setBackdropPath(movieVO.getString(BACKDROP_PATH));
                movie.setReleaseDate(movieVO.getString(RELEASE_DATE));
                movie.setPopularity(movieVO.getString(POPULARITY));
                movie.setRating(movieVO.getString(RATING));
                movie.setVoteAvg(movieVO.getString(VOTE_AVG));

                movieList.add(movie);
            }
        } catch (JSONException e) {
            Log.e("JSONParser", "Exception occurred in parsing JSON");
        }

        return movieList;
    }

    public static List<Review> parseReviews(String jsonData) {

        List<Review> reviewList = new LinkedList<Review>();

        if (jsonData == null)
            return reviewList;

        try {
            JSONArray resultArray = new JSONObject(jsonData).getJSONArray(RESULTS);
            int length = resultArray.length();

            for (int i = 0; i < length; i++) {

                Review review = new Review();
                JSONObject reviewVO = resultArray.getJSONObject(i);

                review.setId(reviewVO.getString(ID));
                review.setAuthor(reviewVO.getString(AUTHOR));
                review.setContent(reviewVO.getString(CONTENT));
                review.setUrl(reviewVO.getString(URL));

                reviewList.add(review);

            }
        } catch (Exception e) {
            Log.e("JSONParser", "Exception occurred in parsing parseTrailerAndReviews JSON");
        }

        return reviewList;
    }

    public static List<YTBTrailer> parseTrailers(String jsonData) {

        List<YTBTrailer> trailerList = new LinkedList<YTBTrailer>();

        if (jsonData == null)
            return trailerList;

        try {
            JSONArray resultArray = new JSONObject(jsonData).getJSONArray(RESULTS);
            int length = resultArray.length();

            for (int i = 0; i < length; i++) {
                YTBTrailer trailer = new YTBTrailer();
                JSONObject trailerVO = resultArray.getJSONObject(i);

                trailer.setId(trailerVO.getString(ID));
                trailer.setName(trailerVO.getString(NAME));
                trailer.setKey(trailerVO.getString(KEY));

                trailerList.add(trailer);
            }
        } catch (Exception e) {
            Log.e("JSONParser", "Exception occurred in parsing parseTrailerAndReviews JSON");
        }

        return trailerList;
    }
}
