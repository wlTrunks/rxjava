package com.example.altarix.newjava8.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.altarix.newjava8.R;
import com.example.altarix.newjava8.adapters.MoviesAdapter;
import com.example.altarix.newjava8.model.Movie;
import com.example.altarix.newjava8.model.MoviesResponse;
import com.example.altarix.newjava8.presenters.MoviePresenter;
import com.example.altarix.newjava8.service.TheMDBService;

import java.util.List;

import rx.Observable;

/**
 * Created by Altarix on 03.06.2016.
 */
public class MostPopularMovieFragment extends Fragment {

    public Observable<MoviesResponse> observable;
    public RecyclerView mMoviesList;
    public  MoviesAdapter adapter;

    public MoviePresenter presenter;
    public TheMDBService theMDBService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.most_popular_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mMoviesList = (RecyclerView) view.findViewById(R.id.moviesList);
        theMDBService = new TheMDBService();
        presenter = new MoviePresenter(this ,theMDBService);
        presenter.loadMovie();
    }

    public void postMovies(List<Movie> movies) {
        adapter = new MoviesAdapter(movies, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mMoviesList.setLayoutManager(mLayoutManager);
        mMoviesList.setItemAnimator(new DefaultItemAnimator());
        mMoviesList.setAdapter(adapter);
    }
}
