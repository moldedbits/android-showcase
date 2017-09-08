package com.moldedbits.showcase.splash

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.moldedbits.showcase.R
import kotlinx.android.synthetic.main.activity_example_app.*

class SplashScreenActivity : AppCompatActivity() {


    private var waveHelper: WaveHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_example_app)

        waveHelper = WaveHelper(splashBg)
        splashBg.setShapeType(WaveView.ShapeType.SQUARE)
        splashBg.waveShiftRatio = 0.0f
        splashBg.setWaveColor(
                Color.parseColor("#FFFFFF"),
                Color.parseColor("#FFFFFF"))

        splashBg.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                splashBg.viewTreeObserver.removeOnGlobalLayoutListener(this)
                YoYo.with(Techniques.Tada)
                        .onStart { nameView.visibility = View.VISIBLE }
                        .delay(5000)
                        .duration(800)
                        .playOn(nameView)
                YoYo.with(Techniques.Pulse)
                        .onStart { nameView.visibility = View.VISIBLE }
                        .delay(5000)
                        .duration(400)
                        .playOn(logoIv)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        waveHelper?.start()
    }

    override fun onPause() {
        super.onPause()
        waveHelper?.cancel()
    }
}
