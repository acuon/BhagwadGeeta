package com.example.bhagwadgeeta.ui.home

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.example.bhagwadgeeta.base.BaseActivity
import com.example.bhagwadgeeta.databinding.ActivityMainBinding
import com.example.bhagwadgeeta.network.ResultOf
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: GeetaViewModel by viewModels()

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
        viewModel.demo bindTo {
            when (it) {
                is ResultOf.Progress -> {}
                is ResultOf.Success -> {}
                is ResultOf.Failure -> {}
                is ResultOf.Empty -> {}
            }
        }
    }

    override fun bindViewEvents() {
        super.bindViewEvents()

    }

    override fun onClick(view: View) {

    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

}