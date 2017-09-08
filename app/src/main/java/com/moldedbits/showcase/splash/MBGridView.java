package com.moldedbits.showcase.splash;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import timber.log.Timber;

/**
 * Created by abhishek
 * on 08/09/17.
 */

public class MBGridView extends LinearLayout {
    public MBGridView(Context context) {
        super(context);
        init(context);
    }

    private int horizontalSwatches = 10;
    private int verticalSwatches = 20;

    private void init(final Context context) {
        setOrientation(VERTICAL);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int width = getMeasuredWidth();
                int height = getMeasuredHeight();

                int nHorizontalSwatches = (int)Math.ceil((double)width/horizontalSwatches);
                int nVerticalSwatches = (int)Math.ceil((double)height/verticalSwatches);

                Timber.i("%d, %d, %d, %d", width, height, nHorizontalSwatches, nVerticalSwatches);

                for (int i = 0; i < nVerticalSwatches; i++) {
                    LayoutParams params =
                            new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                    params.setLayoutDirection(HORIZONTAL);
                    LinearLayout layout = new LinearLayout(context);
                    layout.setLayoutParams(params);

                    for (int j = 0; j < nHorizontalSwatches; j++) {
                        FlippingSwatchView view = new FlippingSwatchView(context);
                        LayoutParams swatchParams = new LayoutParams(10, 10);
                        swatchParams.setMargins(2,2,2, 2);
                        view.setLayoutParams(swatchParams);
                        view.setBackgroundColor(Color.RED);

                        layout.addView(view);
                    }

                    addView(layout);
                }
            }
        });
    }

    public MBGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MBGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
}
