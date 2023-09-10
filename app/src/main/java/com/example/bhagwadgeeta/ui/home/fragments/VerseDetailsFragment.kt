package com.example.bhagwadgeeta.ui.home.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bhagwadgeeta.R
import com.example.bhagwadgeeta.base.BaseFragment
import com.example.bhagwadgeeta.databinding.FragmentVerseDetailsBinding
import com.example.bhagwadgeeta.ui.home.MainActivity
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.ui.home.model.VerseItem
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import com.example.bhagwadgeeta.utils.Constants
import com.example.bhagwadgeeta.utils.getCommentary
import com.example.bhagwadgeeta.utils.getTranslation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerseDetailsFragment : BaseFragment<FragmentVerseDetailsBinding>() {

    private val viewModel: GeetaViewModel by activityViewModels()
    private var chapter: ChapterItem? = null
    private var verse: VerseItem? = null

    private val chapterId by lazy { arguments?.getInt(Constants.CHAPTER_ID, -1) }
    private val verseId by lazy { arguments?.getInt(Constants.VERSE_ID, -1) }


    private val TAG = "VerseDetailFrag"
    override fun setupView() {
        (requireActivity() as MainActivity).toolbarName("Bhagwad Geeta")
        chapter = viewModel.sharedChapterData.value
        verse = viewModel.sharedVerseData.value
        Log.d(TAG, "Bundle from DetailsFragment to VerseDetailFragment: $chapterId $verseId")
        updateUI()
    }

    private fun updateUI() {
        binding.apply {
            title.text = "BG ${chapter?.id}.${verse?.id}"
            sanskritText.text = verse?.text
            transliteration.text = verse?.transliteration
            wordMeanings.text = verse?.word_meanings
            translation.text = verse?.getTranslation("english")
            commentary.text = verse?.getCommentary("english")
        }
    }

    fun openFavorites() {
        findNavController().run {
            navigate(R.id.action_verseDetailsFragment_to_favoriteFragment)
        }
    }

    override fun bindViewModel() {

    }

    override fun onClick(view: View) {

    }

    override fun onBackPressed() {
//        requireContext().showToast("Backpressed from verse details")
        findNavController().popBackStack()
    }

    override fun getViewBinding() = FragmentVerseDetailsBinding.inflate(layoutInflater)

}