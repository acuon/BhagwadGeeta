package com.example.bhagwadgeeta.ui

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.bhagwadgeeta.base.BaseActivity
import com.example.bhagwadgeeta.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun present(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            })
        }
    }

    override fun setupView() {

    }

    override fun bindViewModel() {

    }

    override fun bindViewEvents() {
        super.bindViewEvents()

    }

    override fun onClick(view: View) {

    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

}