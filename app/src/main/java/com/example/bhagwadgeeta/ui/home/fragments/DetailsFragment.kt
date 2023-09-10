package com.example.bhagwadgeeta.ui.home.fragments

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bhagwadgeeta.R
import com.example.bhagwadgeeta.base.BaseFragment
import com.example.bhagwadgeeta.databinding.FragmentDetailsBinding
import com.example.bhagwadgeeta.ui.home.MainActivity
import com.example.bhagwadgeeta.utils.network.ResultOf
import com.example.bhagwadgeeta.ui.home.adapter.VerseAdapter
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.ui.home.model.VerseItem
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import com.example.bhagwadgeeta.utils.Constants
import com.example.bhagwadgeeta.utils.verticalView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    //    private val chapter by lazy { arguments?.getParcelable<ChapterItem>("chapterItem") }
    private val chapterId by lazy { arguments?.getInt(Constants.CHAPTER_ID, -1) }
    private val verseAdapter by lazy { VerseAdapter(::callback) }

    private val viewModel: GeetaViewModel by activityViewModels()

    private var chapter: ChapterItem? = null

    private val TAG = "DetailsFragment"

    override fun setupView() {
        (requireActivity() as MainActivity).toolbarName("Bhagwad Geeta")
        chapter = viewModel.sharedChapterData.value
        binding.apply {
            if (chapter != null && chapter?.id != null) chapter!!.id?.let {
                viewModel.getAllVerses(it)
            }
            else viewModel.getParticularChapter(chapterId!!)

            Log.d(TAG, "Bundle from HomeFragment to DetailsFragment: $chapter $chapterId")
            updateUI()
            rcvVerses.apply {
                verticalView(context)
                adapter = verseAdapter
                if (itemDecorationCount == 0) addItemDecoration(itemDecoration(20))
            }
        }
    }

    private fun updateUI() {
        binding.apply {
            chapterNumber.text = "Chapter ${chapter?.chapter_number.toString()}"
            name.text = chapter?.name_translated.toString()
            description.text = chapter?.chapter_summary
        }
    }

    fun openFavorites() {
        findNavController().run {
            navigate(R.id.action_detailsFragment_to_favoriteFragment)
        }
    }

    private fun callback(verseItem: VerseItem?) {
        viewModel.sharedVerseData.value = verseItem
        findNavController().navigate(
            R.id.action_detailsFragment_to_verseDetailsFragment,
            bundleOf(Constants.CHAPTER_ID to chapterId, Constants.VERSE_ID to verseItem?.id)
        )
    }

    override fun bindViewModel() {
        viewModel.chapter bindTo {
            when (it) {
                is ResultOf.Progress -> {}

                is ResultOf.Empty -> {
                    Log.d(TAG, "API Call: chapter - Empty")
                }

                is ResultOf.Success -> {
                    Log.d(TAG, "API Call: chapter - Success ${it.value}")
                    chapter = it.value
                    viewModel.getAllVerses(chapter?.id ?: 1)
                    updateUI()
                }

                is ResultOf.Failure -> {
                    Log.d(TAG, "API Call: chapter - Failure")
                }
            }
        }
        viewModel.allVerses bindTo {
            when (it) {
                is ResultOf.Progress -> {}

                is ResultOf.Empty -> {
                    Log.d(TAG, "API Call: allVerses - Empty")
                }

                is ResultOf.Success -> {
                    binding.verseNumber.text = it.value?.size.toString()
                    Log.d(TAG, "API Call: allVerses - Success ${it.value}")
                    verseAdapter.list = it.value
                }

                is ResultOf.Failure -> {
                    Log.d(TAG, "API Call: allVerses - Failure")
                }
            }
        }
    }

    override fun onClick(view: View) {

    }

    override fun onBackPressed() {
//        requireContext().showToast("Back pressed from details")
        findNavController().run {
            previousBackStackEntry?.savedStateHandle?.set(Constants.REFRESH, true)
            popBackStack()
        }
    }

    override fun getViewBinding() = FragmentDetailsBinding.inflate(layoutInflater)

}