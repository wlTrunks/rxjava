package com.example.altarix.newjava8.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.altarix.newjava8.R;
import com.example.altarix.newjava8.model.Movie;

import java.util.List;

/**
 * Created by Altarix on 03.06.2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;

    public MoviesAdapter(List<Movie> movies, Context context) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        String url = movie.getUrl();
        Log.v("THEMOVIEMDB", "url = " + url);
        holder.movieTitle.setText(movie.getTitle());
        Glide.with(mContext).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView movieImage;
        public TextView movieTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
        }

    }
}
