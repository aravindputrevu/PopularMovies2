package com.aravind.popularmovies2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;


public class MainActivity extends AppCompatActivity{


    boolean mTabletMode = false;
    private static final String MOVIE_DETAIL_FRAG_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(findViewById(R.id.movie_detail_container) != null){
            mTabletMode=true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(getString(R.string.toolBarTitle));
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.mostPopular));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.topRated));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.myFav));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MoviePagerAdapter adapter = new MoviePagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),mTabletMode);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(mTabletMode) {
            if (savedInstanceState == null) {
                MovieDetailsFragment mf = new MovieDetailsFragment();
                GridView gridView = (GridView) findViewById(R.id.movie_grid);
                MovieAdapter movieAdapter = (MovieAdapter) gridView.getAdapter();
                Movie m = movieAdapter.getItem(0);

                Bundle bundle = new Bundle();
                bundle.putParcelable("movie_object", m);
                mf.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_container, mf, MOVIE_DETAIL_FRAG_TAG).commit();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }


}
