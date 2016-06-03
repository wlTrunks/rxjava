package com.example.altarix.newjava8.presenters;

import android.util.Log;

import com.example.altarix.newjava8.MainActivity;
import com.example.altarix.newjava8.model.MoviesResponse;
import com.example.altarix.newjava8.service.TheMDBService;
import com.example.altarix.newjava8.view.MostPopularMovieFragment;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Altarix on 03.06.2016.
 */
public class MoviePresenter {
    private String API_KEY = "75c20af7fe73d6d00317bb1b3295f961";
    MostPopularMovieFragment mView;
    TheMDBService theMDBService;

    public MoviePresenter(MostPopularMovieFragment view, TheMDBService mdb) {
        mView = view;
        theMDBService = mdb;
    }

    public void loadMovie() {
        theMDBService.getApi()
                .getPopularMovie(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MoviesResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.v("THEMOVIEMDB", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("THEMOVIEMDB", "onError = " + e.getMessage());
                    }

                    @Override
                    public void onNext(MoviesResponse movies) {
                        mView.postMovies(movies.getResults());
                    }
                });
    }
}
