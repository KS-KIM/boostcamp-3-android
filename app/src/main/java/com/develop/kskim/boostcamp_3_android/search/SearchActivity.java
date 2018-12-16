package com.develop.kskim.boostcamp_3_android.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.develop.kskim.boostcamp_3_android.R;
import com.develop.kskim.boostcamp_3_android.util.ActivityUtils;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getName();

    private SearchPresenter mSearchPresenter;

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        mToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(R.string.app_name);
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));

        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        searchFragment = searchFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), searchFragment, R.id.contentFrame);

        String baseUrl = getResources().getString(R.string.baseUrl);
        mSearchPresenter = new SearchPresenter(searchFragment, baseUrl);
    }
}
