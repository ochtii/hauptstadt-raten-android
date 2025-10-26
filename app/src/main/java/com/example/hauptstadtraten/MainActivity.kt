package com.example.hauptstadtraten

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hauptstadtraten.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var settings: GameSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settings = GameSettings(this)

        binding.startButton.setOnClickListener {
            val enabledContinents = settings.getEnabledContinents()
            if (enabledContinents.isEmpty()) {
                android.widget.Toast.makeText(
                    this,
                    R.string.select_continents,
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else {
                startActivity(Intent(this, GameActivity::class.java))
            }
        }

        binding.statisticsButton.setOnClickListener {
            startActivity(Intent(this, StatisticsActivity::class.java))
        }

        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
