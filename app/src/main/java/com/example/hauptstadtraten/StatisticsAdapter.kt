package com.example.hauptstadtraten

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hauptstadtraten.databinding.ItemCountryStatsBinding

class StatisticsAdapter : ListAdapter<CountryStats, StatisticsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCountryStatsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemCountryStatsBinding) : 
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stats: CountryStats) {
            binding.flagTextView.text = stats.country.flag
            binding.countryNameTextView.text = stats.country.name
            binding.statsTextView.text = binding.root.context.getString(
                R.string.country_stats,
                stats.correctCount,
                stats.wrongCount
            )
            
            val percentage = stats.correctPercentage.toInt()
            binding.percentageTextView.text = "$percentage%"
            binding.progressBar.max = 100
            binding.progressBar.progress = percentage
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CountryStats>() {
        override fun areItemsTheSame(oldItem: CountryStats, newItem: CountryStats): Boolean {
            return oldItem.country.name == newItem.country.name
        }

        override fun areContentsTheSame(oldItem: CountryStats, newItem: CountryStats): Boolean {
            return oldItem == newItem
        }
    }
}
