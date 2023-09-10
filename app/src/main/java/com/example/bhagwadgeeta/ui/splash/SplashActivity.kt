package com.example.bhagwadgeeta.ui.splash

import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.bhagwadgeeta.R
import com.example.bhagwadgeeta.base.BaseActivity
import com.example.bhagwadgeeta.databinding.ActivitySplashBinding
import com.example.bhagwadgeeta.ui.home.MainActivity
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import dagger.hilt.android.AndroidEntryPoint

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun setupView() {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in)
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
//                val intent = Intent(this@SplashActivity, MainActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                startActivity(intent)
                MainActivity.present(this@SplashActivity)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })
        binding.ivAppLogo.startAnimation(fadeIn)
    }

    override fun bindViewModel() {}

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun onClick(view: View) {

    }

}