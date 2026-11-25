package com.example.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.R
import com.example.data.model.StudentAvatar
import com.example.databinding.FragmentProfileBinding
import com.example.ui.common.LittleGeniusModals
import com.example.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private var suppressSwitchCallback = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutAvatarExplorer.setOnClickListener {
            viewModel.selectAvatar(StudentAvatar.EXPLORER)
        }
        binding.layoutAvatarAstronaut.setOnClickListener {
            viewModel.selectAvatar(StudentAvatar.ASTRONAUT)
        }

        binding.btnSaveProfileName.setOnClickListener {
            val newName = binding.etProfileName.text?.toString().orEmpty().trim()
            if (newName.isNotEmpty()) {
                viewModel.updateStudentName(newName)
                Snackbar.make(binding.root, "Student name updated to $newName", Snackbar.LENGTH_SHORT).show()
            }
        }

        val openClassPicker = View.OnClickListener {
            LittleGeniusModals.showClassSelector(
                requireContext(),
                viewModel.uiState.value.profile.schoolClass
            ) { selected ->
                viewModel.selectClass(selected)
            }
        }
        binding.cardProfileClass.setOnClickListener(openClassPicker)
        binding.btnProfileChangeClass.setOnClickListener(openClassPicker)

        binding.switchProfileTts.setOnCheckedChangeListener { _, isChecked ->
            if (!suppressSwitchCallback) {
                viewModel.setTtsEnabled(isChecked)
            }
        }

        val openParentGate = View.OnClickListener {
            LittleGeniusModals.showParentGateDialog(requireContext()) {
                findNavController().navigate(R.id.parentDashboardFragment)
            }
        }
        binding.cardProfileParent.setOnClickListener(openParentGate)
        binding.btnProfileOpenParent.setOnClickListener(openParentGate)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val p = state.profile
                    if (binding.etProfileName.text.isNullOrEmpty()) {
                        binding.etProfileName.setText(p.name)
                    }
                    binding.tvProfileActiveClass.text =
                        "${p.schoolClass.displayName} • ${p.schoolClass.ageRange}"

                    suppressSwitchCallback = true
                    binding.switchProfileTts.isChecked = p.ttsEnabled
                    suppressSwitchCallback = false

                    val activeColor = ContextCompat.getColorStateList(requireContext(), R.color.lg_primary)
                    val idleColor = ContextCompat.getColorStateList(requireContext(), R.color.lg_outline)
                    binding.ivAvatarExplorer.strokeColor =
                        if (p.avatar == StudentAvatar.EXPLORER) activeColor else idleColor
                    binding.ivAvatarAstronaut.strokeColor =
                        if (p.avatar == StudentAvatar.ASTRONAUT) activeColor else idleColor
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
