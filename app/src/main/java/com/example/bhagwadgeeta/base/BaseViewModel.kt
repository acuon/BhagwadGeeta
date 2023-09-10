package com.example.bhagwadgeeta.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bhagwadgeeta.GeetaApp
import com.example.bhagwadgeeta.data.pref.GeetaPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    val pref by lazy { GeetaPreferences() }
    val context by lazy { GeetaApp.getAppContext() }

    fun execute(dispatcher: CoroutineContext = Dispatchers.Main, job: suspend () -> Unit) =
        viewModelScope.launch(dispatcher) {
            job.invoke()
        }


    fun <T> Flow<T>.asStateFlow(defaultValue: T): StateFlow<T> {
        return stateIn(viewModelScope, SharingStarted.Eagerly, defaultValue)
    }


    fun ignoreCoroutineException(throwable: Throwable, callback: () -> Unit) {
        if (throwable.message?.contains("Standalone") != true)
            callback.invoke()
    }
}