package com.moldedbits.showcase.animations

import android.animation.ObjectAnimator
import android.view.View

import com.daimajia.androidanimations.library.BaseViewAnimator
import com.daimajia.easing.Glider
import com.daimajia.easing.Skill

/**
 * Created by abhishek
 * on 06/09/17.
 */

class SplashBgSlideDownAnimator : BaseViewAnimator() {
    public override fun prepare(target: View) {
        val distance:Int = target.top + target.height
        animatorAgent.playTogether(Glider.glide(Skill.BounceEaseOut, duration.toFloat(),
                        ObjectAnimator.ofFloat(target, "translationY", -distance.toFloat(), 0.0f)))
    }
}