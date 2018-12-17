package com.develop.kskim.boostcamp_3_android.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.develop.kskim.boostcamp_3_android.R;
import com.develop.kskim.boostcamp_3_android.adapter.MovieAdapter;
import com.develop.kskim.boostcamp_3_android.listener.EndlessRecyclerViewScrollListener;
import com.develop.kskim.boostcamp_3_android.listener.RecyclerItemClickListener;
import com.develop.kskim.boostcamp_3_android.moviePage.MoviePageActivity;
import com.develop.kskim.boostcamp_3_android.repository.Item;

import java.util.ArrayList;

import static com.develop.kskim.boostcamp_3_android.util.Constants.MOVIE_URL;

public class SearchFragment extends Fragment implements SearchContract.View, View.OnClickListener {

    private static final String TAG = SearchFragment.class.getName();

    private SearchPresenter mPresenter;

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private EditText mEtKeyword;

    private Button mBtnSearch;

    private InputMethodManager mInputMethodManager;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.search_fragment, container,
                false);

        mRecyclerView = root.findViewById(R.id.result_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                showMoviePage(position);
            }
        }));

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d(TAG, "pageno: " + Integer.toString(page * 100 + 1));
                mPresenter.getMovies(mEtKeyword.getText().toString(), page * 100 + 1);
            }
        });
        ArrayList<Item> movieInfoArrayList = new ArrayList<>();

        mMovieAdapter = new MovieAdapter(movieInfoArrayList);
        mRecyclerView.setAdapter(mMovieAdapter);

        mEtKeyword = root.findViewById(R.id.et_keyword);
        mBtnSearch = root.findViewById(R.id.btn_search);

        mBtnSearch.setOnClickListener(this);

        mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull SearchContract.Presenter presenter) {
        mPresenter = (SearchPresenter) presenter;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_search:
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                mPresenter.startSearch(mEtKeyword.getText().toString());
                break;
        }
    }

    @Override
    public void showEmptyField() {
        Toast.makeText(getContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotFindItem() {
        mMovieAdapter.clearItems();
        Toast.makeText(getContext(), "\'" + mEtKeyword.getText().toString()
                + "\' 검색결과는 없습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMoviePage(int position) {
        String url = mMovieAdapter.getItem(position).getLink();
        Intent intent = new Intent(getContext(), MoviePageActivity.class);
        intent.putExtra(MOVIE_URL, url);
        startActivity(intent);
    }

    @Override
    public void showMoreMovies(ArrayList<Item> items) {
        mMovieAdapter.addItems(items);
    }

    @Override
    public void showNewMovies(ArrayList<Item> items) {
        mLayoutManager.scrollToPosition(0);
        mMovieAdapter.clearAndAddItems(items);
    }
}