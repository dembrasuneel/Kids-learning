package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.data.model.SchoolClass
import com.example.databinding.ItemClassOptionBinding

class ClassSelectorAdapter(
    private val classes: List<SchoolClass>,
    private val selectedClass: SchoolClass,
    private val onClassSelected: (SchoolClass) -> Unit
) : RecyclerView.Adapter<ClassSelectorAdapter.ClassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val binding = ItemClassOptionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ClassViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bind(classes[position])
    }

    override fun getItemCount(): Int = classes.size

    inner class ClassViewHolder(
        private val binding: ItemClassOptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(schoolClass: SchoolClass) {
            val isSelected = schoolClass == selectedClass
            val ctx = binding.root.context

            binding.tvClassName.text = "${schoolClass.displayName} • ${schoolClass.ageRange}"
            binding.tvClassTagline.text = schoolClass.tagline
            binding.ivClassSelected.visibility = if (isSelected) View.VISIBLE else View.GONE

            binding.cardClassOption.strokeWidth = if (isSelected) 4 else 2
            binding.cardClassOption.strokeColor = ContextCompat.getColor(
                ctx,
                if (isSelected) R.color.lg_primary else R.color.lg_outline
            )
            binding.cardClassOption.setCardBackgroundColor(
                ContextCompat.getColor(
                    ctx,
                    if (isSelected) R.color.lg_primary_container else R.color.lg_surface
                )
            )

            binding.cardClassOption.setOnClickListener { onClassSelected(schoolClass) }
        }
    }
}
