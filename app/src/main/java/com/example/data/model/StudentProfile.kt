package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

enum class CurriculumTrack(val label: String) {
    ISLAMIC_STUDIES("Islamic Studies"),
    ETHICS("Ethics & Moral Values")
}

enum class StudentAvatar(
    val id: String,
    val label: String,
    @DrawableRes val drawableRes: Int
) {
    EXPLORER("explorer", "Explorer", R.drawable.img_avatar_explorer),
    ASTRONAUT("astronaut", "Astronaut", R.drawable.img_avatar_astronaut);

    companion object {
        fun fromId(id: String?): StudentAvatar =
            entries.firstOrNull { it.id == id } ?: EXPLORER
    }
}

data class StudentProfile(
    val id: String = "student_1",
    val name: String = "Ayan",
    val avatar: StudentAvatar = StudentAvatar.EXPLORER,
    val schoolClass: SchoolClass = SchoolClass.CLASS_1,
    val stars: Int = 24,
    val points: Int = 340,
    val streakDays: Int = 5,
    val dailyTargetMinutes: Int = 20,
    val minutesToday: Int = 14,
    val weeklyMinutes: List<Int> = listOf(15, 22, 18, 25, 20, 12, 14),
    val lastSubjectTitle: String = "Mathematics",
    val lastLessonTitle: String = "Fun with Number Bonds",
    val lastLessonProgress: Int = 65,
    val curriculumTrack: CurriculumTrack = CurriculumTrack.ISLAMIC_STUDIES,
    val ttsEnabled: Boolean = true
) {
    val dailyProgressPercent: Int
        get() = if (dailyTargetMinutes <= 0) 0 else ((minutesToday * 100f) / dailyTargetMinutes).toInt().coerceIn(0, 100)
}
