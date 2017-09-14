package com.moldedbits.showcase.splash.gridview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by abhishek
 * on 12/09/17.
 */

class MBGridView : View {
    constructor(context: Context?): super(context)

    constructor(context: Context?, attributeSet: AttributeSet): super(context, attributeSet)

    private var nHorizontalRects: Int = 20
    private var nVerticalRects: Int = 40

    private var isIntialized: Boolean = false

    // Animation flipDuration in millisecond for each flip
    var flipDuration: Long = 1500

    // Duration for complete animation
    var animationDuration: Long = 3000

    // determines how many variations do we need for starting flip
    private val nVariations: Int = 12

    // one scale factor for each variation
    private lateinit var scaleFactors: MutableMap<Int, Float>

    // starting color of rects
    var startColor:Int = Color.WHITE

    // final color of rects
    var endColor: Int = Color.BLUE

    // buckets for different animation intervals
    private var flippingBuckets: MutableMap<Int, FlippingBucket> = HashMap()

    // offset between successive rects
    private var border = 5

    // needed size of a square
    val squareDimension =  40.0f

    private var animator: ValueAnimator? = null

    private fun init() {
        scaleFactors = HashMap()

        for(i in 0 until nVariations) {
            val bucket = FlippingBucket()
            bucket.createAnimator(flipDuration)
            bucket.starColor = startColor

            // randomly darken or lighten color
            val hsv: FloatArray = kotlin.FloatArray(3)
            Color.colorToHSV(endColor, hsv)
            var saturation = hsv[1]
            Timber.d(" Before: %f", hsv[1])
            saturation -= ThreadLocalRandom.current().nextInt(0, 10)/20.0f
            hsv[1] = saturation
            Timber.d("After: %f", hsv[1])

            bucket.endColor = Color.HSVToColor(hsv)
            flippingBuckets.put(i, bucket)
        }

        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .map {
                    for (y in 0 until nVerticalRects) {
                        for(x in 0 until nHorizontalRects) {
                            val rect = FlippableRect(border + y  * squareDimension,
                                    border + x * squareDimension,
                                    (y + 1) * squareDimension,
                                    (x + 1) * squareDimension)

                            // put rects randomly in buckets
                            val key = ThreadLocalRandom.current().nextInt(0, nVariations)
                            if(flippingBuckets.containsKey(key)) {
                                flippingBuckets.getValue(key).flippableRects.add(rect)
                            }

                            rect.paint.color = Color.WHITE
                        }
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    isIntialized = true
                    animator = ValueAnimator.ofFloat(1.0f, 0.0f)
                    animator?.duration = animationDuration
                    animator?.addUpdateListener { invalidate() }
                    invalidate()
//                    animator.repeatCount = ValueAnimator.INFINITE
//                    animator.repeatMode = ValueAnimator.REVERSE

                }
    }

    fun start() {
        val delayInterval = animationDuration - flipDuration
        flippingBuckets.forEach {
            it.value.start(ThreadLocalRandom.current().nextLong(0, delayInterval))
        }

        animator?.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if(!isIntialized) {
            nHorizontalRects = Math.ceil((w.toFloat()/squareDimension).toDouble()).toInt()
            nVerticalRects = Math.ceil((h.toFloat()/squareDimension).toDouble()).toInt()
            init()
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if(isIntialized) {
            flippingBuckets.forEach { it.value.draw(canvas) }
        }
    }
}