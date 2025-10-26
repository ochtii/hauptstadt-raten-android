package com.example.hauptstadtraten

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hauptstadtraten.databinding.ActivityCountrySelectionBinding

class CountrySelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountrySelectionBinding
    private lateinit var settings: GameSettings
    private lateinit var adapter: ContinentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountrySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.select_countries)
        
        settings = GameSettings(this)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val continents = listOf("Africa", "Asia", "Europe", "North America", "South America", "Oceania")
        val continentData = continents.map { continent ->
            val countries = CountryData.countries.filter { 
                it.continent.contains(continent)
            }
            ContinentData(continent, countries)
        }

        adapter = ContinentAdapter(settings)
        binding.continentsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.continentsRecyclerView.adapter = adapter
        adapter.submitList(continentData)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

data class ContinentData(
    val name: String,
    val countries: List<Country>
)
