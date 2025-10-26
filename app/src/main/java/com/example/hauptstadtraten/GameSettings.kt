package com.example.hauptstadtraten

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GameSettings(context: Context) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("game_settings", Context.MODE_PRIVATE)
    
    private val statsPrefs: SharedPreferences = 
        context.getSharedPreferences("country_stats", Context.MODE_PRIVATE)
    
    private val gson = Gson()

    var timeLimit: Int
        get() = prefs.getInt("time_limit", 30)
        set(value) = prefs.edit().putInt("time_limit", value).apply()

    var hintsEnabled: Boolean
        get() = prefs.getBoolean("hints_enabled", true)
        set(value) = prefs.edit().putBoolean("hints_enabled", value).apply()

    var africaEnabled: Boolean
        get() = prefs.getBoolean("africa_enabled", true)
        set(value) = prefs.edit().putBoolean("africa_enabled", value).apply()

    var asiaEnabled: Boolean
        get() = prefs.getBoolean("asia_enabled", true)
        set(value) = prefs.edit().putBoolean("asia_enabled", value).apply()

    var europeEnabled: Boolean
        get() = prefs.getBoolean("europe_enabled", true)
        set(value) = prefs.edit().putBoolean("europe_enabled", value).apply()

    var northAmericaEnabled: Boolean
        get() = prefs.getBoolean("north_america_enabled", true)
        set(value) = prefs.edit().putBoolean("north_america_enabled", value).apply()

    var southAmericaEnabled: Boolean
        get() = prefs.getBoolean("south_america_enabled", true)
        set(value) = prefs.edit().putBoolean("south_america_enabled", value).apply()

    var oceaniaEnabled: Boolean
        get() = prefs.getBoolean("oceania_enabled", true)
        set(value) = prefs.edit().putBoolean("oceania_enabled", value).apply()

    fun getEnabledContinents(): List<String> {
        val continents = mutableListOf<String>()
        if (africaEnabled) continents.add("Africa")
        if (asiaEnabled) continents.add("Asia")
        if (europeEnabled) continents.add("Europe")
        if (northAmericaEnabled) continents.add("North America")
        if (southAmericaEnabled) continents.add("South America")
        if (oceaniaEnabled) continents.add("Oceania")
        return continents
    }
    
    // Einzelne Länder aktivieren/deaktivieren
    fun isCountryEnabled(countryName: String): Boolean {
        return prefs.getBoolean("country_$countryName", true)
    }
    
    fun setCountryEnabled(countryName: String, enabled: Boolean) {
        prefs.edit().putBoolean("country_$countryName", enabled).apply()
    }
    
    // Statistiken
    fun recordAnswer(countryName: String, correct: Boolean) {
        val key = "stats_$countryName"
        val statsJson = statsPrefs.getString(key, null)
        val stats = if (statsJson != null) {
            gson.fromJson(statsJson, CountryStatsData::class.java)
        } else {
            CountryStatsData()
        }
        
        if (correct) {
            stats.correctCount++
        } else {
            stats.wrongCount++
        }
        
        statsPrefs.edit().putString(key, gson.toJson(stats)).apply()
    }
    
    fun getCountryStats(countryName: String): CountryStatsData {
        val key = "stats_$countryName"
        val statsJson = statsPrefs.getString(key, null)
        return if (statsJson != null) {
            gson.fromJson(statsJson, CountryStatsData::class.java)
        } else {
            CountryStatsData()
        }
    }
    
    fun getAllStats(): Map<String, CountryStatsData> {
        val allStats = mutableMapOf<String, CountryStatsData>()
        statsPrefs.all.forEach { (key, value) ->
            if (key.startsWith("stats_") && value is String) {
                val countryName = key.removePrefix("stats_")
                val stats = gson.fromJson(value, CountryStatsData::class.java)
                allStats[countryName] = stats
            }
        }
        return allStats
    }
}

data class CountryStatsData(
    var correctCount: Int = 0,
    var wrongCount: Int = 0
)
