package com.example.hauptstadtraten

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hauptstadtraten.databinding.ItemContinentBinding

class ContinentAdapter(
    private val settings: GameSettings
) : ListAdapter<ContinentData, ContinentAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContinentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, settings)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemContinentBinding,
        private val settings: GameSettings
    ) : RecyclerView.ViewHolder(binding.root) {

        private val countryAdapter = CountryCheckboxAdapter(settings)

        init {
            binding.countriesRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
            binding.countriesRecyclerView.adapter = countryAdapter
        }

        fun bind(data: ContinentData) {
            binding.continentNameTextView.text = when(data.name) {
                "Africa" -> "Afrika"
                "Asia" -> "Asien"
                "Europe" -> "Europa"
                "North America" -> "Nordamerika"
                "South America" -> "Südamerika"
                "Oceania" -> "Ozeanien"
                else -> data.name
            }

            countryAdapter.submitList(data.countries)

            binding.selectAllButton.setOnClickListener {
                val allEnabled = data.countries.all { settings.isCountryEnabled(it.name) }
                data.countries.forEach { country ->
                    settings.setCountryEnabled(country.name, !allEnabled)
                }
                countryAdapter.notifyDataSetChanged()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ContinentData>() {
        override fun areItemsTheSame(oldItem: ContinentData, newItem: ContinentData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ContinentData, newItem: ContinentData): Boolean {
            return oldItem == newItem
        }
    }
}
