package com.dm.vibesapp.presentation.quotes;

import android.util.Log;

import com.dm.vibesapp.R;
import com.dm.vibesapp.data.local.AppDatabase;
import com.dm.vibesapp.data.remote.model.Quote;
import com.dm.vibesapp.rx.usecases.QuotesUseCase;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class QuotesPresenter implements QuotesContract.Presenter {

    public static final String TAG = "DATABASE";
    public static final int MAX_QUOTES = 350;

    private QuotesUseCase mUseCase;
    private QuotesContract.View mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private AppDatabase mAppDatabase;

    private List<com.dm.vibesapp.data.local.entity.Quote> mListDeniedQuotes;
    private int mQuotesNumber = 0;
    private int mSwipesNumber = 0;

    public QuotesPresenter (QuotesContract.View view, QuotesUseCase useCase, AppDatabase appDatabase) {
        mView = view;
        mUseCase = useCase;
        mAppDatabase = appDatabase;
    }


    @Override
    public void attachView(QuotesContract.View view) {
        //do nothing
    }

    @Override
    public void onStart() {
        //do nothing
    }

    @Override
    public void onStop() {
        mView = null;
        mUseCase = null;
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    @Override
    public void getQuotes() {
        getAllQuotes();
    }

    private void getAllQuotes() {
        if (mView != null && mAppDatabase != null && mUseCase != null) {
            mView.showLoading();
            //getting the quotes that user does not want to see from the local database.
            mAppDatabase.quoteDao().getAll().delay(3000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<com.dm.vibesapp.data.local.entity.Quote>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(List<com.dm.vibesapp.data.local.entity.Quote> quotes) {
                            mListDeniedQuotes = quotes;
                            //chain of network calls
                            //getting the quotes from the api
                            mDisposable.add(mUseCase.getQuoteList().subscribeWith(new QuotesSubscriber()));
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.hideLoading();
                            Log.e(TAG, e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void onSwipeRight(Quote quote) {
        //verify if all quotes were showed already, if yes, get more quotes from the api.
        mSwipesNumber++;
        if(mSwipesNumber == mQuotesNumber){
            getAllQuotes();
            mSwipesNumber = 0;
        }
    }

    @Override
    public void onSwipeLeft(Quote quote) {
        if (mView != null && mAppDatabase != null) {
            Completable.fromAction(() -> mAppDatabase.quoteDao()
                    .insertAll(new com.dm.vibesapp.data.local.entity.Quote(quote.getId(), quote.getQuote())))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable.add(d);
                        }

                        @Override
                        public void onComplete() {
                            mAppDatabase.quoteDao().getCount().subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<Integer>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            mDisposable.add(d);
                                        }

                                        @Override
                                        public void onSuccess(Integer count) {
                                            if (count >= MAX_QUOTES) {
                                                Completable.fromAction(mAppDatabase.quoteDao()::cleanTable)
                                                        .subscribeOn(Schedulers.single()).subscribe();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            mView.hideLoading();
                                            Log.e(TAG, e.getMessage());
                                        }
                                    });
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, e.getMessage());
                        }
                    });

            //verify if all quotes were showed already, if yes, get more quotes from the api.
            mSwipesNumber++;
            if (mSwipesNumber == mQuotesNumber) {
                getAllQuotes();
                mSwipesNumber = 0;
            }
        }
    }

    private class QuotesSubscriber extends DisposableObserver<List<Quote>> {

        @Override
        public void onNext(List<Quote> quotes) {
            //filter result removing quotes that user does not want to see.
            List<Quote> result = quotes.stream()
                    .filter(item -> !mListDeniedQuotes.contains(item))
                    .collect(Collectors.toList());

            mQuotesNumber = result.size();
            if (mView != null) {
                mView.hideLoading();
                mView.onLoadQuotes(result);
            }
        }

        @Override
        public void onError(Throwable e) {
            if (mView != null) {
                mView.hideLoading();
                mView.showError(R.string.generic_error_message);
            }
        }

        @Override
        public void onComplete() {
            //nothing to do
        }
    }
}
