# Hauptstadt Raten 🌍

Eine moderne Android-Quiz-App zum Erraten von Hauptstädten aus aller Welt mit intelligentem Fuzzy Matching und umfangreichen Statistiken.

## ✨ Features

### 🎮 Spielmodi
- **Einstellbares Zeitlimit:** 10, 20, 30, 60 Sekunden oder unbegrenzt
- **195 Länder:** Alle UN-Mitgliedsstaaten mit Flaggen-Emojis
- **Kontinentfilter:** Wähle einzelne oder mehrere Kontinente aus

### 🌍 Intelligente Länderverwaltung
- **Individuelle Länderauswahl:** Aktiviere/Deaktiviere einzelne Länder pro Kontinent
- **Fast-Scroll-Bar:** Schnelles Navigieren durch lange Länderlisten
- **Kontinent-Tabs:** Organisiert nach Afrika, Asien, Europa, Nord-/Südamerika, Ozeanien
- **Select All/Deselect All:** Praktische Schnellauswahl-Buttons

### 💡 Zweistufiges Tipps-System
- **Buchstabenanzahl:** Zeigt die Anzahl der Buchstaben
- **Erster Buchstabe:** Zeigt den ersten Buchstaben der Hauptstadt
- **Optional aktivierbar:** Ein/Ausschaltbar in den Einstellungen

### 🎯 Fuzzy Matching (Schreibfehlertoleranz)
- **2-Buchstaben-Toleranz:** Kleine Tippfehler werden automatisch erkannt
- **Levenshtein-Algorithmus:** Intelligente Distanzberechnung
- **Beispiele:** "Belin" → Berlin ✓, "Pris" → Paris ✓

### 🌐 Mehrsprachige Antworten
Die App akzeptiert Hauptstädte in verschiedenen Schreibweisen:
- **Deutsche Namen:** Wien, Warschau, Moskau, Lissabon
- **Englische Namen:** Vienna, Warsaw, Moscow, Lisbon
- **Landeseigene Namen:** Warszawa, Moskva, Lisboa
- **Kombiniert mit Fuzzy Matching:** Maximale Flexibilität

### 📊 Detaillierte Statistiken
- **Pro-Land-Statistik:** Verfolge Richtig/Falsch für jedes Land
- **Top 20 Listen:** Meistens richtig & meistens falsch
- **Visuelle Fortschrittsbalken:** Schneller Überblick
- **Persistente Speicherung:** Statistiken bleiben erhalten

### 🎨 Modernes Design
- **Material Design 3:** Neueste Design-Standards
- **Dunkles Theme:** Augenfreundlich und energiesparend
- **Grüne Akzentfarbe:** Frisches, modernes Erscheinungsbild
- **Responsive Layouts:** Optimiert für verschiedene Bildschirmgrößen

## 📱 Screenshots

*(Screenshots hier einfügen)*

## 🚀 Installation

### Voraussetzungen
- Android Studio Arctic Fox oder höher
- Android SDK 34
- Kotlin 1.9.0+

### Build & Installation
```bash
# Repository klonen
git clone https://github.com/ochtii/hauptstadt-raten-android.git
cd hauptstadt-raten-android

# Gradle Build
./gradlew assembleDebug

# App auf verbundenem Gerät installieren
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## 🎮 Spielanleitung

1. **Länder auswählen**
   - Öffne Einstellungen → Länderauswahl
   - Wähle Kontinente über Tabs aus
   - Aktiviere/Deaktiviere einzelne Länder
   - Nutze "Alle auswählen/abwählen" für schnelle Auswahl
   - Fast-Scroll-Bar für schnelles Navigieren

2. **Einstellungen anpassen**
   - **Zeitlimit:** 10, 20, 30, 60 Sekunden oder unbegrenzt
   - **Tipps:** Ein/Ausschalten des Hinweis-Systems

3. **Spiel starten**
   - Flagge (Emoji) und Ländername werden angezeigt
   - Hauptstadt in beliebiger Sprache eingeben
   - Tippfehler bis zu 2 Buchstaben werden toleriert
   - Optional: Tipps verwenden (Buchstabenanzahl → Erster Buchstabe)
   - Mit Enter oder "Absenden" bestätigen

4. **Antworten**
   - ✅ **Richtig:** Sofortiges positives Feedback
   - ❌ **Falsch:** Korrekte Antwort wird angezeigt
   - **Pro-Land-Statistik:** Wird automatisch aktualisiert
   - Mit "Nächste Frage" fortfahren

5. **Spielende**
   - Alle ausgewählten Länder durchgespielt
   - **Endstand:** Punktzahl und Zusammenfassung
   - **Statistik:** Detaillierte Auswertung verfügbar
   - "Nochmal spielen" oder "Zurück zum Menü"

6. **Statistiken einsehen**
   - Öffne Hauptmenü → Statistik
   - **Top 20 richtig:** Deine stärksten Länder
   - **Top 20 falsch:** Länder zum Üben
   - Fortschrittsbalken zeigen Verhältnis richtig/falsch

## 🛠️ Technische Details

### Architektur
- **Sprache:** Kotlin 1.9.0
- **Min SDK:** 24 (Android 7.0 Nougat)
- **Target SDK:** 34 (Android 14)
- **Build System:** Gradle 8.0 mit Kotlin DSL

### Libraries & Frameworks
- **Material Design 3:** Modernes UI-Framework
- **ViewBinding:** Type-safe View-Zugriff
- **RecyclerView:** Effiziente Listen-Darstellung
- **SharedPreferences + Gson:** Persistente Datenspeicherung
- **Constraint Layout:** Flexible UI-Layouts

### Datenstruktur
```kotlin
data class Country(
    val name: String,                          // Ländername
    val capital: String,                       // Hauptstadt (primär)
    val alternativeCapitals: List<String>,     // Alternative Schreibweisen
    val flag: String,                          // Emoji-Flagge
    val continent: String                      // Kontinent
)
```

### Algorithmen
- **Levenshtein Distance:** Fuzzy Matching mit O(m×n) Komplexität
- **String Matching:** Case-insensitive Vergleich mit Toleranz

### Code-Struktur
```
app/src/main/java/com/example/hauptstadtraten/
├── MainActivity.kt              # Hauptmenü
├── GameActivity.kt              # Spiellogik
├── SettingsActivity.kt          # Einstellungen
├── StatisticsActivity.kt        # Statistik-Anzeige
├── CountrySelectionActivity.kt  # Länderauswahl
├── Country.kt                   # Datenmodelle
├── CountryData.kt              # 195 Länder-Daten
├── GameSettings.kt             # SharedPreferences Wrapper
├── StringMatcher.kt            # Fuzzy Matching Algorithmus
└── *Adapter.kt                 # RecyclerView Adapter

app/src/main/res/
├── layout/                     # XML Layouts
├── values/
│   ├── colors.xml             # Farbdefinitionen (Grünes Theme)
│   ├── strings.xml            # Deutsche Texte
│   └── themes.xml             # Material Design Theme
```

## 📝 Beispiele für akzeptierte Antworten

| Land | Primär | Alternative Antworten | Mit Fuzzy Match |
|------|--------|----------------------|-----------------|
| 🇦🇹 Österreich | Vienna | Wien | Viena, Vienne |
| 🇵🇱 Polen | Warsaw | Warschau, Warszawa | Warsav, Varsava |
| 🇷🇺 Russland | Moscow | Moskau, Moskva | Moscou, Moskou |
| 🇵🇹 Portugal | Lisbon | Lissabon, Lisboa | Lisbom, Lissbon |
| 🇨🇿 Tschechien | Prague | Prag, Praha | Praga, Praghe |
| 🇺🇸 USA | Washington, D.C. | Washington DC, Washington | Washingtn, Wasington |

## 🤝 Beitragen

Contributions sind willkommen! Bitte erstelle einen Pull Request für:
- Neue Features
- Bugfixes
- Verbesserungen der Benutzeroberfläche
- Erweiterungen der Länder-Daten
- Performance-Optimierungen

## 📄 Lizenz

*(Lizenz hier einfügen)*

## 👤 Autor

**ochtii**
- GitHub: [@ochtii](https://github.com/ochtii)
- Repository: [hauptstadt-raten-android](https://github.com/ochtii/hauptstadt-raten-android)

## 🙏 Danksagungen

- Material Design 3 für das moderne UI-Framework
- Alle Hauptstadtdaten basieren auf offiziellen UN-Mitgliedstaaten
- Emoji-Flaggen aus dem Unicode-Standard

---

**Viel Spaß beim Hauptstädte raten! 🌍🎮**

## Struktur

```
app/src/main/
├── java/com/example/hauptstadtraten/
│   ├── MainActivity.kt          # Hauptmenü
│   ├── GameActivity.kt          # Spiellogik
│   ├── SettingsActivity.kt      # Einstellungen
│   ├── Country.kt               # Datenmodell
│   ├── CountryData.kt           # Länderliste
│   └── GameSettings.kt          # Einstellungsverwaltung
└── res/
    ├── layout/                  # UI-Layouts
    ├── values/                  # Strings, Colors, Themes
    └── ...
```

## Lizenz

Dieses Projekt wurde als Beispiel-App erstellt.
