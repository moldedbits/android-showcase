package com.moldedbits.showcase.splash;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

/**
 * Created by abhishek
 * on 08/09/17.
 */

public class FlippingSwatchView extends View {
    public FlippingSwatchView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setVisibility(GONE);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setVisibility(VISIBLE);
                YoYo.with(Techniques.FlipInX)
                        .duration(500)
                        .delay(new Random(System.currentTimeMillis() % 2000).nextInt(1000))
                        .playOn(FlippingSwatchView.this);
            }
        });
    }

    public FlippingSwatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlippingSwatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
