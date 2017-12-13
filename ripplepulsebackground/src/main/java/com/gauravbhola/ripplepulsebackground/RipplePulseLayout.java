package com.gauravbhola.ripplepulsebackground;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.FitWindowsLinearLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by gauravbhola on 10/12/17.
 */

public class RipplePulseLayout extends RelativeLayout {
    public static final int DEFAULT_DURATION = 2000;
    public static final String RIPPLE_TYPE_FILL = "0";
    public static final String RIPPLE_TYPE_STROKE = "1";
    public static final String TAG = "RipplePulseLayout";

    private Paint mPaint;
    private AnimatorSet mAnimatorSet;
    private boolean mIsAnimating;


    public RipplePulseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RipplePulseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public RipplePulseLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * Initializes the required ripple views
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }
        //reading the attributes
        TypedArray attrValues = context.obtainStyledAttributes(attrs, R.styleable.RipplePulseLayout);
        int color = attrValues.getColor(R.styleable.RipplePulseLayout_rippleColor, getResources().getColor(android.R.color.holo_blue_bright));
        float startRadius = attrValues.getDimension(R.styleable.RipplePulseLayout_startRadius, getMeasuredWidth()/2);
        float endRadius = attrValues.getDimension(R.styleable.RipplePulseLayout_endRadius, getMeasuredWidth());
        float strokeWidth = attrValues.getDimension(R.styleable.RipplePulseLayout_strokeWidth, 4);
        int duration = attrValues.getInteger(R.styleable.RipplePulseLayout_duration, DEFAULT_DURATION);
        String rippleType = attrValues.getString(R.styleable.RipplePulseLayout_rippleType);
        if (TextUtils.isEmpty(rippleType)) {
            rippleType = RIPPLE_TYPE_FILL;
        }

        initializePaint(color, rippleType, strokeWidth);

        RippleView rp = new RippleView(getContext(), mPaint, startRadius);
        LayoutParams params = new LayoutParams((int)endRadius, (int)endRadius);
        params.addRule(CENTER_IN_PARENT, TRUE);
        addView(rp, params);

        mAnimatorSet = new AnimatorSet();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rp, "scaleX", 1f, endRadius/startRadius);
        scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rp, "scaleY", 1f, endRadius/startRadius);
        scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rp, "alpha", 1f, 0f);
        alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);

        mAnimatorSet.setDuration(duration);
        mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
    }

    public void startRippleAnimation() {
        if (mIsAnimating) {
            //already animating
            return;
        }
        mAnimatorSet.start();
        mIsAnimating = true;
    }

    public void stopRippleAnimation() {
        if (!mIsAnimating) {
            //already not animating
            return;
        }
        mAnimatorSet.end();
        mIsAnimating = false;
    }

    /**
     * Initializes the paint with the required properties
     * @param color
     */
    private void initializePaint(int color, String rippleType, float strokeWidth) {
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        if (rippleType.equals(RIPPLE_TYPE_STROKE)) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth);
        } else {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(0);
        }
    }

    private static class RippleView extends View {
        private Paint mPaint;
        private float mRadius;

        public RippleView(Context context, Paint p, float radius) {
            super(context);
            mPaint = p;
            mRadius = radius;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int center=(Math.min(getWidth(),getHeight()))/2;
            canvas.drawCircle(center, center, mRadius, mPaint);
        }
    }
}
