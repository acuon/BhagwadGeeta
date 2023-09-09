package com.example.bhagwadgeeta.ui.home.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "chapters")
data class ChapterItem(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "chapter_number")
    val chapter_number: Int? = null,
    @ColumnInfo(name = "chapter_summary")
    val chapter_summary: String? = null,
    @ColumnInfo(name = "chapter_summary_hindi")
    val chapter_summary_hindi: String? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "name_meaning")
    val name_meaning: String? = null,
    @ColumnInfo(name = "name_translated")
    val name_translated: String? = null,
    @ColumnInfo(name = "name_transliterated")
    val name_transliterated: String? = null,
    @ColumnInfo(name = "slug")
    val slug: String? = null,
    @ColumnInfo(name = "verses_count")
    val verses_count: Int? = null
) {
}