package com.moldedbits.showcase.splash.gridview

import android.graphics.Canvas
import android.graphics.Paint
import timber.log.Timber

/**
 * Created by abhishek
 * on 13/09/17.
 */
class FlippableRect {
    private var pivotX:Float
    private var pivotY: Float
    private var top: Float

    private var left: Float

    private var bottom: Float

    private var right: Float

    var paint: Paint

    constructor(top:Float, left:Float, bottom:Float, right:Float) {
        this.top = top
        this.left = left
        this.bottom = bottom
        this.right = right
        
        this.pivotX = (right + left) / 2
        this.pivotY = (bottom + top) /2

        paint = Paint()
    }
    
    fun draw(canvas:Canvas?, scaleFactor: Float, color:Int) {
        canvas?.save()
        canvas?.scale(scaleFactor, 1.0f, pivotX, pivotY)
        paint.color = color
        canvas?.drawRect(left, top, right, bottom, paint)
        canvas?.restore()
    }
}