package com.example.hauptstadtraten

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hauptstadtraten.databinding.ItemCountryCheckboxBinding

class CountryCheckboxAdapter(
    private val settings: GameSettings
) : ListAdapter<Country, CountryCheckboxAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCountryCheckboxBinding.inflate(
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
        private val binding: ItemCountryCheckboxBinding,
        private val settings: GameSettings
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            binding.countryCheckbox.text = "${country.flag} ${country.name}"
            binding.countryCheckbox.isChecked = settings.isCountryEnabled(country.name)
            
            binding.countryCheckbox.setOnCheckedChangeListener { _, isChecked ->
                settings.setCountryEnabled(country.name, isChecked)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }
}
