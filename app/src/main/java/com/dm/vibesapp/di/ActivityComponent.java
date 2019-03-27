package com.dm.vibesapp.di;

import com.dm.vibesapp.presentation.quotes.QuotesActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(QuotesActivity quotesActivity);
}
