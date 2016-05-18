package com.example.altarix.newjava8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


// Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        TMDbAPI service = retrofit.create(TMDbAPI.class);

        Observable<MoviesResponse> observable = service.getPopularMovie(getString(R.string.tmdb_key));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<MoviesResponse>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getApplicationContext(),
                                "Completed",
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("TMDB", " movie = " + e.getMessage());
                        Toast.makeText(getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(MoviesResponse movies) {

                        Log.v("TMDB", " movie = " + movies.getTotalResults());
                    }
                });
    }

    private void loop() {
        Subscription subscription;
        Observable<Long> values;
        values = Observable.interval(1000, TimeUnit.MILLISECONDS);
        subscription = values.subscribe(
                v -> Log.v("RxANDROID", "Received: " + v),
                e -> Log.v("RxANDROID", "Error: " + e),
                () -> Log.v("RxANDROID", "Completed")
        );
    }

    private void subscribes2() {
        Subject<Integer, Integer> values = ReplaySubject.create();
        Subscription subscription1 = values.subscribe(
                v -> System.out.println("First: " + v)
        );
        Subscription subscription2 = values.subscribe(
                v -> System.out.println("Second: " + v)
        );
        values.onNext(0);
        values.onNext(1);
        subscription1.unsubscribe();
        System.out.println("Unsubscribed first");
        values.onNext(2);
        values.onNext(3);
        values.onNext(4);
    }


    private void asyncSubject() {
        AsyncSubject<Integer> s = AsyncSubject.create();
        s.subscribe(v -> Log.v("RxANDROID", "AsyncSubject: " + v));
        s.onNext(0);
        s.onNext(1);
        s.onNext(2);
        s.onCompleted();
    }

    private void behaviorSubject() {
        BehaviorSubject<Integer> s = BehaviorSubject.create(0);
        s.subscribe(v -> Log.v("RxANDROID", "Early: " + v));
        s.onNext(1);
        s.onNext(2);
        s.onNext(3);
        s.subscribe(v -> Log.v("RxANDROID", "Late: " + v));
        s.onNext(4);
        s.onNext(5);
    }

    private void replaySubjectCreateWithTime() {
        ReplaySubject<Integer> s = ReplaySubject.createWithTime(1000, TimeUnit.MILLISECONDS, Schedulers.immediate());
        s.onNext(0);
        try {
            Thread.sleep(100);
            s.onNext(1);
            Thread.sleep(100);
            s.onNext(2);
            s.subscribe(v -> Log.v("RxANDROID", "Late: " + v));
            s.onNext(3);
        } catch (InterruptedException e) {
        }
    }

    private void replaySubjectCreateWithSize() {
        ReplaySubject<Integer> s = ReplaySubject.createWithSize(2);
        s.onNext(0);
        s.onNext(1);
        s.onNext(2);
        s.subscribe(v -> Log.v("RxANDROID", "Late: " + v));
        s.onNext(3);
    }

    private void replaySubject() {
        ReplaySubject<Integer> s = ReplaySubject.create();
        s.subscribe(v -> Log.v("RxANDROID", "Early: " + v));
        s.onNext(0);
        s.onNext(1);
        s.subscribe(v -> Log.v("RxANDROID", "Late: " + v));
        s.onNext(2);
    }

    private void publishSubject() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.onNext(1);
        subject.subscribe(System.out::println);
        subject.onNext(2);
        subject.onNext(3);
        subject.onNext(4);
    }

    public void unsubs(View view) {
    }

    public void subs(View view) {
    }
}
