package com.example.liaodh.keeprun.view.commod;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class BounceListView extends ListView implements Runnable {
    private float mLastDownY = 0f;
    private int mDistance = 0;
    private int mStep = 10;
    private boolean mPositive = false;
    private boolean mIsBounces = true;

    public BounceListView(Context context) {
        super(context);
    }

    public BounceListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsBounces) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mLastDownY == 0f && mDistance == 0) {
                        mLastDownY = event.getY();
                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (mDistance != 0) {
                        mStep = 1;
                        mPositive = mDistance >= 0;
                        this.post(this);
                        return true;
                    }

                    mLastDownY = 0f;
                    mDistance = 0;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mLastDownY != 0f) {
                        mDistance = (int) (mLastDownY - event.getY());
                        if ((mDistance < 0 && getFirstVisiblePosition() == 0 && getChildAt(
                                0).getTop() == 0)) {
                            mDistance /= 2;
                            scrollTo(0, mDistance);
                            return true;
                        } else if (mDistance > 0
                                && getLastVisiblePosition() == getCount() - 1) {
                            final View bottomChildView = getChildAt(getLastVisiblePosition()
                                    - getFirstVisiblePosition());
                            if (getHeight() >= bottomChildView.getBottom()) {
                                mDistance /= 2;
                                scrollTo(0, mDistance);
                                return true;
                            }
                        }
                    }
                    mDistance = 0;
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void run() {
        mDistance += mDistance > 0 ? -mStep : mStep;
        scrollTo(0, mDistance);
        if ((mPositive && mDistance <= 0) || (!mPositive && mDistance >= 0)) {
            scrollTo(0, 0);
            mDistance = 0;
            mLastDownY = 0f;
            return;
        }
        mStep += 1;
        this.postDelayed(this, 10);
    }

    protected void setBounces(boolean isBounces) {
        this.mIsBounces = isBounces;
    }
}
