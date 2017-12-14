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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

public class RipplePulseLayout extends RelativeLayout {
    public static final int DEFAULT_DURATION = 2000;
    public static final String RIPPLE_TYPE_FILL = "0";
    public static final String RIPPLE_TYPE_STROKE = "1";
    public static final String TAG = "RipplePulseLayout";

    private Paint mPaint;
    private AnimatorSet mAnimatorSet;
    private boolean mIsAnimating;
    private RippleView mRippleView;


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
        float startRadius = attrValues.getDimension(R.styleable.RipplePulseLayout_startRadius, getMeasuredWidth());
        float endRadius = attrValues.getDimension(R.styleable.RipplePulseLayout_endRadius, getMeasuredWidth() * 2);
        float strokeWidth = attrValues.getDimension(R.styleable.RipplePulseLayout_strokeWidth, 4);
        int duration = attrValues.getInteger(R.styleable.RipplePulseLayout_duration, DEFAULT_DURATION);
        String rippleType = attrValues.getString(R.styleable.RipplePulseLayout_rippleType);
        if (TextUtils.isEmpty(rippleType)) {
            rippleType = RIPPLE_TYPE_FILL;
        }
        //initialize stuff
        initializePaint(color, rippleType, strokeWidth);
        initializeRippleView(endRadius, startRadius, strokeWidth);
        initializeAnimators(startRadius, endRadius, duration);
    }

    private void initializeRippleView(float endRadius, float startRadius, float strokeWidth) {
        mRippleView = new RippleView(getContext(), mPaint, startRadius);
        LayoutParams params = new LayoutParams(2 * (int)(endRadius + strokeWidth), 2 * (int)(endRadius + strokeWidth));
        params.addRule(CENTER_IN_PARENT, TRUE);
        addView(mRippleView, params);
        mRippleView.setVisibility(INVISIBLE);
    }

    private void initializeAnimators(float startRadius, float endRadius, int duration) {
        mAnimatorSet = new AnimatorSet();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mRippleView, "radius", startRadius, endRadius);
        scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mRippleView, "alpha", 1f, 0f);
        alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);

        mAnimatorSet.setDuration(duration);
        mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorSet.playTogether(scaleXAnimator, alphaAnimator);
    }

    /**
     * Starts the ripple animation
     */
    public void startRippleAnimation() {
        if (mIsAnimating) {
            //already animating
            return;
        }
        mRippleView.setVisibility(View.VISIBLE);
        mAnimatorSet.start();
        mIsAnimating = true;
    }

    /**
     * Stops the ripple animation
     */
    public void stopRippleAnimation() {
        if (!mIsAnimating) {
            //already not animating
            return;
        }
        mAnimatorSet.end();
        mRippleView.setVisibility(View.INVISIBLE);
        mIsAnimating = false;
    }

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

        public float getRadius() {
            return mRadius;
        }

        public void setRadius(float radius) {
            mRadius = radius;
            invalidate();
        }

        public RippleView(Context context, Paint p, float radius) {
            super(context);
            mPaint = p;
            mRadius = radius;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int centerX = getWidth()/2;
            int centerY = getHeight()/2;
            canvas.drawCircle(centerX, centerY, mRadius, mPaint);
        }
    }
}
