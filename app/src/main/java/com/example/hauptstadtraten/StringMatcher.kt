package com.example.hauptstadtraten

object StringMatcher {
    /**
     * Berechnet die Levenshtein-Distanz zwischen zwei Strings
     * (Anzahl der erforderlichen Einfügungen, Löschungen oder Ersetzungen)
     */
    fun levenshteinDistance(s1: String, s2: String): Int {
        val len1 = s1.length
        val len2 = s2.length
        val dp = Array(len1 + 1) { IntArray(len2 + 1) }

        for (i in 0..len1) {
            dp[i][0] = i
        }
        for (j in 0..len2) {
            dp[0][j] = j
        }

        for (i in 1..len1) {
            for (j in 1..len2) {
                val cost = if (s1[i - 1].equals(s2[j - 1], ignoreCase = true)) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,      // Löschung
                    dp[i][j - 1] + 1,      // Einfügung
                    dp[i - 1][j - 1] + cost // Ersetzung
                )
            }
        }

        return dp[len1][len2]
    }

    /**
     * Prüft ob zwei Strings mit einer Toleranz von maxDistance übereinstimmen
     */
    fun matches(input: String, target: String, maxDistance: Int = 2): Boolean {
        val distance = levenshteinDistance(input.trim(), target.trim())
        return distance <= maxDistance
    }

    /**
     * Prüft ob der Input mit einem der Ziel-Strings übereinstimmt
     */
    fun matchesAny(input: String, targets: List<String>, maxDistance: Int = 2): Boolean {
        return targets.any { target -> matches(input, target, maxDistance) }
    }
}
