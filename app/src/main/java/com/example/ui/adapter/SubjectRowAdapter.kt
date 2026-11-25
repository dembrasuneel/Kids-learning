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
import com.example.databinding.ItemSubjectRowBinding

class SubjectRowAdapter(
    private val onSubjectClick: (Subject) -> Unit
) : ListAdapter<Subject, SubjectRowAdapter.RowViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val binding = ItemSubjectRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RowViewHolder(
        private val binding: ItemSubjectRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: Subject) {
            val ctx = binding.root.context
            val bgColor = ContextCompat.getColor(ctx, subject.bgColorRes)
            val accentColor = ContextCompat.getColor(ctx, subject.accentColorRes)

            binding.rowIconBg.backgroundTintList = ColorStateList.valueOf(bgColor)
            binding.ivRowIcon.setImageResource(subject.iconRes)
            ImageViewCompat.setImageTintList(binding.ivRowIcon, ColorStateList.valueOf(accentColor))

            binding.tvRowTitle.text = subject.title
            binding.tvRowSubtitle.text = subject.subtitle
            binding.progressRow.setIndicatorColor(accentColor)
            binding.progressRow.setProgressCompat(subject.progressPercent, true)
            binding.tvRowPercent.text = "${subject.progressPercent}%"

            binding.cardSubjectRow.setOnClickListener { onSubjectClick(subject) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean =
            oldItem == newItem
    }
}
