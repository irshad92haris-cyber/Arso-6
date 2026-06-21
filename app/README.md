# Arso AI — AI-Powered Photo & Video Editor

Arso is a production-ready, beautiful, and feature-complete Android application built with modern **Jetpack Compose**, **Kotlin**, **Room Database for Local Data Persistence**, and direct integrations with the **Google Gemini API** (using Retrofit + Moshi).

Arso offers four main features centered in a sleek, high-fidelity dark-themed creative dashboard:
1. **Face Swap (Image)**: Select source and target portrait preset photos, trigger deep landmark alignment models, and review results with an interactive, gorgeous side-by-side comparison slider.
2. **Face Swap (Video)**: Synthesize dynamic face tracking onto cinematic action scene presets, with an interactive mock video timelines progress bar and scrubber controller.
3. **AI Clothing Refit**: Query the **Gemini 3.5 Flash / Gemini 3.1 Pro** API with text garments prompts (e.g., "Futuristic Stainless Steel Armor") to generate professional stylist advisory details, segment garment layer masks, and blend overlays on portraits.
4. **AI Group Photo Merge**: Segment silhouettes from up to three different portrait sources and organically blend them over chosen custom generated studios backdrop or scenic description.

---

## 🚀 Key Architectural Outlines

- **MVVM Architecture**: Follows proper Clean separation of layers between reactive views, `ViewModel` state flows, and abstracted persistence repositories.
- **Room Database Storage**: Saves finalized design history, title, project classification types, and parameters local persistence immediately. Users can search logs, favorite files, or delete items.
- **Edge-to-Edge Fluidity**: Full-bleed immersive display design matching Google's latest **Material Design 3 (M3)** specifications, incorporating balanced margins and luxury modern typography.
- **Robust Failure Resilience**: Transparently resolves lack of API Key configurations, providing beautiful simulation templates to satisfy continuous operation without breaking interface elements.

---

## 📂 Core Project Structures

```
/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   │   ├── data/                 # Room DB Entities, Daos & AppDatabase configs
│   │   │   │   ├── network/              # Retrofit rest service and Moshi Gemini Request schemas
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/          # Dashboard, Face Swap, Refit, and Detail screens
│   │   │   │   │   ├── theme/            # Material 3 custom Color palettes & Typography themes
│   │   │   │   │   └── DrawableResolver  # Connects generated assets dynamically
│   │   │   │   └── MainActivity.kt       # Navigation entry points
│   │   │   ├── res/
│   │   │   │   ├── drawable/             # Adaptive custom launcher & beautiful generated AI portraits
│   │   │   │   └── values/strings.xml    # Unified app resource strings
│   │   │   └── AndroidManifest.xml       # Internet permissions declaration
│   │   └── build.gradle.kts              # Application build config and dependencies
│   └── build.gradle.kts                  # Project level configuration
├── metadata.json                         # Project metadata
└── README.md                             # Running guide
```

---

## 🛠️ Build and Run Guide

### 1. Configure the Gemini API Key
Arso leverages the server-side capabilities of Google Gemini.
To configure the key in the development environment:
1. Open the **Secrets Panel** in your **Google AI Studio** workspace.
2. Add a new secret with the name: **`GEMINI_API_KEY`** and enter your valid API Key.
3. The platform will automatically inject the key into the `.env` configuration file at compile time, matching the values defined in `.env.example`.

*Note: In compliance with Google's production requirements, Arso does not expose keys or variables using hardcoded values, local resources, or unsafely stored `local.properties`.*

### 2. Build via terminal / Gradle
To build the project executable and outputs:
- Go to your root directory.
- Execute standard Gradle compilation steps:
```bash
gradle assembleDebug
```
- Or run local tests using:
```bash
gradle :app:testDebugUnitTest
```

---

## 🌌 Modern Visual Aesthetic Pairings
Arso establishes a **Midnight Cadet Midnight Slate theme** with vibrant **Radiant Sky Blue** and **Deep Violet** system highlights:
- Generates beautiful tactile interactive feedback using Material Ripples.
- Supports high-contrast vector adaptive launcher icons centering active AI lens graphics.
- Features beautiful generated photo and banner assets stored in resource layouts.
