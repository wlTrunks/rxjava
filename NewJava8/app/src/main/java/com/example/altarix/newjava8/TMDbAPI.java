package com.example.altarix.newjava8;


import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Altarix on 29.04.2016.
 */
public interface TMDbAPI {

    @GET("movie/popular/?")
    Observable<List<Movie>> getPopularMovie();

}
