package com.example.bhagwadgeeta.ui.home.fragments

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bhagwadgeeta.R
import com.example.bhagwadgeeta.base.BaseFragment
import com.example.bhagwadgeeta.databinding.FragmentFavoriteBinding
import com.example.bhagwadgeeta.ui.home.MainActivity
import com.example.bhagwadgeeta.ui.home.adapter.ChapterAdapter
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import com.example.bhagwadgeeta.utils.Constants
import com.example.bhagwadgeeta.utils.GeetaSnackBar
import com.example.bhagwadgeeta.utils.network.ResultOf
import com.example.bhagwadgeeta.utils.verticalView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: GeetaViewModel by activityViewModels()
    private val chapterAdapter by lazy { ChapterAdapter(::callback, ::favoriteCallback) }

    override fun setupView() {
        (requireActivity() as MainActivity).toolbarName("Favorites")
        binding.rcvChapters.apply {
            verticalView(context)
            adapter = chapterAdapter
            if (itemDecorationCount == 0) addItemDecoration(itemDecoration(20, 20, 40, 40))
        }
        chapterAdapter.list = listOf(ChapterItem(1, 1, "", ", ", ", ", "ff", ", ", ", ", ", ", 3))
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).toolbarName("Favorites")
    }

    override fun bindViewModel() {
        viewModel.allChaptersFromDB bindTo {
            Log.d("DATA", "${it}")
            chapterAdapter.list = it
        }
    }

    private fun callback(chapter: ChapterItem?) {
        viewModel.sharedChapterData.value = chapter
        if (viewModel.networkConnectivityLiveData.value == true) {
            findNavController().navigate(
                R.id.action_favoriteFragment_to_detailsFragment,
                bundleOf(Constants.CHAPTER_ID to chapter?.id)
            )
        } else {
            showSnackBar(
                "Oops!, It seems you are not connected to internet",
                GeetaSnackBar.SnackType.FAILURE
            )
        }
    }

    private fun favoriteCallback(chapterId: Int, isFavorite: Boolean) {
        viewModel.updateChapterFavoriteStatus(chapterId, isFavorite)
    }

    override fun onClick(view: View) {}

    override fun getViewBinding() = FragmentFavoriteBinding.inflate(layoutInflater)

}