package com.dm.vibesapp.data.remote;

import com.dm.vibesapp.data.remote.model.Quote;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VibesAPI {

    String VIBES_BASE_URL = "https://vibes.dgrzyb.me/";
    //String VIBES_BASE_URL = "http://www.mocky.io/";


    @GET("api/v1/quotes?client_id=?")
    Observable<List<Quote>> getQuotes(@Query("client_id") String apiKey);
    //@GET("v2/5c708427380000333e3fcc78")
    //Observable<List<Quote>> getQuotes();
}
