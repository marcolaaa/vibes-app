package com.dm.vibesapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.dm.vibesapp.data.local.dao.QuoteDao;
import com.dm.vibesapp.data.local.entity.Quote;

@Database(entities = {Quote.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuoteDao quoteDao();
}