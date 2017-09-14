package com.moldedbits.showcase.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.moldedbits.showcase.R
import com.moldedbits.showcase.home.HomeActivity
import kotlinx.android.synthetic.main.activity_example_app.*


class SplashScreenActivity : AppCompatActivity() {


    private var waveHelper: WaveHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_example_app)

        val duration = 1000
//        waveHelper = WaveHelper(splashBg, duration)
//        splashBg.setShapeType(WaveView.ShapeType.SQUARE)
//        splashBg.waveShiftRatio = 0.0f
//        splashBg.setWaveColor(
//                Color.parseColor("#ccFFFFFF"),
//                Color.parseColor("#ccFFFFFF"))
//
//        splashBg.setOnClickListener{waveHelper?.start()}

        mbGridView.startColor = ContextCompat.getColor(this, R.color.start_color)
        mbGridView.endColor = ContextCompat.getColor(this, R.color.end_color)

        Handler().postDelayed({mbGridView.start()}, duration.toLong())

//        Handler().postDelayed({presentActivity(nameView)}, duration.toLong() )
    }

    private fun presentActivity(view: View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition")
        val revealX = (view.x + view.width / 2).toInt()
        val revealY = (view.y + view.height / 2).toInt()

        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(HomeActivity.EXTRA_CIRCULAR_REVEAL_X, revealX)
        intent.putExtra(HomeActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY)

        ActivityCompat.startActivity(this, intent, options.toBundle())
    }

    override fun onResume() {
        super.onResume()
//        waveHelper?.start()
    }
//
//    override fun onPause() {
//        super.onPause()
//        waveHelper?.cancel()
//    }
}
