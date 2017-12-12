package com.gauravbhola.ripplepulsebackground;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by gauravbhola on 10/12/17.
 */

public class RipplePulseLayout extends RelativeLayout {
    public static final String TAG = "RipplePulseLayout";
    private Paint mPaint;

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
        TypedArray attrValues = context.obtainStyledAttributes(attrs, R.styleable.RipplePulseLayout);
        int color = attrValues.getColor(R.styleable.RipplePulseLayout_rippleColor, getResources().getColor(android.R.color.black));
        initializePaint(color);
        Log.d(TAG, "init");

        RippleView rp = new RippleView(getContext(), mPaint);
        LayoutParams params = new LayoutParams(300, 300);
        params.addRule(CENTER_IN_PARENT, TRUE);
        addView(rp, params);
    }

    /**
     * Initializes the paint with the required properties
     * @param color
     */
    private void initializePaint(int color) {
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
    }

    private static class RippleView extends View {
        private Paint mPaint;

        public RippleView(Context context, Paint p) {
            super(context);
            mPaint = p;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int radius=(Math.min(getWidth(),getHeight()))/2;
            canvas.drawCircle(radius,
                    radius,
                    200,
                    mPaint);
        }
    }
}
