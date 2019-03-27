package com.dm.vibesapp.presentation.quotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;

import com.dm.vibesapp.BaseApplication;
import com.dm.vibesapp.R;
import com.dm.vibesapp.data.remote.model.Quote;
import com.dm.vibesapp.di.ActivityModule;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class QuotesActivity extends AppCompatActivity implements QuotesContract.View, QuotesCardCallback {

    @BindView(R.id.swipe_view)
    SwipePlaceHolderView mSwipeView;

    @BindView(R.id.quotes_progressbar)
    ProgressBar mProgressBar;

    private Unbinder mUnbinder;

    @Inject
    QuotesContract.Presenter mPresenter;

    public static Intent createInstance(Context applicationContext) {
        return new Intent(applicationContext, QuotesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);

        injectDependencies();

        if (mUnbinder != null) {
            mSwipeView.getBuilder()
                    .setDisplayViewCount(3)
                    .setSwipeDecor(new SwipeDecor()
                            .setPaddingTop(20)
                            .setRelativeScale(0.01f)
                            .setSwipeOutMsgGravity(Gravity.RIGHT)
                            .setSwipeInMsgGravity(Gravity.LEFT)
                            .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                            .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        }

        if (mPresenter != null) {
            mPresenter.getQuotes();
        }
    }

    private void injectDependencies() {
        ((BaseApplication) getApplication()).getAppComponent()
                .newActivitySubcomponent(new ActivityModule(this))
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onStop();
            mPresenter = null;
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        super.onDestroy();
    }


    @Override
    public void onSwipeIn(Quote quote) {
        if (mUnbinder != null) {
            mPresenter.onSwipeRight(quote);
        }
    }

    @Override
    public void onSwipeOut(Quote quote) {
        if (mUnbinder != null) {
            mPresenter.onSwipeLeft(quote);
        }
    }

    @Override
    public void onLoadQuotes(List<Quote> quotes) {
        if (mUnbinder != null) {
            for (Quote quote : quotes) {
                mSwipeView.addView(new QuoteCard(this, mSwipeView, quote));
            }
        }
    }

    @Override
    public void showError(int message) {
        if (mUnbinder != null) {
            Snackbar.make(mSwipeView, message, 6000).show();
        }
    }

    @Override
    public void showLoading() {
        if (mUnbinder != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (mUnbinder != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
