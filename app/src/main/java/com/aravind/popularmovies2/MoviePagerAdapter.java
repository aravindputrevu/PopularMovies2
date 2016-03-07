package com.aravind.popularmovies2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MoviePagerAdapter extends FragmentStatePagerAdapter{

    int mNumOfTabs;
    boolean isTablet;

    public MoviePagerAdapter(FragmentManager fm, int NumOfTabs, boolean isTablet) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.isTablet = isTablet;
    }



    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isTablet", isTablet);
        switch (position) {
            case 0:
                PopularMoviesFragment tab1 = new PopularMoviesFragment();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                RatingMovieFragment tab2 = new RatingMovieFragment();
                tab2.setArguments(bundle);
                return tab2;
            case 2:
                FavoriteMovieFragment tab3 = new FavoriteMovieFragment();
                tab3.setArguments(bundle);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}

