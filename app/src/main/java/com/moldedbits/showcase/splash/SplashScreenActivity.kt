package com.moldedbits.showcase.splash

import android.content.Context
import android.content.Intent
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
import com.moldedbits.showcase.home.HomeActivity
import kotlinx.android.synthetic.main.activity_example_app.*
import tyrantgit.explosionfield.ExplosionField
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat



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

        val explosion: ExplosionField = ExplosionField.attach2Window(this)
        splashBg.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                splashBg.viewTreeObserver.removeOnGlobalLayoutListener(this)
                YoYo.with(Techniques.Pulse)
                        .delay(3000)
                        .duration(400)
                        .onEnd {
                            nameView.visibility = View.VISIBLE
                            YoYo.with(Techniques.RubberBand)
                                .duration(800)
                                .onEnd {
                                    presentActivity(logoIv)
                                }
                                .playOn(nameView)
                        }
                        .playOn(logoIv)
            }
        })
    }

    fun presentActivity(view: View) {
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
        waveHelper?.start()
    }

    override fun onPause() {
        super.onPause()
        waveHelper?.cancel()
    }
}
