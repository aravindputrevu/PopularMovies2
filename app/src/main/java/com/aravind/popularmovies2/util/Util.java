package com.aravind.popularmovies2.util;


import android.net.Uri;

import com.aravind.popularmovies2.BuildConfig;
import com.aravind.popularmovies2.Constants;

public class Util {

    public static String constructAPIURL(String sortBy, String page) {
        return Uri.parse(Constants.FETCH_MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(Constants.SORT_PARAM, sortBy)
                .appendQueryParameter(Constants.PAGE_PARAM, page)
                .appendQueryParameter(Constants.API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build().toString();
    }

    public static String constructAPIURL(String url) {
        return Uri.parse(url).buildUpon()
                .appendQueryParameter(Constants.API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build().toString();
    }
}
