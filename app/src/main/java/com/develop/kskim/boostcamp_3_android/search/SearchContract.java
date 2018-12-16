package com.develop.kskim.boostcamp_3_android.search;

import com.develop.kskim.boostcamp_3_android.base.BasePresenter;
import com.develop.kskim.boostcamp_3_android.base.BaseView;
import com.develop.kskim.boostcamp_3_android.repository.Item;

import java.util.ArrayList;

public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void showEmptyField();
        void showNotFindItem();
        void showMoviePage(int position);
        void showMoreMovies(ArrayList<Item> items);
        void showMovies(ArrayList<Item> items);

    }

    interface Presenter extends BasePresenter {

        void startSearch(String title);
        void getMovies(String title, int startPosition);

    }

}
