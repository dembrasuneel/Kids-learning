package com.example.data.model

import androidx.annotation.DrawableRes

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    @DrawableRes val iconRes: Int,
    val pointsReward: Int
)

data class DailyQuizQuestion(
    val id: String,
    val subjectLabel: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)
