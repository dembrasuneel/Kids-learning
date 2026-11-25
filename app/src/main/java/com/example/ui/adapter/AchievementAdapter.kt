package com.example.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.data.model.Achievement
import com.example.databinding.ItemAchievementCardBinding

class AchievementAdapter :
    ListAdapter<Achievement, AchievementAdapter.BadgeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val binding = ItemAchievementCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BadgeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BadgeViewHolder(
        private val binding: ItemAchievementCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Achievement) {
            val ctx = binding.root.context
            binding.tvBadgeTitle.text = item.title
            binding.tvBadgeDesc.text = item.description
            binding.ivBadgeIcon.setImageResource(item.iconRes)

            if (item.isUnlocked) {
                binding.cardAchievement.setCardBackgroundColor(
                    ContextCompat.getColor(ctx, R.color.pastel_lemon_bg)
                )
                ImageViewCompat.setImageTintList(
                    binding.ivBadgeIcon,
                    ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.star_gold))
                )
                binding.tvBadgeStatus.text = "Unlocked"
                binding.tvBadgeStatus.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(ctx, R.color.pastel_mint_bg)
                )
                binding.tvBadgeStatus.setTextColor(
                    ContextCompat.getColor(ctx, R.color.pastel_mint_fg)
                )
            } else {
                binding.cardAchievement.setCardBackgroundColor(
                    ContextCompat.getColor(ctx, R.color.lg_surface_variant)
                )
                ImageViewCompat.setImageTintList(
                    binding.ivBadgeIcon,
                    ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.lg_on_surface_variant))
                )
                binding.tvBadgeStatus.text = "+${item.pointsReward} pts"
                binding.tvBadgeStatus.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(ctx, R.color.lg_surface)
                )
                binding.tvBadgeStatus.setTextColor(
                    ContextCompat.getColor(ctx, R.color.lg_on_surface_variant)
                )
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Achievement>() {
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean =
            oldItem == newItem
    }
}
