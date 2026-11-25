package com.example.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.databinding.FragmentProgressBinding
import com.example.ui.adapter.AchievementAdapter
import com.example.ui.common.WeeklyChartBinder
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private val achievementAdapter = AchievementAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProgressAchievements.adapter = achievementAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val p = state.profile
                    binding.tvProgressStatsHeadline.text = "${p.stars} Stars • ${p.points} Points"
                    binding.tvProgressStreakDetail.text =
                        "${p.streakDays}-day learning streak active in ${p.schoolClass.displayName}!"

                    WeeklyChartBinder.bind(
                        binding = binding.includeProgressWeeklyChart,
                        weeklyMinutes = p.weeklyMinutes,
                        dailyGoalMinutes = p.dailyTargetMinutes
                    )

                    achievementAdapter.submitList(state.achievements)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
