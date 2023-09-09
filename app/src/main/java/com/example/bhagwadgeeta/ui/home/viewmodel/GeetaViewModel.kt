package com.example.bhagwadgeeta.ui.home.viewmodel

import com.example.bhagwadgeeta.base.BaseViewModel
import com.example.bhagwadgeeta.data.repository.GeetaRepository
import com.example.bhagwadgeeta.network.ResultOf
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.ui.home.model.VerseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GeetaViewModel @Inject constructor(private val repository: GeetaRepository) :
    BaseViewModel() {
    private val _allChapters = MutableStateFlow<ResultOf<List<ChapterItem?>?>>(ResultOf.Empty())
    val allChapters: StateFlow<ResultOf<List<ChapterItem?>?>>
        get() = _allChapters

    private val _chapter = MutableStateFlow<ResultOf<ChapterItem?>>(ResultOf.Empty())
    val chapter: StateFlow<ResultOf<ChapterItem?>>
        get() = _chapter

    private val _allVerses = MutableStateFlow<ResultOf<List<VerseItem?>?>>(ResultOf.Empty())
    val allVerses: StateFlow<ResultOf<List<VerseItem?>?>>
        get() = _allVerses

    private val _verse = MutableStateFlow<ResultOf<VerseItem?>>(ResultOf.Empty())
    val verse: StateFlow<ResultOf<VerseItem?>>
        get() = _verse

    init {
        getAllChapters(18)
    }

    fun getAllChapters(limit: Int) {
        execute {
            _allChapters.value = ResultOf.Progress(true)
            kotlin.runCatching {
                repository.getAllChapters(limit)
            }.onSuccess {
                _allChapters.value = ResultOf.Success(it)
            }.onFailure {
                _allChapters.value = ResultOf.Failure(it.message)
            }
            _allChapters.value = ResultOf.Progress(false)
        }
    }

    fun getParticularChapter(chapterNumber: Int) {
        execute {
            _chapter.value = ResultOf.Progress(true)
            kotlin.runCatching {
                repository.getParticularChapter(chapterNumber)
            }.onSuccess {
                _chapter.value = ResultOf.Success(it)
            }.onFailure {
                _chapter.value = ResultOf.Failure(it.message)
            }
            _chapter.value = ResultOf.Progress(false)
        }
    }

    fun getAllVerses(chapterNumber: Int) {
        execute {
            _allVerses.value = ResultOf.Progress(true)
            kotlin.runCatching {
                repository.getAllVerses(chapterNumber)
            }.onSuccess {
                _allVerses.value = ResultOf.Success(it)
            }.onFailure {
                _allVerses.value = ResultOf.Failure(it.message)
            }
            _allVerses.value = ResultOf.Progress(false)
        }
    }

    fun getParticularVerse(chapterNumber: Int, verseNumber: Int) {
        execute {
            _verse.value = ResultOf.Progress(true)
            kotlin.runCatching {
                repository.getParticularVerse(chapterNumber, verseNumber)
            }.onSuccess {
                _verse.value = ResultOf.Success(it)
            }.onFailure {
                _verse.value = ResultOf.Failure(it.message)
            }
            _verse.value = ResultOf.Progress(false)
        }
    }
}