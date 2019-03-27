package com.dm.vibesapp.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {

    private ApiBuilder() {
    }

    public static VibesAPI buildYoutubeAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(VibesAPI.VIBES_BASE_URL)
                .build();

        return retrofit.create(VibesAPI.class);
    }
}
