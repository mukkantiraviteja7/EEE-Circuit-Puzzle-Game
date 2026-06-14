<div align="center">

# ⚡ EEE Circuit Puzzle Game

[![Android](https://img.shields.io/badge/Android-Java-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![minSdk](https://img.shields.io/badge/minSdk-24_(Android_7)-blue?style=for-the-badge)](https://developer.android.com)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

**A sliding tile puzzle game with EEE (Electrical & Electronics Engineering) theme.**
Arrange the electronic component symbols in the correct order to solve the circuit!

</div>

---

## 🎮 How to Play

1. Tiles show **EEE symbols** (R, C, L, V, Ω, Hz, DC, AC...)
2. One tile space is **empty** — tap adjacent tiles to slide them
3. Arrange all tiles in **numerical order** (1 → last)
4. Complete it in the **fewest moves and fastest time**!

---

## 🎯 Difficulty Levels

| Level | Grid | Tiles | Challenge |
|---|---|---|---|
| ⚡ Easy   | 3×3 | 8 tiles  | Beginner friendly |
| 🔌 Medium | 4×4 | 15 tiles | Requires strategy |
| 🔥 Hard   | 5×5 | 24 tiles | EEE Expert level! |

---

## 🔤 EEE Symbols Used

| Symbol | Full Name | Symbol | Full Name |
|---|---|---|---|
| R  | Resistor    | C  | Capacitor  |
| L  | Inductor    | V  | Voltage    |
| I  | Current     | Ω  | Ohm        |
| W  | Watt        | Hz | Hertz      |
| DC | Direct C.   | AC | Alternating|
| GND| Ground      | Vcc| Supply     |
| EMF| Electromotive | μF | Microfarad |
| kΩ | Kilo-Ohm    | mH | Millihenry |

---

## 🚀 How to Build & Run

### Requirements
- Android Studio (Hedgehog or newer)
- Android SDK 34
- Java 8+
- Android device or emulator (API 24+)

### Steps
```bash
# 1. Clone the repository
git clone https://github.com/mukkantiraviteja7/EEE-Circuit-Puzzle-Game.git

# 2. Open in Android Studio
File → Open → select the cloned folder

# 3. Sync Gradle
Click "Sync Now" when prompted

# 4. Run the app
Click ▶ Run (Shift+F10) → select device/emulator
```

---

## 📁 Project Structure

```
EEE-Circuit-Puzzle-Game/
├── app/src/main/
│   ├── java/com/raviteja/eeepuzzle/
│   │   ├── MainActivity.java     ← Home screen & difficulty selection
│   │   ├── GameActivity.java     ← Game board & interactions
│   │   ├── PuzzleBoard.java      ← Core puzzle logic & EEE symbols
│   │   └── ScoreManager.java     ← High score storage
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml ← Home screen UI
│   │   │   └── activity_game.xml ← Game screen UI
│   │   ├── drawable/
│   │   │   └── tile_normal.xml   ← Circuit board tile design
│   │   └── values/
│   │       ├── colors.xml        ← Neon green/cyan color scheme
│   │       ├── strings.xml
│   │       └── themes.xml
│   └── AndroidManifest.xml
├── build.gradle
├── settings.gradle
└── README.md
```

---

## 🎨 Design Theme

- 🖤 **Background**: Deep dark navy (`#0A0E1A`) — like a circuit board
- 💚 **Tile text**: Neon green (`#00FF88`) — like an oscilloscope display
- 🔵 **Accents**: Neon cyan (`#00D4FF`) — like LED indicators
- 🟡 **Timer**: Gold (`#FFD700`) — for importance
- Tile borders glow cyan, pressed tiles glow green

---

## 👨‍💻 Developer

**Mukkanti Ravi Teja**
- 🎓 B.Tech EEE Student | Nellore, Andhra Pradesh, India
- 🌐 Portfolio: [mukkantiraviteja7.github.io](https://mukkantiraviteja7.github.io)
- 🐙 GitHub: [@mukkantiraviteja7](https://github.com/mukkantiraviteja7)

---

<div align="center">⭐ Star this repo if you liked the game!</div>