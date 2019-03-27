package com.dm.vibesapp.presentation.quotes;

import com.dm.vibesapp.data.remote.model.Quote;
import com.dm.vibesapp.presentation.BasePresenter;

import java.util.List;

public interface QuotesContract {

    interface View {
        void onLoadQuotes(List<Quote> quotes);
        void showError(int message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter extends BasePresenter<View> {
        void getQuotes();
        void onSwipeRight(Quote quote);
        void onSwipeLeft(Quote quote);
    }
}
