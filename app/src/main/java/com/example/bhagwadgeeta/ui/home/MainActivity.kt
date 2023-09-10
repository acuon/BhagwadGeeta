package com.example.bhagwadgeeta.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.bhagwadgeeta.R
import com.example.bhagwadgeeta.base.BaseActivity
import com.example.bhagwadgeeta.databinding.ActivityMainBinding
import com.example.bhagwadgeeta.ui.home.fragments.HomeFragment
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.bhagwadgeeta.ui.home.fragments.DetailsFragment
import com.example.bhagwadgeeta.ui.home.fragments.VerseDetailsFragment
import com.example.bhagwadgeeta.utils.Constants
import com.example.bhagwadgeeta.utils.GeetaSnackBar

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: GeetaViewModel by viewModels()
    private val TAG = "MainActivity"

    companion object {
        fun present(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.topAppBar)

        viewModel.getAllChapters(Constants.CHAPTER_LIMIT)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, binding.topAppBar.menu)
    }

    override fun setupView() {
    }

    fun toolbarName(name: String) {
        supportActionBar?.title = name
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                navHostFragment?.childFragmentManager?.fragments?.get(0)?.run {
                    when (this) {
                        is HomeFragment -> openFavorites()
                        is DetailsFragment -> openFavorites()
                        is VerseDetailsFragment -> openFavorites()
                    }
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun bindViewModel() {
        viewModel.networkConnectivityLiveData.observe(this, Observer { isConnected ->
            if (isConnected) {
                // Internet is connected, show a Snackbar or perform any action
                showSnackBar("Connected with Internet", GeetaSnackBar.SnackType.SUCCESS)
            } else {
                showSnackBar("Internet is Not Connected", GeetaSnackBar.SnackType.FAILURE)
                // Internet is not connected
            }
        })
        viewModel.isApiCallInProgress bindTo {
            when (it) {
                true -> {
                    Log.d(TAG, "True - Api Call in Progress")
                    binding.progressView.show()
                }

                false -> {
                    Log.d(TAG, "False - Api Call Finished")
                    binding.progressView.hide()
                }

                else -> binding.progressView.hide()
            }
        }
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onClick(view: View) {}

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navHostFragment?.childFragmentManager?.fragments?.get(0)?.run {
            when (this) {
                is HomeFragment -> super.onBackPressed()
                is DetailsFragment -> onBackPressed()
                is VerseDetailsFragment -> onBackPressed()
                else -> super.onBackPressed()
            }
        }
    }
}