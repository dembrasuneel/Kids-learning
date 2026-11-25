package com.example.ui.common

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.R
import com.example.databinding.LayoutWeeklyChartBinding
import kotlin.math.max

object WeeklyChartBinder {

    fun bind(
        binding: LayoutWeeklyChartBinding,
        weeklyMinutes: List<Int>,
        dailyGoalMinutes: Int
    ) {
        val ctx = binding.root.context
        val total = weeklyMinutes.sum()
        binding.tvWeeklyTotal.text = "$total mins learned this week"

        val todayMins = weeklyMinutes.lastOrNull() ?: 0
        if (todayMins >= dailyGoalMinutes) {
            binding.tvWeeklyBadge.text = "Goal Met!"
            binding.tvWeeklyBadge.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.pastel_mint_bg))
            binding.tvWeeklyBadge.setTextColor(ContextCompat.getColor(ctx, R.color.pastel_mint_fg))
        } else {
            binding.tvWeeklyBadge.text = "On Track"
            binding.tvWeeklyBadge.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.lg_primary_container))
            binding.tvWeeklyBadge.setTextColor(ContextCompat.getColor(ctx, R.color.lg_primary))
        }

        val bars: List<Pair<View, TextView>> = listOf(
            binding.barDay0 to binding.tvBarVal0,
            binding.barDay1 to binding.tvBarVal1,
            binding.barDay2 to binding.tvBarVal2,
            binding.barDay3 to binding.tvBarVal3,
            binding.barDay4 to binding.tvBarVal4,
            binding.barDay5 to binding.tvBarVal5,
            binding.barDay6 to binding.tvBarVal6
        )

        val maxVal = max(dailyGoalMinutes, weeklyMinutes.maxOrNull() ?: 25).coerceAtLeast(1)
        val density = ctx.resources.displayMetrics.density
        val maxHeightPx = (78 * density).toInt()
        val minHeightPx = (14 * density).toInt()

        bars.forEachIndexed { index, (barView, labelView) ->
            val mins = weeklyMinutes.getOrElse(index) { 0 }
            labelView.text = mins.toString()
            val targetHeight = ((mins.toFloat() / maxVal) * maxHeightPx).toInt().coerceIn(minHeightPx, maxHeightPx)
            val lp = barView.layoutParams
            lp.height = targetHeight
            barView.layoutParams = lp
        }
    }
}
