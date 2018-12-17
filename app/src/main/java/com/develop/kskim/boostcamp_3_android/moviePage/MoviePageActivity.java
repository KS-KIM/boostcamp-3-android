package com.develop.kskim.boostcamp_3_android.moviePage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebBackForwardList;
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
        mCurrentUrl = intent.getStringExtra(MOVIE_URL);


        mToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(mCurrentUrl);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWvMovie = findViewById(R.id.wv_movie);
        mWvMovie.getSettings().setJavaScriptEnabled(true);
        mWvMovie.getSettings().setDomStorageEnabled(true);
        mWvMovie.setWebViewClient(new WebViewClientClass());
        mWvMovie.loadUrl(mCurrentUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.webpage_menu, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(shareTextUrl(mCurrentUrl));
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
                mWvMovie.loadUrl(mCurrentUrl);
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

    public Intent shareTextUrl(String url) {
        Intent share = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle("Share URL")
                .setText(url)
                .getIntent();
        return share;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWvMovie.canGoBack()) {
            WebBackForwardList webBackForwardList = mWvMovie.copyBackForwardList();
            if (webBackForwardList.getSize() == 2) {
                finish();
            }
            mWvMovie.goBack();
            setUrl(webBackForwardList.getItemAtIndex(webBackForwardList.getCurrentIndex() - 1).getUrl());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            setUrl(url);
            return false;
        }
    }

    private void setUrl(String url) {
        mCurrentUrl = url;
        mShareActionProvider.setShareIntent(shareTextUrl(url));
        getSupportActionBar().setTitle(url);
    }

}
