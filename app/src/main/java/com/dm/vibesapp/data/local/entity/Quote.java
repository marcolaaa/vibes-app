package com.dm.vibesapp.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Quote {

    @PrimaryKey
    @NonNull
    public String uid;

    @ColumnInfo(name = "quote")
    public String mQuote;

    public Quote(String uid, String mQuote) {
        this.uid = uid;
        this.mQuote = mQuote;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQuote() {
        return mQuote;
    }

    public void setQuote(String mQuote) {
        this.mQuote = mQuote;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Quote) {
            return super.equals(obj);
        } else if (obj instanceof com.dm.vibesapp.data.remote.model.Quote) {
            com.dm.vibesapp.data.remote.model.Quote quote = (com.dm.vibesapp.data.remote.model.Quote)obj;
            return quote.getId().equals(this.getUid());
        } else {
            return false;
        }
    }
}
