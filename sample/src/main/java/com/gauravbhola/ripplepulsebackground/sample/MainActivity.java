package com.gauravbhola.ripplepulsebackground.sample;

import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    RipplePulseLayout mRipplePulseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRipplePulseLayout = findViewById(R.id.layout_ripplepulse);
        mRipplePulseLayout.startRippleAnimation();
        mRipplePulseLayout.stopRippleAnimation();
    }

}
