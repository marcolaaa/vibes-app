package com.dm.vibesapp.di;

import com.dm.vibesapp.data.local.AppDatabase;
import com.dm.vibesapp.presentation.quotes.QuotesContract;
import com.dm.vibesapp.presentation.quotes.QuotesPresenter;
import com.dm.vibesapp.rx.usecases.QuotesUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final QuotesContract.View mQuotesView;

    public ActivityModule(QuotesContract.View view) {
        this.mQuotesView = view;
    }

    @Provides
    @ActivityScope
    QuotesContract.Presenter provideQuotesPresenter(QuotesUseCase useCase, AppDatabase appDatabase) {
        return new QuotesPresenter(mQuotesView, useCase, appDatabase);
    }
}
