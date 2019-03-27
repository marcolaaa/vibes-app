package com.dm.vibesapp.di;

import android.app.Application;

import com.dm.vibesapp.BaseApplication;
import com.dm.vibesapp.data.local.AppDatabase;
import com.dm.vibesapp.data.local.dao.QuoteDao;
import com.dm.vibesapp.data.remote.VibesAPI;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class, ApiModule.class})
public interface AppComponent {

    void inject(BaseApplication baseApplication);

    ActivityComponent newActivitySubcomponent(ActivityModule activityModule);

    QuoteDao quoteDao();

    AppDatabase appDatabase();

    VibesAPI vibesAPI();

    Application application();

}