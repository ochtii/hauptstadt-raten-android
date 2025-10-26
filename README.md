# Hauptstadt Raten 🌍

Eine Android-Quiz-App zum Erraten von Hauptstädten aus aller Welt.

## Features

✨ **Einstellbares Zeitlimit**
- 10, 20, 30, 60 Sekunden oder kein Limit
- Visuelles Feedback bei wenig verbleibender Zeit

🌍 **Kontinente auswählen**
- Afrika
- Asien
- Europa
- Nordamerika
- Südamerika
- Ozeanien

💡 **Tipps-System**
- Optional aktivierbar
- Zeigt den ersten Buchstaben der Hauptstadt
- Weniger Punkte bei Verwendung eines Tipps

🎨 **Dunkles Design**
- Material Design 3
- Moderne, augenfreundliche Oberfläche
- Optimiert für lange Spielsessions

## Punkte-System

- **Richtige Antwort ohne Tipp:** 2 Punkte
- **Richtige Antwort mit Tipp:** 1 Punkt
- **Falsche Antwort:** 0 Punkte

## Installation

1. Projekt in Android Studio öffnen
2. Gradle Sync durchführen
3. App auf Emulator oder echtem Gerät ausführen

## Technische Details

- **Sprache:** Kotlin
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **UI:** ViewBinding, Material Design 3
- **Länder:** 195 Länder mit Flaggen-Emojis

## Spielanleitung

1. **Einstellungen anpassen** (optional)
   - Zeitlimit festlegen
   - Kontinente auswählen
   - Tipps aktivieren/deaktivieren

2. **Spiel starten**
   - Flagge und Ländername werden angezeigt
   - Hauptstadt eingeben
   - Mit "Absenden" bestätigen

3. **Feedback erhalten**
   - Richtige/falsche Antwort wird angezeigt
   - Bei falscher Antwort wird die korrekte Lösung gezeigt
   - Mit "Nächste Frage" fortfahren

4. **Spielende**
   - Alle Fragen beantwortet
   - Endstand wird angezeigt
   - Nochmal spielen oder zurück zum Menü

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
