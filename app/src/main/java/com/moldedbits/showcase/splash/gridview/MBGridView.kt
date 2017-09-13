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
    var nHorizontalRects: Int = 20
    var nVerticalRects: Int = 40

    private var isIntialized: Boolean = false
    lateinit var flippableRects: List<FlippableRect>

    private fun init() {
        animator = ValueAnimator.ofFloat(1.0f, 0.0f, -1.0f)
        animator?.duration = 1000
        animator?.repeatCount = ValueAnimator.INFINITE
        animator?.start()

        paint.color = Color.WHITE
        animator?.addUpdateListener { updateAnimation ->
            scaleFactor = updateAnimation.animatedValue as Float
            invalidate()
//            if(scaleFactor < 0.0) {
//                rect.paint.color = Color.RED
//            }
        }

        val squareDimension: Float =  50.0f
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
                            Timber.d("x: %d y: %d %d %d %d %d", x, y, top, left, bottom, right)
                            rect.paint.color = Color.WHITE
                            rectList.add(rect)
                        }
                    }

                    return@map rectList
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  rectList: List<FlippableRect> ->
                    isIntialized = true
                    flippableRects = rectList
                }
    }

    private var scaleFactor:Float = 0.0f

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if(isIntialized) {
            for(rect:FlippableRect in flippableRects) {
                rect.draw(canvas, scaleFactor)
            }
        }
    }
}