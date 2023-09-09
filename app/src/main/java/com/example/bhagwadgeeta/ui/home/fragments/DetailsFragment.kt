package com.example.bhagwadgeeta.ui.home.fragments

import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.bhagwadgeeta.base.BaseFragment
import com.example.bhagwadgeeta.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

//    private val chapter by lazy { arguments?.getParcelable<ChapterItem>("chapterItem") }
    private val chapter by lazy { arguments?.getString("chapterItem") }

    override fun setupView() {
        Log.d("Chapter", chapter.toString())
    }

    override fun bindViewModel() {

    }

    override fun onClick(view: View) {

    }

    fun backPressed() {
        findNavController().run {
            previousBackStackEntry?.savedStateHandle?.set("refresh", true)
            popBackStack()
        }
    }

    override fun getViewBinding() = FragmentDetailsBinding.inflate(layoutInflater)

}