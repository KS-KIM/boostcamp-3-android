package com.develop.kskim.boostcamp_3_android.search;

import android.support.annotation.NonNull;
import android.util.Log;

import com.develop.kskim.boostcamp_3_android.apiInterface.MovieApiInterface;
import com.develop.kskim.boostcamp_3_android.repository.MovieInfo;
import com.develop.kskim.boostcamp_3_android.util.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.develop.kskim.boostcamp_3_android.util.Constants.MOVIE_DISPLAY_SIZE;

public class SearchPresenter implements SearchContract.Presenter {

    private static final String TAG = SearchPresenter.class.getName();

    private SearchContract.View mSearchView;

    private Retrofit mRetrofit;
    private MovieApiInterface mMovieApiInterface;
    private Call<MovieInfo> mCallMovieList;
    private int mPageNo;

    public SearchPresenter(@NonNull SearchContract.View searchView, String baseUrl) {
        mRetrofit = RetrofitClient.getClient(baseUrl);
        mMovieApiInterface = mRetrofit.create(MovieApiInterface.class);

        mSearchView = searchView;
        mSearchView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void startSearch(String title) {
        if (title.isEmpty()) {
            mSearchView.showEmptyField();
        } else {
            mPageNo = 1;
            getMovies(title, 1);
        }
    }

    @Override
    public void getMovies(String title, int startPosition) {
        if ((mPageNo != -1 || startPosition == 1) && startPosition < 1001) {
            mPageNo = startPosition;
            mCallMovieList = mMovieApiInterface.getMovieList(title, MOVIE_DISPLAY_SIZE, startPosition);
            mCallMovieList.enqueue(mRetrofitCallback);
        }
    }

    private Callback<MovieInfo> mRetrofitCallback = new Callback<MovieInfo>() {

        @Override
        public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
            MovieInfo result = response.body();
            if (result.getItems() == null) {
                mPageNo = -1;
                return;
            }
            if (result.getItems().size() == 0) {
                mSearchView.showNotFindItem();
            } else if (mPageNo <= MOVIE_DISPLAY_SIZE) {
                mSearchView.showNewMovies(new ArrayList<>(result.getItems()));
            } else {
                mSearchView.showMoreMovies(new ArrayList<>(result.getItems()));
            }
            if (result.getItems().size() < MOVIE_DISPLAY_SIZE) {
                mPageNo = -1;
            }
        }

        @Override
        public void onFailure(Call<MovieInfo> call, Throwable t) {
            t.printStackTrace();
        }

    };

}