package com.develop.kskim.boostcamp_3_android.moviePage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.develop.kskim.boostcamp_3_android.R;

import static com.develop.kskim.boostcamp_3_android.util.Constants.MOVIE_URL;

public class MoviePageActivity extends AppCompatActivity {

    private WebView mWvMovie;

    private String mCurrentUrl;

    private Toolbar mToolBar;
    private ShareActionProvider mShareActionProvider;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_site_activity);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MOVIE_URL);

        mToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWvMovie = findViewById(R.id.wv_movie);
        mWvMovie.getSettings().setJavaScriptEnabled(true);
        mWvMovie.getSettings().setDomStorageEnabled(true);
        mWvMovie.setWebViewClient(new WebViewClientClass());
        mWvMovie.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.webpage_menu, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(shareTextUrl());
        return true;
    }

    // @TODO Implements action buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_refresh:
                return true;
            case R.id.action_addBookmark:
                return true;
            case R.id.action_addHome:
                return true;
            case R.id.action_open_browser:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public Intent shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, mCurrentUrl);
        return share;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWvMovie.canGoBack()) {
            mWvMovie.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (mCurrentUrl != null && url != null && url.equals(mCurrentUrl)) {
                finish();
            }
            view.loadUrl(url);
            mCurrentUrl = url;
            getSupportActionBar().setTitle(url);
            return true;
        }
    }

}
