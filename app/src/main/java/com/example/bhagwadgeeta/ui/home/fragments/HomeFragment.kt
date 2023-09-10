package com.example.bhagwadgeeta.ui.home.fragments

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import com.example.bhagwadgeeta.base.BaseFragment
import com.example.bhagwadgeeta.databinding.FragmentHomeBinding
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.bhagwadgeeta.R
import com.example.bhagwadgeeta.ui.home.MainActivity
import com.example.bhagwadgeeta.utils.network.ResultOf
import com.example.bhagwadgeeta.ui.home.adapter.ChapterAdapter
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.utils.Constants
import com.example.bhagwadgeeta.utils.GeetaSnackBar
import com.example.bhagwadgeeta.utils.verticalView

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: GeetaViewModel by activityViewModels()
    private val chapterAdapter by lazy { ChapterAdapter(::callback, ::favoriteCallback) }

    private val TAG = "HomeFragment"
    override fun setupView() {
        (requireActivity() as MainActivity).toolbarName("Bhagwad Geeta")
        binding.rcvChapters.apply {
            verticalView(context)
            adapter = chapterAdapter
            if (itemDecorationCount == 0) addItemDecoration(itemDecoration(20, 20, 40, 40))
        }
    }

    override fun bindViewModel() {
        viewModel.networkConnectivityLiveData.observe(this, Observer { isConnected ->
            if (isConnected) {
                viewModel.getAllChapters(Constants.CHAPTER_LIMIT)
            } else {
                viewModel.allChaptersFromDB bindTo {
                    chapterAdapter.list = it
                }
            }
        })
        findNavController().currentBackStackEntry?.savedStateHandle?.run {
            getLiveData<Boolean>(Constants.REFRESH).observe(viewLifecycleOwner) {
                if (it == true) {
//                    viewModel.getAllChapters(Constants.CHAPTER_LIMIT)
                }
                remove<Boolean>(Constants.REFRESH)
            }
        }
        viewModel.allChapters bindTo {
            when (it) {
                is ResultOf.Progress -> {}
                is ResultOf.Empty -> {
                    Log.d(TAG, "API Call: allChapters - Empty")
                }

                is ResultOf.Success -> {
                    Log.d(TAG, "API Call: allChapters - Success ${it.value}")
                    chapterAdapter.list = it.value
                }

                is ResultOf.Failure -> {
                    Log.d(TAG, "API Call: allChapters - Failure")
                }
            }
        }
    }

    private fun callback(chapter: ChapterItem?) {
        viewModel.sharedChapterData.value = chapter
        if (viewModel.networkConnectivityLiveData.value == true) {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
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

    fun openFavorites() {
        findNavController().navigate(
            R.id.action_homeFragment_to_favoriteFragment
        )
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        binding.apply {
            readNow.setOnClickListener(onClickListener)
        }
    }

    override fun onClick(view: View) {
        when (view) {
//            binding.readNow -> callback()
        }
    }

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

}