package com.example.bhagwadgeeta.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bhagwadgeeta.databinding.ItemChapterBinding
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.utils.inflater

class ChapterAdapter(val callback: (ChapterItem?, Int) -> Unit) : RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

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

    inner class ViewHolder(private val binding: ItemChapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chapter: ChapterItem?) {
            binding.apply {
                root.setOnClickListener {
                    callback.invoke(chapter, 0)
                }
                layoutVerses.setOnClickListener {
                    callback.invoke(chapter, 1)
                }
                chapterNumber.text = "Chapter ${chapter?.chapter_number}"
                name.text = chapter?.name_transliterated
                description.text = chapter?.chapter_summary
                verseNumber.text = chapter?.verses_count.toString()
            }
        }
    }
}