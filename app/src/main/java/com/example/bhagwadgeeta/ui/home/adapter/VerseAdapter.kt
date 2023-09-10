package com.example.bhagwadgeeta.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bhagwadgeeta.databinding.ItemVersesBinding
import com.example.bhagwadgeeta.ui.home.model.VerseItem
import com.example.bhagwadgeeta.utils.getTranslation
import com.example.bhagwadgeeta.utils.inflater


class VerseAdapter(val callback: (VerseItem?) -> Unit) :
    RecyclerView.Adapter<VerseAdapter.ViewHolder>() {

    var list: List<VerseItem?>? = null
        set(value) {
            field = value
            notifyItemRangeChanged(0, value?.size ?: 0)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemVersesBinding.inflate(parent.inflater(), null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class ViewHolder(private val binding: ItemVersesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(verse: VerseItem?) {
            binding.apply {
                root.setOnClickListener {
                    callback.invoke(verse)
                }
                verseNumber.text = "Verse ${verse?.verse_number}"
                verseDescription.text = verse?.getTranslation("english")
            }
        }
    }
}