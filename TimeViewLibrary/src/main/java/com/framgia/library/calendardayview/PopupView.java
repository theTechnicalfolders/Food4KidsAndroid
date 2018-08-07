package com.framgia.library.calendardayview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.library.calendardayview.data.IEvent;
import com.framgia.library.calendardayview.data.IPopup;

/**
 * Created by FRAMGIA\pham.van.khac on 08/07/2016.
 */
public class PopupView extends FrameLayout {

    protected IPopup mPopup;
    protected OnEventPopupClickListener mPopupClickListener;
    protected int mShowDuration = 3000;
    protected TextView msg;
    protected CardView mCardView;

    protected AlphaAnimation mFadeOutAnim = new AlphaAnimation(1.0f, 0.0f);
    protected Animation.AnimationListener mFadeOutListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            setAlpha(1);
            setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private Runnable mHidePopup = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    public PopupView(Context context) {
        super(context);
        init(null);
    }

    public PopupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PopupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_event_popup1, this, true);

        msg = (TextView) findViewById(R.id.popup_msg);
        mCardView = (CardView) findViewById(R.id.cardview);

        msg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupClickListener != null) {
                    mPopupClickListener.onQuoteClick(PopupView.this, mPopup);

                }
            }
        });

        mCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupClickListener != null) {
                    mPopupClickListener.onPopupClick(PopupView.this, mPopup);
                  //  Toast.makeText(getContext(), "show popup", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }

    public void show() {
        setVisibility(View.VISIBLE);
        if (this.mPopup.isAutohide()) {
            removeCallbacks(mHidePopup);
            postDelayed(mHidePopup, mShowDuration);
        }
    }

    public void hide() {
        if (mFadeOutAnim.hasStarted() && !mFadeOutAnim.hasEnded()) {
            return;
        }
        mFadeOutAnim = new AlphaAnimation(getAlpha(), 0.0f);
        setVisibility(View.VISIBLE);
        mFadeOutAnim.setAnimationListener(mFadeOutListener);
        mFadeOutAnim.setDuration(200);
        startAnimation(mFadeOutAnim);
    }

    public void setPosition(Rect rect, int topMargin, int bottomMargin) {
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = rect.top + topMargin;
        params.leftMargin = rect.left;
        mCardView.getLayoutParams().height = rect.height() + bottomMargin;
        setLayoutParams(params);
    }

    public void setPopup(IPopup popup) {
        this.mPopup = popup;
        msg.setText(String.valueOf(popup.getDescription()));

        if(mPopupClickListener != null){
          //  mPopupClickListener.onLoadData(PopupView.this, mImvStart,mImvEnd, popup);
        }
        msg.setText(String.valueOf(popup.getMsg()));
    }

    @Override
    public void setOnClickListener(final OnClickListener l) {


        throw new UnsupportedOperationException("you should use setOnEventClickListener()");

         }

    public void setOnPopupClickListener(OnEventPopupClickListener listener) {
        this.mPopupClickListener = listener;
    }

    public interface OnEventPopupClickListener {
        void onPopupClick(PopupView view, IPopup data);

        void onQuoteClick(PopupView view, IPopup data);

        void onLoadData(PopupView view, ImageView start, ImageView end, IPopup data);
    }
}
