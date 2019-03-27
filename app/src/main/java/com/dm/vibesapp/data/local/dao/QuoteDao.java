package com.dm.vibesapp.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.dm.vibesapp.data.local.entity.Quote;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface QuoteDao {

    @Query("SELECT * FROM quote")
    Single<List<Quote>> getAll();

    @Query("SELECT * FROM quote WHERE uid = :id")
    Single<Quote> getById(int id);

    @Query("SELECT * FROM quote WHERE uid IN (:userIds)")
    List<Quote> loadAllByIds(int[] userIds);

    @Query("SELECT COUNT(uid) FROM quote")
    Single<Integer> getCount();

    @Insert
    void insertAll(Quote... quotes);

    @Delete
    void delete(Quote quote);

    @Query("DELETE FROM quote")
    void cleanTable();
}
