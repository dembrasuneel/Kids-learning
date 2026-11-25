package com.example.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.R
import com.example.data.model.SubjectCategory
import com.example.databinding.FragmentLearnBinding
import com.example.ui.adapter.SubjectRowAdapter
import com.example.ui.common.LittleGeniusModals
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: SubjectRowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SubjectRowAdapter { subject ->
            LittleGeniusModals.showSubjectPreview(requireContext(), subject) { text ->
                viewModel.speakIfEnabled(text)
            }
        }
        binding.rvLearnSubjects.adapter = adapter

        binding.chipLearnClass.setOnClickListener {
            LittleGeniusModals.showClassSelector(
                requireContext(),
                viewModel.uiState.value.profile.schoolClass
            ) { selected ->
                viewModel.selectClass(selected)
            }
        }

        binding.etLearnSearch.doAfterTextChanged { text ->
            viewModel.setSearchQuery(text?.toString().orEmpty())
        }

        binding.chipGroupLearnFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull()
            val category = when (checkedId) {
                R.id.chip_filter_languages -> SubjectCategory.LANGUAGES
                R.id.chip_filter_stem -> SubjectCategory.STEM
                R.id.chip_filter_gk -> SubjectCategory.GK_VALUES
                else -> null
            }
            viewModel.setCategoryFilter(category)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val schoolClass = state.profile.schoolClass
                    binding.chipLearnClass.text = schoolClass.displayName
                    binding.tvLearnClassSubtitle.text =
                        "${schoolClass.displayName} • ${state.subjects.size} subjects available"

                    adapter.submitList(state.filteredSubjects)
                    val isEmpty = state.filteredSubjects.isEmpty()
                    binding.layoutLearnEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
                    binding.rvLearnSubjects.visibility = if (isEmpty) View.GONE else View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
