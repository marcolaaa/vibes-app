package com.dm.vibesapp.presentation;

public interface BasePresenter <View> {
    void attachView(View view);
    void onStart();
    void onStop();
}
