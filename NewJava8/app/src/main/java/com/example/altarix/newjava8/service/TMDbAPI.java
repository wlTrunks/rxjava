package com.example.altarix.newjava8.service;


import com.example.altarix.newjava8.model.MoviesResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Altarix on 29.04.2016.
 */
public interface TMDbAPI {

    @GET("movie/popular/?")
    Observable<MoviesResponse> getPopularMovie(@Query("api_key") String key);

}
