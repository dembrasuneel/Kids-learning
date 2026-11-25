package com.example.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.Subject
import com.example.databinding.ItemSubjectCardBinding

class SubjectCardAdapter(
    private val onSubjectClick: (Subject) -> Unit
) : ListAdapter<Subject, SubjectCardAdapter.SubjectViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SubjectViewHolder(
        private val binding: ItemSubjectCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: Subject) {
            val ctx = binding.root.context
            val bgColor = ContextCompat.getColor(ctx, subject.bgColorRes)
            val accentColor = ContextCompat.getColor(ctx, subject.accentColorRes)

            binding.cardSubject.setCardBackgroundColor(bgColor)
            binding.ivSubjectIcon.setImageResource(subject.iconRes)
            ImageViewCompat.setImageTintList(binding.ivSubjectIcon, ColorStateList.valueOf(accentColor))

            binding.tvSubjectTitle.text = subject.title
            binding.tvSubjectChapters.text = "${subject.completedChapters} of ${subject.chapterCount} chapters"
            binding.progressSubject.setIndicatorColor(accentColor)
            binding.progressSubject.setProgressCompat(subject.progressPercent, true)

            binding.cardSubject.setOnClickListener { onSubjectClick(subject) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean =
            oldItem == newItem
    }
}
