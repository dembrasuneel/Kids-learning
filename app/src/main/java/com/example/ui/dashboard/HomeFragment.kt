package com.example.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.R
import com.example.databinding.FragmentHomeBinding
import com.example.ui.adapter.SubjectCardAdapter
import com.example.ui.common.LittleGeniusModals
import com.example.ui.common.WeeklyChartBinder
import com.example.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var subjectAdapter: SubjectCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subjectAdapter = SubjectCardAdapter { subject ->
            LittleGeniusModals.showSubjectPreview(requireContext(), subject) { text ->
                viewModel.speakIfEnabled(text)
            }
        }
        binding.rvDashboardSubjects.adapter = subjectAdapter

        setupClicks()
        observeState()
    }

    private fun setupClicks() {
        binding.ivHomeAvatar.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.chipClassSelector.setOnClickListener {
            val currentClass = viewModel.uiState.value.profile.schoolClass
            LittleGeniusModals.showClassSelector(requireContext(), currentClass) { selected ->
                viewModel.selectClass(selected)
            }
        }

        binding.btnSpeakWelcome.setOnClickListener {
            val profile = viewModel.uiState.value.profile
            val message = "Hello ${profile.name}! You are in ${profile.schoolClass.displayName}. " +
                "You have earned ${profile.stars} stars and ${profile.points} points. Let's keep learning!"
            viewModel.speakIfEnabled(message)
            Snackbar.make(binding.root, "Reading welcome message aloud", Snackbar.LENGTH_SHORT).show()
        }

        binding.btnParentZone.setOnClickListener {
            LittleGeniusModals.showParentGateDialog(requireContext()) {
                findNavController().navigate(R.id.parentDashboardFragment)
            }
        }

        binding.btnContinueLearning.setOnClickListener {
            findNavController().navigate(R.id.learnFragment)
        }

        binding.btnSeeAllSubjects.setOnClickListener {
            findNavController().navigate(R.id.learnFragment)
        }

        binding.cardDailyQuiz.setOnClickListener {
            val quiz = viewModel.uiState.value.dailyQuiz ?: return@setOnClickListener
            LittleGeniusModals.showDailyQuizDialog(requireContext(), quiz) {
                viewModel.completeDailyQuiz()
            }
        }

        binding.cardEducationalGames.setOnClickListener {
            findNavController().navigate(R.id.gamesFragment)
        }

        binding.cardHomeRewards.setOnClickListener {
            findNavController().navigate(R.id.progressFragment)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val profile = state.profile

                    binding.tvHomeGreeting.text = greetingForTimeOfDay()
                    binding.tvHomeStudentName.text = profile.name
                    binding.ivHomeAvatar.setImageResource(profile.avatar.drawableRes)

                    binding.chipClassSelector.text = profile.schoolClass.displayName
                    binding.tvHomeStars.text = profile.stars.toString()
                    binding.tvHomeStreak.text = "${profile.streakDays}d"
                    binding.tvHomePoints.text = profile.points.toString()

                    binding.tvContinueSubject.text = profile.lastSubjectTitle
                    binding.tvContinueLesson.text = profile.lastLessonTitle
                    binding.progressContinueLesson.setProgressCompat(profile.lastLessonProgress, true)
                    binding.tvContinuePercent.text = "${profile.lastLessonProgress}%"

                    binding.tvDailyTargetValue.text = "${profile.minutesToday} / ${profile.dailyTargetMinutes} mins"
                    binding.progressDailyTarget.setProgressCompat(profile.dailyProgressPercent, true)
                    val remaining = (profile.dailyTargetMinutes - profile.minutesToday).coerceAtLeast(0)
                    binding.tvDailyTargetHint.text = if (remaining == 0) {
                        "Amazing! You completed today's learning goal!"
                    } else {
                        "Just $remaining more minutes to hit today's goal!"
                    }

                    binding.tvSubjectsHeader.text = "My Subjects (${state.subjects.size})"
                    subjectAdapter.submitList(state.subjects)

                    WeeklyChartBinder.bind(
                        binding = binding.includeWeeklyChart,
                        weeklyMinutes = profile.weeklyMinutes,
                        dailyGoalMinutes = profile.dailyTargetMinutes
                    )

                    val unlockedCount = state.achievements.count { it.isUnlocked }
                    binding.tvHomeBadgesSummary.text =
                        "$unlockedCount of ${state.achievements.size} badges unlocked • Keep shining!"
                }
            }
        }
    }

    private fun greetingForTimeOfDay(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when {
            hour < 12 -> getString(R.string.dashboard_greeting_morning)
            hour < 17 -> getString(R.string.dashboard_greeting_afternoon)
            else -> getString(R.string.dashboard_greeting_evening)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
