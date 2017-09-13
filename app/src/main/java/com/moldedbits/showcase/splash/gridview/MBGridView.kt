package com.moldedbits.showcase.splash.gridview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.HashMap

/**
 * Created by abhishek
 * on 12/09/17.
 */

class MBGridView : View {
    constructor(context: Context?): super(context) {
        init()
    }

    constructor(context: Context?, attributeSet: AttributeSet): super(context, attributeSet) {
        init()
    }

    private var animator: ValueAnimator? = null
    private var paint: Paint = Paint()
    private var nHorizontalRects: Int = 20
    private var nVerticalRects: Int = 40

    private var isIntialized: Boolean = false

    // determines how many variations do we need for starting flip
    private val nVariations: Int = 10

    // one scale factor for each variation
    private lateinit var scaleFactors: MutableMap<Int, Float>

    // buckets for different animation intervals
    private lateinit var flippingRects: MutableMap<Int, MutableList<FlippableRect>>
    private fun init() {
        scaleFactors = HashMap()
        for(i in 0 until nVariations) {
            animator = ValueAnimator.ofFloat(1.0f, 0.0f, -1.0f)
            animator?.duration = 1000
            animator?.repeatCount = ValueAnimator.INFINITE
            animator?.start()
            animator.

            animator?.addUpdateListener { updateAnimation ->
                val scaleFactor = updateAnimation.animatedValue as Float
                updateAnimation.
                invalidate()
            }
        }



        paint.color = Color.WHITE


        val squareDimension =  50.0f
        flippingRects = HashMap()
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .map {
                    val rectList = mutableListOf<FlippableRect>()
                    for (y in 0 until nVerticalRects) {
                        for(x in 0 until nHorizontalRects) {
                            val rect = FlippableRect(y * squareDimension,
                                    x * squareDimension,
                                    (y + 1) * squareDimension,
                                    (x + 1) * squareDimension)

                            // put rects randomly in buckets
                            val key = ThreadLocalRandom.current().nextInt(0, nVariations)
                            if(flippingRects.containsKey(key)) {
                                flippingRects.getValue(key).add(rect)
                            } else {
                                flippingRects.put(key, mutableListOf(rect))
                            }

                            Timber.d("x: %d y: %d %d %d %d %d", x, y, top, left, bottom, right)
                            rect.paint.color = Color.WHITE
                            rectList.add(rect)
                        }
                    }

                    return@map rectList
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isIntialized = true }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if(isIntialized) {
            for(rect:FlippableRect in flippableRects) {
                rect.draw(canvas, scaleFactor)
            }
        }
    }
}