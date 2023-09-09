package com.example.bhagwadgeeta.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bhagwadgeeta.base.BaseViewModel
import com.example.bhagwadgeeta.data.database.Temp
import com.example.bhagwadgeeta.data.repository.GeetaRepository
import com.example.bhagwadgeeta.network.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GeetaViewModel @Inject constructor(private val repository: GeetaRepository) :
    BaseViewModel() {
    private val _demo = MutableStateFlow<ResultOf<Temp>>(ResultOf.Empty())
    val demo: StateFlow<ResultOf<Temp>>
        get() = _demo

    fun demo() {
        execute {
            _demo.value = ResultOf.Progress(true)
            _demo.value = ResultOf.Progress(true)
            kotlin.runCatching {
                repository.getAllChapters()
            }.onSuccess {
                _demo.value = ResultOf.Success(it)
            }.onFailure {
                _demo.value = ResultOf.Failure(it.message)
            }
            _demo.value = ResultOf.Progress(false)
        }
    }
}