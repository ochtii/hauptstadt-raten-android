package com.example.hauptstadtraten

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hauptstadtraten.databinding.ActivityStatisticsBinding
import com.google.android.material.tabs.TabLayout

class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var settings: GameSettings
    private lateinit var adapter: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        settings = GameSettings(this)

        setupRecyclerView()
        setupTabLayout()
        
        loadStatistics(true)
    }

    private fun setupRecyclerView() {
        adapter = StatisticsAdapter()
        binding.statisticsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.statisticsRecyclerView.adapter = adapter
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadStatistics(tab?.position == 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadStatistics(showTopCorrect: Boolean) {
        val allStats = settings.getAllStats()
        
        if (allStats.isEmpty()) {
            binding.noStatsTextView.visibility = View.VISIBLE
            binding.statisticsRecyclerView.visibility = View.GONE
            return
        }

        binding.noStatsTextView.visibility = View.GONE
        binding.statisticsRecyclerView.visibility = View.VISIBLE

        val statsWithCountries = allStats.mapNotNull { (countryName, stats) ->
            val country = CountryData.countries.find { it.name == countryName }
            country?.let { CountryStats(it, stats.correctCount, stats.wrongCount) }
        }

        val sortedStats = if (showTopCorrect) {
            statsWithCountries.sortedByDescending { it.correctCount }
        } else {
            statsWithCountries.sortedByDescending { it.wrongCount }
        }.take(20)

        adapter.submitList(sortedStats)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
