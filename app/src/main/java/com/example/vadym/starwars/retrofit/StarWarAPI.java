package com.example.vadym.starwars.retrofit;

import com.example.vadym.starwars.model.StarWarResponce;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vadym on 14.03.2018.
 */

public interface StarWarAPI {

    @GET("people/?")
    Flowable<StarWarResponce> getResponce(@Query("page") int page);
}
