package com.example.bhagwadgeeta.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.bhagwadgeeta.data.pref.GeetaPreferences
import com.example.bhagwadgeeta.utils.GeetaSnackBar
import com.example.bhagwadgeeta.utils.hideSoftKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B
    private var snackBar: GeetaSnackBar? = null
    val pref by lazy { GeetaPreferences() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preInit()
        binding = getViewBinding()
        setContentView(binding.root)
        init()
        setupView()
        bindViewEvents()
        bindViewModel()
    }

    private fun init() {
    }

    protected open fun preInit() {

    }

    protected abstract fun setupView()
    protected open fun bindViewEvents() {
        requireNotNull(binding.root).setOnClickListener {
            hideSoftKeyboard()
        }
    }

    protected abstract fun bindViewModel()

    abstract fun getViewBinding(): B

    protected val onClickListener = View.OnClickListener {
        hideSoftKeyboard()
        onClick(it)
    }

    protected fun showSnackBar(message: String?, type: GeetaSnackBar.SnackType) {
        binding.root.let {
            snackBar = GeetaSnackBar
                .Builder()
                .type(type)
                .message(message)
                .setCallBack(snackListener)
                .make(it)
                .showSnack()
        }
    }

    private val snackListener = object : GeetaSnackBar.Builder.ISnackListener {
        override fun onClosed(view: View) {
            snackBar?.dismiss()
        }
    }

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        with(this) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    collect { action(it) }
                }
            }
        }
    }


    protected abstract fun onClick(view: View)

    fun runDelayed(delayMilliSec: Long, job: suspend () -> Unit) =
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                delay(delayMilliSec)
                withContext(Dispatchers.Main) {
                    job.invoke()
                }
            }
        }

}