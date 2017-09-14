package com.moldedbits.showcase.splash.gridview

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color

/**
 * Created by abhishek
 * on 14/09/17.
 */
class FlippingBucket {
    var animator: ValueAnimator? = null
    var flippableRects: MutableList<FlippableRect> = mutableListOf()
    var starColor: Int = Color.WHITE
    var endColor: Int = Color.BLUE

    private var scaleFactor: Float = 1.0f

    fun createAnimator(duration: Long) {
        animator = ValueAnimator.ofFloat(1.0f, 0.0f, -1.0f)
        animator?.duration = duration

        animator?.addUpdateListener { updateAnimation ->
            scaleFactor = updateAnimation.animatedValue as Float
        }
    }

    fun start(delay: Long) {
        animator?.startDelay = delay
        animator?.start()
    }

    fun draw(canvas: Canvas?) {
        for(rect:FlippableRect in flippableRects) {
            if(scaleFactor >= 0) {
                rect.draw(canvas, scaleFactor, starColor)
            } else {
                rect.draw(canvas, scaleFactor, endColor)
            }
        }
    }
}