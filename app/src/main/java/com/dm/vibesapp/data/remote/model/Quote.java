package com.dm.vibesapp.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("_id")
    private String mId;

    @SerializedName("quote")
    private String mQuote;

    @SerializedName("author")
    private String mAuthor;

    @SerializedName("length")
    private int mLength;

    @SerializedName("explicit")
    private boolean mExplicit;

    public Quote(String mId, String mQuote, String mAuthor, int mLength, boolean mExplicit) {
        this.mId = mId;
        this.mQuote = mQuote;
        this.mAuthor = mAuthor;
        this.mLength = mLength;
        this.mExplicit = mExplicit;
    }

    public String getId() {
        return mId;
    }

    public String getQuote() {
        return mQuote;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public int getLength() {
        return mLength;
    }

    public boolean isExplicit() {
        return mExplicit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Quote) {
            return super.equals(obj);
        } else if (obj instanceof com.dm.vibesapp.data.local.entity.Quote) {
            com.dm.vibesapp.data.local.entity.Quote quote = (com.dm.vibesapp.data.local.entity.Quote)obj;
            return quote.getUid().equals(this.getId());
        } else {
            return false;
        }
    }
}
