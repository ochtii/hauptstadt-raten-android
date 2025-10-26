package com.example.hauptstadtraten

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.hauptstadtraten.databinding.ActivityGameBinding
import com.example.hauptstadtraten.databinding.DialogGameOverBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var settings: GameSettings
    private var countries = mutableListOf<Country>()
    private var currentCountry: Country? = null
    private var score = 0
    private var timer: CountDownTimer? = null
    private var hintUsed = false
    private var letterCountHintUsed = false
    private var firstLetterHintUsed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        settings = GameSettings(this)

        loadCountries()
        setupGame()
        nextQuestion()
    }

    private fun loadCountries() {
        val enabledContinents = settings.getEnabledContinents()
        countries = CountryData.countries.filter { country ->
            enabledContinents.any { continent ->
                country.continent.contains(continent)
            } && settings.isCountryEnabled(country.name)
        }.toMutableList()
        countries.shuffle()
    }

    private fun setupGame() {
        binding.submitButton.setOnClickListener {
            checkAnswer()
        }

        binding.nextButton.setOnClickListener {
            nextQuestion()
        }

        binding.hintLetterCountButton.setOnClickListener {
            showLetterCountHint()
        }

        binding.hintFirstLetterButton.setOnClickListener {
            showFirstLetterHint()
        }

        binding.answerEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkAnswer()
                true
            } else {
                false
            }
        }

        updateScore()
    }

    private fun nextQuestion() {
        if (countries.isEmpty()) {
            showGameOver()
            return
        }

        currentCountry = countries.removeAt(0)
        hintUsed = false
        letterCountHintUsed = false
        firstLetterHintUsed = false

        binding.flagTextView.text = currentCountry?.flag
        binding.questionTextView.text = getString(R.string.what_is_capital, currentCountry?.name)
        
        // Zeige Statistiken für dieses Land
        currentCountry?.let { country ->
            val stats = settings.getCountryStats(country.name)
            binding.countryStatsTextView.text = getString(
                R.string.country_stats,
                stats.correctCount,
                stats.wrongCount
            )
        }
        
        binding.answerEditText.text?.clear()
        binding.answerEditText.isEnabled = true
        binding.resultTextView.visibility = View.GONE
        binding.hintTextView.visibility = View.GONE
        binding.submitButton.visibility = View.VISIBLE
        binding.nextButton.visibility = View.GONE

        if (settings.hintsEnabled) {
            binding.hintButtonsLayout.visibility = View.VISIBLE
            binding.hintLetterCountButton.isEnabled = true
            binding.hintFirstLetterButton.isEnabled = true
        } else {
            binding.hintButtonsLayout.visibility = View.GONE
        }

        startTimer()
        binding.answerEditText.requestFocus()
        showKeyboard()
    }

    private fun showLetterCountHint() {
        currentCountry?.let { country ->
            val letterCount = country.capital.length
            binding.hintTextView.text = getString(R.string.hint_letter_count, letterCount)
            binding.hintTextView.visibility = View.VISIBLE
            binding.hintLetterCountButton.isEnabled = false
            letterCountHintUsed = true
            hintUsed = true
        }
    }

    private fun showFirstLetterHint() {
        currentCountry?.let { country ->
            val firstLetter = country.capital.first()
            val hintText = getString(R.string.hint, firstLetter)
            if (binding.hintTextView.visibility == View.VISIBLE) {
                binding.hintTextView.text = "${binding.hintTextView.text}\n$hintText"
            } else {
                binding.hintTextView.text = hintText
                binding.hintTextView.visibility = View.VISIBLE
            }
            binding.hintFirstLetterButton.isEnabled = false
            firstLetterHintUsed = true
            hintUsed = true
        }
    }

    private fun checkAnswer() {
        timer?.cancel()
        hideKeyboard()

        val userAnswer = binding.answerEditText.text.toString().trim()
        val acceptedAnswers = currentCountry?.getAllAcceptedCapitals() ?: emptyList()

        // Prüfe ob die Antwort mit Schreibfehlertoleranz richtig ist
        val isCorrect = StringMatcher.matchesAny(userAnswer, acceptedAnswers, maxDistance = 2)

        binding.answerEditText.isEnabled = false
        binding.submitButton.visibility = View.GONE
        binding.nextButton.visibility = View.VISIBLE
        binding.hintButtonsLayout.visibility = View.GONE
        binding.resultTextView.visibility = View.VISIBLE

        // Speichere Statistik
        currentCountry?.let { country ->
            settings.recordAnswer(country.name, isCorrect)
        }

        if (isCorrect) {
            val points = when {
                !hintUsed -> 3  // Keine Tipps verwendet
                letterCountHintUsed && firstLetterHintUsed -> 1  // Beide Tipps verwendet
                else -> 2  // Ein Tipp verwendet
            }
            score += points
            updateScore()
            binding.resultTextView.text = getString(R.string.correct)
            binding.resultTextView.setTextColor(getColor(R.color.success))
        } else {
            val correctAnswer = currentCountry?.capital ?: ""
            binding.resultTextView.text = getString(R.string.wrong, correctAnswer)
            binding.resultTextView.setTextColor(getColor(R.color.error))
        }
    }

    private fun startTimer() {
        timer?.cancel()

        val timeLimit = settings.timeLimit
        if (timeLimit == 0) {
            binding.timerTextView.visibility = View.GONE
            return
        }

        binding.timerTextView.visibility = View.VISIBLE

        timer = object : CountDownTimer((timeLimit * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                binding.timerTextView.text = getString(R.string.time_remaining, secondsRemaining)

                if (secondsRemaining <= 5) {
                    binding.timerTextView.setTextColor(getColor(R.color.error))
                } else {
                    binding.timerTextView.setTextColor(getColor(R.color.secondary))
                }
            }

            override fun onFinish() {
                binding.timerTextView.text = getString(R.string.time_remaining, 0)
                checkAnswer()
            }
        }.start()
    }

    private fun updateScore() {
        binding.scoreTextView.text = getString(R.string.score, score)
    }

    private fun showGameOver() {
        val dialog = Dialog(this)
        val dialogBinding = DialogGameOverBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.finalScoreTextView.text = getString(R.string.score, score)

        dialogBinding.playAgainButton.setOnClickListener {
            dialog.dismiss()
            score = 0
            updateScore()
            loadCountries()
            nextQuestion()
        }

        dialogBinding.backToMenuButton.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.setCancelable(false)
        dialog.show()
    }

    private fun showKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.answerEditText.postDelayed({
            imm.showSoftInput(binding.answerEditText, InputMethodManager.SHOW_IMPLICIT)
        }, 100)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.answerEditText.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
