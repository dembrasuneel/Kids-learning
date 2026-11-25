package com.example.data.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

enum class SubjectCategory {
    LANGUAGES,
    STEM,
    GK_VALUES,
    CREATIVE
}

data class Subject(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: SubjectCategory,
    @ColorRes val bgColorRes: Int,
    @ColorRes val accentColorRes: Int,
    @DrawableRes val iconRes: Int,
    val chapterCount: Int,
    val completedChapters: Int,
    val sampleTopics: List<String>,
    val urduSample: String? = null
) {
    val progressPercent: Int
        get() = if (chapterCount <= 0) 0 else ((completedChapters * 100f) / chapterCount).toInt().coerceIn(0, 100)
}
