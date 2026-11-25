package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.LittleGeniusApp
import com.example.data.model.Achievement
import com.example.data.model.CurriculumTrack
import com.example.data.model.DailyQuizQuestion
import com.example.data.model.SchoolClass
import com.example.data.model.StudentAvatar
import com.example.data.model.StudentProfile
import com.example.data.model.Subject
import com.example.data.model.SubjectCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class DashboardUiState(
    val profile: StudentProfile = StudentProfile(),
    val subjects: List<Subject> = emptyList(),
    val filteredSubjects: List<Subject> = emptyList(),
    val selectedCategoryFilter: SubjectCategory? = null,
    val searchQuery: String = "",
    val achievements: List<Achievement> = emptyList(),
    val dailyQuiz: DailyQuizQuestion? = null
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as LittleGeniusApp
    private val curriculumRepo = app.curriculumRepository
    private val dashboardRepo = app.dashboardRepository

    private val filterCategory = MutableStateFlow<SubjectCategory?>(null)
    private val searchQuery = MutableStateFlow("")

    val uiState: StateFlow<DashboardUiState> = combine(
        dashboardRepo.profile,
        filterCategory,
        searchQuery
    ) { profile, category, query ->
        val subjects = curriculumRepo.getSubjectsForClass(profile.schoolClass, profile.curriculumTrack)
        val filtered = subjects.filter { subject ->
            val matchesCategory = category == null || subject.category == category
            val matchesQuery = query.isBlank() ||
                subject.title.contains(query, ignoreCase = true) ||
                subject.subtitle.contains(query, ignoreCase = true) ||
                subject.sampleTopics.any { it.contains(query, ignoreCase = true) }
            matchesCategory && matchesQuery
        }
        val achievements = curriculumRepo.getAchievements(
            stars = profile.stars,
            streakDays = profile.streakDays,
            points = profile.points
        )
        val dailyQuiz = curriculumRepo.getDailyQuizForClass(profile.schoolClass)

        DashboardUiState(
            profile = profile,
            subjects = subjects,
            filteredSubjects = filtered,
            selectedCategoryFilter = category,
            searchQuery = query,
            achievements = achievements,
            dailyQuiz = dailyQuiz
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState()
    )

    fun selectClass(schoolClass: SchoolClass) {
        dashboardRepo.selectClass(schoolClass)
    }

    fun updateStudentName(name: String) {
        dashboardRepo.updateStudentName(name)
    }

    fun selectAvatar(avatar: StudentAvatar) {
        dashboardRepo.selectAvatar(avatar)
    }

    fun setDailyTargetMinutes(minutes: Int) {
        dashboardRepo.setDailyTargetMinutes(minutes)
    }

    fun setCurriculumTrack(track: CurriculumTrack) {
        dashboardRepo.setCurriculumTrack(track)
    }

    fun setTtsEnabled(enabled: Boolean) {
        dashboardRepo.setTtsEnabled(enabled)
    }

    fun setCategoryFilter(category: SubjectCategory?) {
        filterCategory.value = category
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun completeDailyQuiz() {
        dashboardRepo.awardQuizCompletion(pointsEarned = 15, starsEarned = 1, minutesAdded = 3)
    }

    fun speakIfEnabled(text: String) {
        if (uiState.value.profile.ttsEnabled) {
            app.speechHelper.speak(text)
        }
    }
}
