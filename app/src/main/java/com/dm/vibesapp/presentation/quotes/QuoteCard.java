package com.dm.vibesapp.presentation.quotes;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.vibesapp.R;
import com.dm.vibesapp.data.remote.model.Quote;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;

import java.util.Random;

@Layout(R.layout.card_view)
public class QuoteCard {

    public static String TAG ="SwipeCardView";
    private QuotesCardCallback mQuotesCardCallback;
    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3,
            R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7,
            R.drawable.image8, R.drawable.image9, R.drawable.image10};

    @View(R.id.imageView)
    private ImageView mImageView;

    @View(R.id.quoteText)
    private TextView mQuoteText;

    private Quote mQuote;
    private Context mContext;
    private SwipePlaceHolderView mSwipePlaceHolderView;

    public QuoteCard(Context context, SwipePlaceHolderView swipePlaceHolderView, Quote movie) {
        this.mContext = context;
        this.mQuotesCardCallback = (QuotesCardCallback) context;
        this.mSwipePlaceHolderView = swipePlaceHolderView;
        this.mQuote = movie;
    }

    @Resolve
    private void onResolve(){
        Random rand = new Random();
        mImageView.setImageResource(images[rand.nextInt(10)]);
        if(mQuote.getAuthor() != null && !mQuote.getAuthor().isEmpty()) {
            mQuoteText.setText(mQuote.getQuote() + " - " + mQuote.getAuthor());
        } else {
            mQuoteText.setText(mQuote.getQuote());
        }
    }

    @SwipeIn
    private void onSwipeIn(){
        mQuotesCardCallback.onSwipeIn(mQuote);
    }

    @SwipeOut
    private void onSwipeOut(){
        mQuotesCardCallback.onSwipeOut(mQuote);
    }
}
