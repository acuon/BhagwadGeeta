package com.example.bhagwadgeeta.ui.home.fragments

import android.graphics.Rect
import android.util.Log
import android.view.View
import com.example.bhagwadgeeta.base.BaseFragment
import com.example.bhagwadgeeta.databinding.FragmentHomeBinding
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bhagwadgeeta.R
import com.example.bhagwadgeeta.network.ResultOf
import com.example.bhagwadgeeta.ui.home.adapter.ChapterAdapter
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.utils.verticalView

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: GeetaViewModel by activityViewModels()
    private val chapterAdapter by lazy { ChapterAdapter(::callback) }

    override fun setupView() {
        binding.rcvChapters.apply {
            verticalView(context)
            adapter = chapterAdapter
            addItemDecoration(itemDecorator)
        }
    }

    private val itemDecorator by lazy {
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                with(outRect) {
                    top = 20
                    left = 40
                    right = 40
                    bottom = 20
                }
            }
        }
    }

    override fun bindViewModel() {
        findNavController().currentBackStackEntry?.savedStateHandle?.run {
            getLiveData<Boolean>("refresh").observe(viewLifecycleOwner) {
                if (it == true) {
                    viewModel.getAllChapters(18)
                }
                remove<Boolean>("refresh")
            }
        }
        viewModel.allChapters bindTo {
            when (it) {
                is ResultOf.Progress -> {}
                is ResultOf.Empty -> {
                    Log.d("AllChapters", "Empty")
                }

                is ResultOf.Success -> {
                    Log.d("AllChapters", "Success ${it.value}")
                    chapterAdapter.list = it.value
                }

                is ResultOf.Failure -> {
                    Log.d("AllChapters", "Failure")
                }
            }
        }
    }

    private fun callback(chapter: ChapterItem?, type: Int) {
        findNavController().navigate(
            if (type == 0) R.id.action_homeFragment_to_detailsFragment else R.id.action_homeFragment_to_verseDetailsFragment,
            bundleOf("chapterItem" to chapter?.id.toString())
        )
    }

    override fun onClick(view: View) {

    }

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

}