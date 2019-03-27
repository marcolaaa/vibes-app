package com.dm.vibesapp.rx.usecases;

import com.dm.vibesapp.data.remote.VibesAPI;
import com.dm.vibesapp.data.remote.model.Quote;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QuotesUseCase {

    @Inject
    VibesAPI mVibesAPI;

    private String API_KEY = "bb53d772-8bc0-49f6-8158-5ab462d49b1d";

    @Inject
    public QuotesUseCase () {

    }

    public Observable<List<Quote>> getQuoteList() {
        //return mYoutubeAPI.getQuotes(API_KEY).subscribeOn(Schedulers.computation())
                //.observeOn(AndroidSchedulers.mainThread());
        return mVibesAPI.getQuotes(API_KEY).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
