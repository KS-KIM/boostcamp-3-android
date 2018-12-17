package com.develop.kskim.boostcamp_3_android.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.develop.kskim.boostcamp_3_android.MyApplication;
import com.develop.kskim.boostcamp_3_android.R;
import com.develop.kskim.boostcamp_3_android.repository.Item;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = MovieAdapter.class.getName();

    private ArrayList<Item> mMovieInfoArrayList;

    public MovieAdapter(ArrayList<Item> movieInfoArrayList) {
        mMovieInfoArrayList = movieInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.movie_recycler_view_row, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;

        Item item = mMovieInfoArrayList.get(position);
        // movieViewHolder.mIvPoster.setImageResource(item.getImage());
        movieViewHolder.mTvTitle.setText(item.getTitle());
        movieViewHolder.mRbUserRating.setRating(Float.parseFloat(item.getUserRating()) / 2);
        movieViewHolder.mTvPubData.setText(item.getPubDate());
        movieViewHolder.mTvDirector.setText(item.getDirector());
        movieViewHolder.mTvActor.setText(item.getActor());

        try {
            Glide.with(MyApplication.getContext())
                    .load(item.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(movieViewHolder.getImage());
        } catch (NullPointerException e) {
            Log.d(TAG, "Not Found Image Url");
        }

    }

    @Override
    public int getItemCount() {
        return mMovieInfoArrayList.size();
    }

    public Item getItem(int position) {
        return mMovieInfoArrayList.get(position);
    }

    public void addItems(ArrayList<Item> items) {
        for (Item item: items) {
            item.setTitle(item.getTitle().replace("<b>", "").replace("</b>", ""));
            mMovieInfoArrayList.add(item);
        }
        notifyDataSetChanged();
    }

    public void clearItems() {
        mMovieInfoArrayList.clear();
        notifyDataSetChanged();
    }

    public void clearAndAddItems(ArrayList<Item> items) {
        mMovieInfoArrayList.clear();
        addItems(items);
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvPoster;
        private TextView mTvTitle;
        private RatingBar mRbUserRating;
        private TextView mTvPubData;
        private TextView mTvDirector;
        private TextView mTvActor;

        MovieViewHolder(View view) {
            super(view);

            mIvPoster = view.findViewById(R.id.iv_poster);
            mTvTitle = view.findViewById(R.id.tv_title);
            mRbUserRating = view.findViewById(R.id.rb_user_rating);
            mTvPubData = view.findViewById(R.id.tv_pub_data);
            mTvDirector = view.findViewById(R.id.tv_director);
            mTvActor = view.findViewById(R.id.tv_actor);
        }

        public ImageView getImage() {
            return mIvPoster;
        }
    }
}
