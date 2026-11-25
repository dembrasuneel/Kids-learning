package com.example.data.repository

import android.content.Context
import com.example.data.model.CurriculumTrack
import com.example.data.model.SchoolClass
import com.example.data.model.StudentAvatar
import com.example.data.model.StudentProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardRepository(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _profile = MutableStateFlow(loadInitialProfile())
    val profile: StateFlow<StudentProfile> = _profile.asStateFlow()

    private fun loadInitialProfile(): StudentProfile {
        val name = prefs.getString(KEY_NAME, "Ayan") ?: "Ayan"
        val avatar = StudentAvatar.fromId(prefs.getString(KEY_AVATAR, StudentAvatar.EXPLORER.id))
        val schoolClass = SchoolClass.fromId(prefs.getString(KEY_CLASS, SchoolClass.CLASS_1.id))
        val stars = prefs.getInt(KEY_STARS, 24)
        val points = prefs.getInt(KEY_POINTS, 340)
        val streak = prefs.getInt(KEY_STREAK, 5)
        val targetMins = prefs.getInt(KEY_TARGET_MINS, 20)
        val minsToday = prefs.getInt(KEY_MINS_TODAY, 14)
        val trackName = prefs.getString(KEY_TRACK, CurriculumTrack.ISLAMIC_STUDIES.name)
        val track = CurriculumTrack.entries.firstOrNull { it.name == trackName } ?: CurriculumTrack.ISLAMIC_STUDIES
        val ttsEnabled = prefs.getBoolean(KEY_TTS_ENABLED, true)

        return StudentProfile(
            name = name,
            avatar = avatar,
            schoolClass = schoolClass,
            stars = stars,
            points = points,
            streakDays = streak,
            dailyTargetMinutes = targetMins,
            minutesToday = minsToday,
            weeklyMinutes = listOf(15, 22, 18, 25, 20, 12, minsToday),
            lastSubjectTitle = defaultSubjectFor(schoolClass),
            lastLessonTitle = defaultLessonFor(schoolClass),
            lastLessonProgress = 65,
            curriculumTrack = track,
            ttsEnabled = ttsEnabled
        )
    }

    fun selectClass(schoolClass: SchoolClass) {
        prefs.edit().putString(KEY_CLASS, schoolClass.id).apply()
        _profile.update {
            it.copy(
                schoolClass = schoolClass,
                lastSubjectTitle = defaultSubjectFor(schoolClass),
                lastLessonTitle = defaultLessonFor(schoolClass)
            )
        }
    }

    fun updateStudentName(newName: String) {
        val trimmed = newName.trim()
        if (trimmed.isEmpty()) return
        prefs.edit().putString(KEY_NAME, trimmed).apply()
        _profile.update { it.copy(name = trimmed) }
    }

    fun selectAvatar(avatar: StudentAvatar) {
        prefs.edit().putString(KEY_AVATAR, avatar.id).apply()
        _profile.update { it.copy(avatar = avatar) }
    }

    fun setDailyTargetMinutes(minutes: Int) {
        val clamped = minutes.coerceIn(10, 60)
        prefs.edit().putInt(KEY_TARGET_MINS, clamped).apply()
        _profile.update { it.copy(dailyTargetMinutes = clamped) }
    }

    fun setCurriculumTrack(track: CurriculumTrack) {
        prefs.edit().putString(KEY_TRACK, track.name).apply()
        _profile.update { it.copy(curriculumTrack = track) }
    }

    fun setTtsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_TTS_ENABLED, enabled).apply()
        _profile.update { it.copy(ttsEnabled = enabled) }
    }

    fun awardQuizCompletion(pointsEarned: Int = 15, starsEarned: Int = 1, minutesAdded: Int = 3) {
        _profile.update { current ->
            val newStars = current.stars + starsEarned
            val newPoints = current.points + pointsEarned
            val newMins = current.minutesToday + minutesAdded
            val updatedWeekly = current.weeklyMinutes.toMutableList().apply {
                if (isNotEmpty()) this[lastIndex] = newMins
            }
            prefs.edit()
                .putInt(KEY_STARS, newStars)
                .putInt(KEY_POINTS, newPoints)
                .putInt(KEY_MINS_TODAY, newMins)
                .apply()
            current.copy(
                stars = newStars,
                points = newPoints,
                minutesToday = newMins,
                weeklyMinutes = updatedWeekly
            )
        }
    }

    private fun defaultSubjectFor(schoolClass: SchoolClass): String = when (schoolClass) {
        SchoolClass.PREP_1, SchoolClass.PREP_2 -> "Phonics"
        SchoolClass.CLASS_1, SchoolClass.CLASS_2 -> "Mathematics"
        SchoolClass.CLASS_3, SchoolClass.CLASS_4 -> "Science"
        SchoolClass.CLASS_5, SchoolClass.CLASS_6 -> "General Science"
    }

    private fun defaultLessonFor(schoolClass: SchoolClass): String = when (schoolClass) {
        SchoolClass.PREP_1 -> "Friendly Letter Sounds A–E"
        SchoolClass.PREP_2 -> "Rhyming Words & Blends"
        SchoolClass.CLASS_1 -> "Fun with Number Bonds"
        SchoolClass.CLASS_2 -> "Skip Counting & Tables"
        SchoolClass.CLASS_3 -> "Journey of the Water Cycle"
        SchoolClass.CLASS_4 -> "Exploring Our Solar System"
        SchoolClass.CLASS_5 -> "Simple Electric Circuits"
        SchoolClass.CLASS_6 -> "Light, Shadows & Reflections"
    }

    companion object {
        private const val PREFS_NAME = "little_genius_prefs"
        private const val KEY_NAME = "student_name"
        private const val KEY_AVATAR = "student_avatar"
        private const val KEY_CLASS = "student_class"
        private const val KEY_STARS = "student_stars"
        private const val KEY_POINTS = "student_points"
        private const val KEY_STREAK = "student_streak"
        private const val KEY_TARGET_MINS = "daily_target_mins"
        private const val KEY_MINS_TODAY = "minutes_today"
        private const val KEY_TRACK = "curriculum_track"
        private const val KEY_TTS_ENABLED = "tts_enabled"
    }
}
