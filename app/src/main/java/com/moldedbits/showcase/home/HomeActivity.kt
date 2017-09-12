package com.moldedbits.showcase.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import com.moldedbits.showcase.R
import kotlinx.android.synthetic.main.activity_home.*


open class HomeActivity : AppCompatActivity() {

    companion object {
        var EXTRA_CIRCULAR_REVEAL_X: String = "key_reveal_x"
        var EXTRA_CIRCULAR_REVEAL_Y: String = "key_reveal_y"
    }

    private var revealX: Int = 0;
    private var revealY: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null
                && intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X)
                && intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            revealX  = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)


            val viewTreeObserver = rootLayout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        revealActivity(revealX, revealY)
                        rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        } else {
            rootLayout.visibility = View.VISIBLE
        }
    }

    protected fun revealActivity(x: Int, y: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val finalRadius = (Math.max(rootLayout.width, rootLayout.height) * 1.1).toFloat()

            // create the animator for this view (the start radius is zero)
            val circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0f, finalRadius)
            circularReveal.duration = 400
            circularReveal.interpolator = AccelerateInterpolator()

            // make the view visible and start the animation
            rootLayout.visibility = View.VISIBLE
            circularReveal.start()
        } else {
            finish()
        }
    }

    protected fun unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish()
        } else {
            val finalRadius = (Math.max(rootLayout.width, rootLayout.height) * 1.1).toFloat()
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0f)

            circularReveal.duration = 400
            circularReveal.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    rootLayout.visibility = View.INVISIBLE
                    finish()
                }
            })

            circularReveal.start()
        }
    }
}
