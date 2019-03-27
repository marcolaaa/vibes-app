package com.dm.vibesapp.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.dm.vibesapp.data.local.AppDatabase;
import com.dm.vibesapp.data.local.dao.QuoteDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private AppDatabase mAppDatabase;

    public RoomModule(Application application) {
        mAppDatabase = Room.databaseBuilder(application, AppDatabase.class, "quote-database.db")
                .build();
    }

    @Singleton
    @Provides
    AppDatabase providesAppDatabase() {
        return mAppDatabase;
    }

    @Singleton
    @Provides
    QuoteDao providesQuoteDao(AppDatabase appDatabase) {
        return appDatabase.quoteDao();
    }
}
