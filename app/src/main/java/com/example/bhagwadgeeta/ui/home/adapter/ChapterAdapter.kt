package com.example.bhagwadgeeta.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bhagwadgeeta.R
import com.example.bhagwadgeeta.databinding.ItemChapterBinding
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.utils.inflater
import com.example.bhagwadgeeta.utils.showToast

class ChapterAdapter(val callback: (ChapterItem?) -> Unit, val favoriteCallback: (Int, Boolean) -> Unit) :
    RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    var list: List<ChapterItem?>? = null
        set(value) {
            field = value
            notifyItemRangeChanged(0, value?.size ?: 0)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemChapterBinding.inflate(parent.inflater(), null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class ViewHolder(private val binding: ItemChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chapter: ChapterItem?) {
            binding.apply {
                root.setOnClickListener {
                    callback.invoke(chapter)
                }
                addToFavorite.setOnClickListener {
                    chapter.run {
                        chapter?.favorite = chapter?.favorite != true
                        notifyItemChanged(adapterPosition)
                        favoriteCallback.invoke(chapter?.id!!, chapter.favorite)
                    }
                }
                chapterNumber.text = "Chapter ${chapter?.chapter_number}"
                name.text = chapter?.name_translated
                description.text = chapter?.chapter_summary
                verseNumber.text = chapter?.verses_count.toString()

                Glide.with(binding.addToFavorite)
                    .load(if (chapter?.favorite == true) R.drawable.ic_favorite_filled else R.drawable.ic_favorite)
                    .into(binding.addToFavorite)
            }
        }
    }
}