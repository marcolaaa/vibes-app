package com.dm.vibesapp;

import android.app.Application;

import com.dm.vibesapp.di.AppComponent;
import com.dm.vibesapp.di.AppModule;
import com.dm.vibesapp.di.DaggerAppComponent;
import com.dm.vibesapp.di.RoomModule;

public class BaseApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .roomModule(new RoomModule(this))
                .appModule(new AppModule(this))
                .build();

        mAppComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
