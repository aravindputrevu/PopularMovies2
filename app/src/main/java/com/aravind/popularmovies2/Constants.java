package com.aravind.popularmovies2;


public class Constants {


    public static final String FETCH_MOVIE_BASE_URL = "https://api.themoviedb.org/3/discover/movie";

    public static final String SORT_PARAM = "sort_by";

    public static final String API_KEY_PARAM = "api_key";

    public static final String PAGE_PARAM = "page";

    public static final String SORT_BY_POPULARITY = "popularity.desc";

    public static final String SORT_BY_RATING = "vote_average.desc";

    public static final String MOVIE_POSTER_PATH_SMALL = "https://image.tmdb.org/t/p/w185/";

    public static final String MOVIE_POSTER_PATH_BIG = "https://image.tmdb.org/t/p/w342/";

    public static final String FETCH_MOVIE_TRAILERS = "https://api.themoviedb.org/3/movie/%s/videos";

    public static final String FETCH_MOVIE_REVIEWS = "https://api.themoviedb.org/3/movie/%s/reviews";

    public static final String SHARED_PREF_KEY = "myprefs";

}
