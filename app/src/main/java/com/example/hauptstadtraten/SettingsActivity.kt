package com.example.hauptstadtraten

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.hauptstadtraten.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settings: GameSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        settings = GameSettings(this)

        setupTimeLimitSpinner()
        setupHintsSwitch()
        setupCountrySelection()
    }

    private fun setupTimeLimitSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.time_limit_entries,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.timeLimitSpinner.adapter = adapter

        val timeValues = resources.getStringArray(R.array.time_limit_values)
        val currentTimeLimit = settings.timeLimit
        val position = timeValues.indexOf(currentTimeLimit.toString())
        if (position >= 0) {
            binding.timeLimitSpinner.setSelection(position)
        }

        binding.timeLimitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                settings.timeLimit = timeValues[position].toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupHintsSwitch() {
        binding.hintsSwitch.isChecked = settings.hintsEnabled
        binding.hintsSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.hintsEnabled = isChecked
        }
    }

    private fun setupCountrySelection() {
        binding.selectCountriesButton.setOnClickListener {
            startActivity(Intent(this, CountrySelectionActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
