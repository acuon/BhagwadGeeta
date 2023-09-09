package com.example.bhagwadgeeta.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bhagwadgeeta.R
import com.example.bhagwadgeeta.base.BaseActivity
import com.example.bhagwadgeeta.databinding.ActivityMainBinding
import com.example.bhagwadgeeta.ui.home.fragments.HomeFragment
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import com.example.bhagwadgeeta.utils.KeepStateNavigator
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.plusAssign
import androidx.navigation.findNavController
import com.example.bhagwadgeeta.ui.home.fragments.DetailsFragment
import com.example.bhagwadgeeta.ui.home.fragments.VerseDetailsFragment

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: GeetaViewModel by viewModels()

        private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val fragmentManager = supportFragmentManager

    companion object {
        fun present(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configBottomNav()
    }

    private fun configBottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
        val navigator =
            KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.nav_home)
    }

    override fun setupView() {

    }

    override fun bindViewModel() {

    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onClick(view: View) {

    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navHostFragment?.childFragmentManager?.fragments?.get(0)?.run {
            when(this){
                is HomeFragment -> super.onBackPressed()
                is DetailsFragment -> backPressed()
                is VerseDetailsFragment -> onBackPressed()
                else -> super.onBackPressed()
            }
        }

    }
}