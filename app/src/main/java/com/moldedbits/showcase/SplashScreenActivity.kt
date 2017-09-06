package com.moldedbits.showcase

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.moldedbits.showcase.animations.SplashBgSlideDownAnimator
import kotlinx.android.synthetic.main.activity_example_app.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_example_app)

        splashBg.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                splashBg.viewTreeObserver.removeOnGlobalLayoutListener(this)
                YoYo.with(SplashBgSlideDownAnimator())
                        .duration(1000)
                        .onEnd {
                            nameView.visibility = View.VISIBLE
                            YoYo.with(Techniques.Tada)
                                    .duration(800)
                                    .playOn(nameView)
                            YoYo.with(Techniques.Pulse)
                                    .duration(400)
                                    .playOn(logoIv)
                        }
                        .playOn(splashBg)
            }
        })
    }
}
