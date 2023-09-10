package com.example.bhagwadgeeta.ui.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.bhagwadgeeta.GeetaApp
import com.example.bhagwadgeeta.base.BaseViewModel
import com.example.bhagwadgeeta.data.repository.GeetaRepository
import com.example.bhagwadgeeta.utils.network.ResultOf
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.ui.home.model.VerseItem
import com.example.bhagwadgeeta.utils.network.NetworkConnectivityLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class GeetaViewModel @Inject constructor(
    private val repository: GeetaRepository,
) :
    BaseViewModel() {
    private val _allChapters = MutableStateFlow<ResultOf<List<ChapterItem?>?>>(ResultOf.Empty())
    val allChapters: StateFlow<ResultOf<List<ChapterItem?>?>>
        get() = _allChapters

    val allFavoriteChaptersFromDB: StateFlow<List<ChapterItem?>?> =
        repository.getFavoriteChapters().asStateFlow(emptyList())

    val allChaptersFromDB: StateFlow<List<ChapterItem?>?> =
        repository.getAllChaptersFromDatabase().asStateFlow(emptyList())

    private val _chapter = MutableStateFlow<ResultOf<ChapterItem?>>(ResultOf.Empty())
    val chapter: StateFlow<ResultOf<ChapterItem?>>
        get() = _chapter

    private val _allVerses = MutableStateFlow<ResultOf<List<VerseItem?>?>>(ResultOf.Empty())
    val allVerses: StateFlow<ResultOf<List<VerseItem?>?>>
        get() = _allVerses

    private val _verse = MutableStateFlow<ResultOf<VerseItem?>>(ResultOf.Empty())
    val verse: StateFlow<ResultOf<VerseItem?>>
        get() = _verse

    val sharedChapterData = MutableStateFlow<ChapterItem?>(null)
    val sharedVerseData = MutableStateFlow<VerseItem?>(null)

    val isApiCallInProgress = MutableStateFlow<Boolean>(false)
    private val appContext: GeetaApp
        get() = GeetaApp.getAppContext()
    val networkConnectivityLiveData = NetworkConnectivityLiveData(appContext)

    init {
//        getAllChapters(18)
    }

    fun getAllChapters(limit: Int) {
        getAllChapterFromApi(limit)
    }

    fun loadChaptersFromDatabase(): List<ChapterItem?>? {
        return repository.getAllChaptersFromDatabase().asStateFlow(emptyList()).value
    }

    fun updateChapterFavoriteStatus(chapterId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateChapterFavoriteStatus(chapterId, isFavorite)
            }
        }
    }

    private fun getAllChapterFromApi(limit: Int) {
        execute {
            isApiCallInProgress.value = true
            _allChapters.value = ResultOf.Progress(true)
            kotlin.runCatching {
                repository.getAllChapters(limit)
            }.onSuccess {
                val deferredInsertion = viewModelScope.async(Dispatchers.IO) {
                    repository.addChaptersToDatabase(it)
                }
                deferredInsertion.await()
                _allChapters.value = ResultOf.Success(it)
            }.onFailure {
                _allChapters.value = ResultOf.Failure(it.message)
            }
            _allChapters.value = ResultOf.Progress(false)
            isApiCallInProgress.value = false
        }
    }

    fun getParticularChapter(chapterNumber: Int) {
        execute {
            isApiCallInProgress.value = true
            _chapter.value = ResultOf.Progress(true)
            kotlin.runCatching {
                repository.getParticularChapter(chapterNumber)
            }.onSuccess {
                _chapter.value = ResultOf.Success(it)
            }.onFailure {
                _chapter.value = ResultOf.Failure(it.message)
            }
            _chapter.value = ResultOf.Progress(false)
            isApiCallInProgress.value = false
        }
    }

    fun getAllVerses(chapterNumber: Int) {
        execute {
            isApiCallInProgress.value = true
            _allVerses.value = ResultOf.Progress(true)
            kotlin.runCatching {
                repository.getAllVerses(chapterNumber)
            }.onSuccess {
                _allVerses.value = ResultOf.Success(it)
            }.onFailure {
                _allVerses.value = ResultOf.Failure(it.message)
            }
            _allVerses.value = ResultOf.Progress(false)
            isApiCallInProgress.value = false
        }
    }

    fun getParticularVerse(chapterNumber: Int, verseNumber: Int) {
        execute {
            isApiCallInProgress.value = true
            _verse.value = ResultOf.Progress(true)
            kotlin.runCatching {
                repository.getParticularVerse(chapterNumber, verseNumber)
            }.onSuccess {
                _verse.value = ResultOf.Success(it)
            }.onFailure {
                _verse.value = ResultOf.Failure(it.message)
            }
            _verse.value = ResultOf.Progress(false)
            isApiCallInProgress.value = false
        }
    }
}