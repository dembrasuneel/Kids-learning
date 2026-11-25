package com.example.ui.parent

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
import com.example.data.model.CurriculumTrack
import com.example.databinding.FragmentParentDashboardBinding
import com.example.ui.adapter.SubjectRowAdapter
import com.example.ui.common.LittleGeniusModals
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class ParentDashboardFragment : Fragment() {

    private var _binding: FragmentParentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var subjectAdapter: SubjectRowAdapter
    private var suppressTrackUpdate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarParent.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        subjectAdapter = SubjectRowAdapter { subject ->
            LittleGeniusModals.showSubjectPreview(requireContext(), subject) { text ->
                viewModel.speakIfEnabled(text)
            }
        }
        binding.rvParentSubjects.adapter = subjectAdapter

        binding.sliderParentDailyGoal.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                viewModel.setDailyTargetMinutes(value.toInt())
            }
        }

        binding.chipGroupCurriculumTrack.setOnCheckedStateChangeListener { _, checkedIds ->
            if (suppressTrackUpdate) return@setOnCheckedStateChangeListener
            val track = when (checkedIds.firstOrNull()) {
                R.id.chip_track_ethics -> CurriculumTrack.ETHICS
                else -> CurriculumTrack.ISLAMIC_STUDIES
            }
            viewModel.setCurriculumTrack(track)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val p = state.profile
                    binding.tvParentStudentSummary.text =
                        "${p.name} • ${p.schoolClass.displayName} (${p.schoolClass.ageRange})"
                    binding.tvParentTimeSummary.text =
                        "${p.minutesToday} mins today • ${p.weeklyMinutes.sum()} mins this week • ${p.streakDays} day streak"

                    binding.tvParentGoalValue.text = "${p.dailyTargetMinutes} mins"
                    if (binding.sliderParentDailyGoal.value.toInt() != p.dailyTargetMinutes) {
                        binding.sliderParentDailyGoal.value = p.dailyTargetMinutes.toFloat()
                    }

                    suppressTrackUpdate = true
                    val chipId = if (p.curriculumTrack == CurriculumTrack.ETHICS) {
                        R.id.chip_track_ethics
                    } else {
                        R.id.chip_track_islamic
                    }
                    binding.chipGroupCurriculumTrack.check(chipId)
                    suppressTrackUpdate = false

                    val focusList = state.subjects
                        .sortedBy { it.progressPercent }
                        .take(2)
                        .joinToString("\n") { "• ${it.title} (${it.progressPercent}% complete)" }
                    binding.tvParentFocusTopics.text = focusList

                    subjectAdapter.submitList(state.subjects)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
