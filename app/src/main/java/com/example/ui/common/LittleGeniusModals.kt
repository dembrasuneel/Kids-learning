package com.example.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.example.R
import com.example.data.model.DailyQuizQuestion
import com.example.data.model.SchoolClass
import com.example.data.model.Subject
import com.example.databinding.BottomSheetClassSelectorBinding
import com.example.databinding.BottomSheetSubjectPreviewBinding
import com.example.databinding.DialogDailyQuizBinding
import com.example.databinding.DialogParentGateBinding
import com.example.ui.adapter.ClassSelectorAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random

object LittleGeniusModals {

    fun showClassSelector(
        context: Context,
        currentClass: SchoolClass,
        onClassSelected: (SchoolClass) -> Unit
    ) {
        val dialog = BottomSheetDialog(context)
        val binding = BottomSheetClassSelectorBinding.inflate(LayoutInflater.from(context))
        binding.rvClassOptions.adapter = ClassSelectorAdapter(
            classes = SchoolClass.entries,
            selectedClass = currentClass
        ) { chosen ->
            onClassSelected(chosen)
            dialog.dismiss()
        }
        dialog.setContentView(binding.root)
        dialog.show()
    }

    fun showSubjectPreview(
        context: Context,
        subject: Subject,
        onSpeak: (String) -> Unit
    ) {
        val dialog = BottomSheetDialog(context)
        val binding = BottomSheetSubjectPreviewBinding.inflate(LayoutInflater.from(context))
        binding.tvSheetSubjectTitle.text = subject.title
        binding.tvSheetSubjectSubtitle.text = subject.subtitle

        if (!subject.urduSample.isNullOrBlank()) {
            binding.tvSheetUrduSample.visibility = View.VISIBLE
            binding.tvSheetUrduSample.text = subject.urduSample
        } else {
            binding.tvSheetUrduSample.visibility = View.GONE
        }

        binding.tvSheetTopics.text = subject.sampleTopics.joinToString("\n") { "• $it" }
        binding.btnSheetListen.setOnClickListener {
            onSpeak("${subject.title}. ${subject.subtitle}. Topics include ${subject.sampleTopics.joinToString(", ")}.")
        }
        binding.btnSheetClose.setOnClickListener { dialog.dismiss() }

        dialog.setContentView(binding.root)
        dialog.show()
    }

    fun showDailyQuizDialog(
        context: Context,
        quiz: DailyQuizQuestion,
        onCorrectAnswer: () -> Unit
    ) {
        val binding = DialogDailyQuizBinding.inflate(LayoutInflater.from(context))
        val dialog = MaterialAlertDialogBuilder(context)
            .setView(binding.root)
            .create()

        binding.tvQuizSubjectBadge.text = quiz.subjectLabel
        binding.tvQuizQuestion.text = quiz.question
        val radios = listOf(binding.rbOption0, binding.rbOption1, binding.rbOption2, binding.rbOption3)
        radios.forEachIndexed { idx, rb ->
            val opt = quiz.options.getOrNull(idx)
            if (opt != null) {
                rb.visibility = View.VISIBLE
                rb.text = opt
            } else {
                rb.visibility = View.GONE
            }
        }

        var answeredCorrectly = false

        binding.btnQuizSubmit.setOnClickListener {
            val checkedId = binding.rgQuizOptions.checkedRadioButtonId
            val selectedIndex = radios.indexOfFirst { it.id == checkedId }
            if (selectedIndex == -1) {
                binding.tvQuizFeedback.visibility = View.VISIBLE
                binding.tvQuizFeedback.setTextColor(ContextCompat.getColor(context, R.color.lg_secondary))
                binding.tvQuizFeedback.text = "Please pick an answer first!"
                return@setOnClickListener
            }

            binding.tvQuizFeedback.visibility = View.VISIBLE
            if (selectedIndex == quiz.correctIndex) {
                binding.tvQuizFeedback.setTextColor(ContextCompat.getColor(context, R.color.success_green))
                binding.tvQuizFeedback.text = "${context.getString(R.string.quiz_correct)}\n${quiz.explanation}"
                if (!answeredCorrectly) {
                    answeredCorrectly = true
                    onCorrectAnswer()
                }
                binding.btnQuizSubmit.isEnabled = false
            } else {
                binding.tvQuizFeedback.setTextColor(ContextCompat.getColor(context, R.color.error_red))
                binding.tvQuizFeedback.text = context.getString(R.string.quiz_wrong)
            }
        }

        binding.btnQuizClose.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun showParentGateDialog(
        context: Context,
        onUnlocked: () -> Unit
    ) {
        val binding = DialogParentGateBinding.inflate(LayoutInflater.from(context))
        val dialog = MaterialAlertDialogBuilder(context)
            .setView(binding.root)
            .create()

        val a = Random.nextInt(4, 10)
        val b = Random.nextInt(3, 9)
        val expected = a * b
        binding.tvParentGateChallenge.text = "$a × $b = ?"

        binding.btnParentGateCancel.setOnClickListener { dialog.dismiss() }
        binding.btnParentGateUnlock.setOnClickListener {
            val input = binding.etParentGateAnswer.text?.toString()?.trim()?.toIntOrNull()
            if (input == expected) {
                dialog.dismiss()
                onUnlocked()
            } else {
                binding.tilParentGate.error = context.getString(R.string.parent_gate_wrong)
            }
        }
        dialog.show()
    }
}
