package com.dm.vibesapp.presentation.quotes;

import com.dm.vibesapp.data.remote.model.Quote;

public interface QuotesCardCallback {
    void onSwipeIn(Quote quote);
    void onSwipeOut(Quote quote);
}
