package com.example.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.data.model.ClassTier
import com.example.data.model.DailyQuizQuestion
import com.example.databinding.FragmentGamesBinding
import com.example.ui.common.LittleGeniusModals
import com.example.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random
import kotlinx.coroutines.launch

class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGamesPlayQuiz.setOnClickListener {
            val quiz = viewModel.uiState.value.dailyQuiz ?: return@setOnClickListener
            LittleGeniusModals.showDailyQuizDialog(requireContext(), quiz) {
                viewModel.completeDailyQuiz()
            }
        }

        binding.cardGameMathSprint.setOnClickListener {
            val mathQuiz = generateQuickMathQuestion(viewModel.uiState.value.profile.schoolClass.tier)
            LittleGeniusModals.showDailyQuizDialog(requireContext(), mathQuiz) {
                viewModel.completeDailyQuiz()
            }
        }

        binding.cardGameWordListen.setOnClickListener {
            val tier = viewModel.uiState.value.profile.schoolClass.tier
            val phrase = when (tier) {
                ClassTier.PREP -> "A is for Apple, B is for Ball, C is for Cat!"
                ClassTier.LOWER_PRIMARY -> "Practice spelling: Sunshine, Rainbow, Library!"
                ClassTier.MIDDLE_PRIMARY -> "Vocabulary spotlight: Curiosity means eager to learn!"
                ClassTier.UPPER_PRIMARY -> "Word power: Perseverance helps you master big goals!"
            }
            viewModel.speakIfEnabled(phrase)
            Snackbar.make(binding.root, phrase, Snackbar.LENGTH_LONG).show()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val quiz = state.dailyQuiz
                    if (quiz != null) {
                        binding.tvGamesQuizPreview.text =
                            "${state.profile.schoolClass.displayName} Challenge • ${quiz.subjectLabel}: ${quiz.question}"
                    }
                }
            }
        }
    }

    private fun generateQuickMathQuestion(tier: ClassTier): DailyQuizQuestion {
        val (a, b, isMultiply) = when (tier) {
            ClassTier.PREP -> Triple(Random.nextInt(1, 6), Random.nextInt(1, 5), false)
            ClassTier.LOWER_PRIMARY -> Triple(Random.nextInt(5, 16), Random.nextInt(3, 12), false)
            ClassTier.MIDDLE_PRIMARY -> Triple(Random.nextInt(3, 10), Random.nextInt(3, 10), true)
            ClassTier.UPPER_PRIMARY -> Triple(Random.nextInt(7, 16), Random.nextInt(6, 13), true)
        }
        val answer = if (isMultiply) a * b else a + b
        val symbol = if (isMultiply) "×" else "+"
        val wrongSet = mutableSetOf<Int>()
        while (wrongSet.size < 3) {
            val candidate = (answer + Random.nextInt(-5, 7)).coerceAtLeast(1)
            if (candidate != answer) wrongSet.add(candidate)
        }
        val options = (wrongSet + answer).shuffled()
        return DailyQuizQuestion(
            id = "sprint_${System.currentTimeMillis()}",
            subjectLabel = "Math Sprint",
            question = "What is $a $symbol $b?",
            options = options.map { it.toString() },
            correctIndex = options.indexOf(answer),
            explanation = "$a $symbol $b = $answer. Great mental math!"
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
