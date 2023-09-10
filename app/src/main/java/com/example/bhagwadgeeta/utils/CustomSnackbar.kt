package com.example.bhagwadgeeta.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bhagwadgeeta.R
import com.google.android.material.snackbar.ContentViewCallback

class CustomSnackBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

     val snackPic: ImageView

    init {
        View.inflate(context, R.layout.widget_snackbar, this)
        this.snackPic = findViewById(R.id.ivSnackPic)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val scaleX = ObjectAnimator.ofFloat(snackPic, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(snackPic, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(500)
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

    override fun animateContentOut(delay: Int, duration: Int) {
    }
}