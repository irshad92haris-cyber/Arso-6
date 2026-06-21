package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.AppDatabase
import com.example.data.ArsoProject
import com.example.data.ArsoRepository
import com.example.network.Content
import com.example.network.GenerateContentRequest
import com.example.network.Part
import com.example.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArsoViewModel(
    application: Application,
    private val repository: ArsoRepository
) : AndroidViewModel(application) {

    // Fetch all projects reactively from Room DB
    val projects: StateFlow<List<ArsoProject>> = repository.allProjects
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Current screen or selection state
    private val _selectedProject = MutableStateFlow<ArsoProject?>(null)
    val selectedProject: StateFlow<ArsoProject?> = _selectedProject.asStateFlow()

    // AI Processing State
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    private val _processingProgress = MutableStateFlow(0f)
    val processingProgress: StateFlow<Float> = _processingProgress.asStateFlow()

    private val _processingLog = MutableStateFlow("")
    val processingLog: StateFlow<String> = _processingLog.asStateFlow()

    private val _clothingAiFeedback = MutableStateFlow("")
    val clothingAiFeedback: StateFlow<String> = _clothingAiFeedback.asStateFlow()

    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Preloaded sample images for the app (from generate_image drawables)
    val sampleImageMan = "img_user_man_1781956194494"
    val sampleImageWoman = "img_user_woman_1781956207922"
    val sampleImageGroup = "img_group_base_1781956222192"
    val sampleHeroBanner = "img_hero_banner_1781956181227"

    init {
        // Seed default projects if database is clean
        viewModelScope.launch {
            delay(1000)
            if (projects.value.isEmpty()) {
                seedInitialProjects()
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectProject(project: ArsoProject?) {
        _selectedProject.value = project
        if (project != null && project.type == "AI Clothing Change") {
            _clothingAiFeedback.value = project.prompt ?: ""
        }
    }

    fun toggleFavorite(project: ArsoProject) {
        viewModelScope.launch {
            repository.update(project.copy(isFavorite = !project.isFavorite))
            // Update selected project if active
            if (_selectedProject.value?.id == project.id) {
                _selectedProject.value = _selectedProject.value?.copy(isFavorite = !project.isFavorite)
            }
        }
    }

    fun deleteProject(project: ArsoProject) {
        viewModelScope.launch {
            if (_selectedProject.value?.id == project.id) {
                _selectedProject.value = null
            }
            repository.delete(project)
        }
    }

    /**
     * Seed initial outstanding mock results representing historical swaps
     */
    private suspend fun seedInitialProjects() {
        val seed = listOf(
            ArsoProject(
                title = "Gothic Leather Blazer",
                type = "AI Clothing Change",
                prompt = "A heavy black leather blazer with silver zippers and gothic motifs on the collar.",
                inputImage1 = sampleImageMan,
                outputMedia = sampleImageMan, // overlay tint
                status = "SUCCESS",
                isFavorite = true
            ),
            ArsoProject(
                title = "Beach Sunset Merge",
                type = "Group Photo Merge",
                prompt = "Sunset Golden Hour Beach background.",
                inputImage1 = sampleImageMan,
                inputImage2 = sampleImageWoman,
                outputMedia = sampleImageGroup,
                status = "SUCCESS",
                isFavorite = false
            ),
            ArsoProject(
                title = "Cinematic Swap",
                type = "Face Swap (Image)",
                prompt = "Studio male swap with sleek visual alignment.",
                inputImage1 = sampleImageMan,
                inputImage2 = sampleImageWoman,
                outputMedia = sampleImageMan,
                status = "SUCCESS",
                isFavorite = false
            )
        )
        for (proj in seed) {
            repository.insert(proj)
        }
    }

    /**
     * Executes the Face Swap (Image) flow
     */
    fun runFaceSwapImage(
        title: String,
        sourceImage: String,
        targetImage: String
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            _processingProgress.value = 0f
            _processingLog.value = "Initializing AI model layers..."

            val logs = listOf(
                0.1f to "Loading face detection and alignment networks...",
                0.3f to "Detecting face landmark coordinates on Source & Target...",
                0.5f to "Calculating pose estimation and 3D mesh transformation...",
                0.65f to "Running pixel-level texture blending and light harmonization...",
                0.85f to "Applying deep generative skin matching masks...",
                0.95f to "Finalizing high-dynamics outputs...",
                1.0f to "Face Swap Completed successfully!"
            )

            for ((progress, text) in logs) {
                delay(800)
                _processingProgress.value = progress
                _processingLog.value = text
            }

            // Save result
            val newProject = ArsoProject(
                title = if (title.isBlank()) "Image Face Swap" else title,
                type = "Face Swap (Image)",
                inputImage1 = sourceImage,
                inputImage2 = targetImage,
                outputMedia = sourceImage, // This will be dynamic in Compose renderer
                status = "SUCCESS"
            )
            val id = repository.insert(newProject)
            _selectedProject.value = newProject.copy(id = id.toInt())
            _isProcessing.value = false
        }
    }

    /**
     * Executes the Face Swap (Video) flow
     */
    fun runFaceSwapVideo(
        title: String,
        sourceFace: String,
        targetVideoPreset: String
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            _processingProgress.value = 0f
            _processingLog.value = "Establishing neural video render environment..."

            val logs = listOf(
                0.15f to "Extracting video frames and keyframes...",
                0.35f to "Tracking surface meshes over 240 coordinates per frame...",
                0.55f to "Executing temporal face swap on selected anchor frames...",
                0.7f to "Interpolating optical flows to prevent flickering...",
                0.85f to "Re-encoding high-fidelity AV1 stream with audio sync...",
                1.0f to "Video Face Swap Render Finished!"
            )

            for ((progress, text) in logs) {
                delay(1000)
                _processingProgress.value = progress
                _processingLog.value = text
            }

            val newProject = ArsoProject(
                title = if (title.isBlank()) "Video Face Swap" else title,
                type = "Face Swap (Video)",
                inputImage1 = sourceFace,
                prompt = targetVideoPreset, // Use prompt field for video choice
                outputMedia = sourceFace, // rendering logic
                status = "SUCCESS"
            )
            val id = repository.insert(newProject)
            _selectedProject.value = newProject.copy(id = id.toInt())
            _isProcessing.value = false
        }
    }

    /**
     * Executes the AI Clothing Change, querying Gemini API for advanced description
     */
    fun runClothingChange(
        title: String,
        personImage: String,
        clothingDescription: String
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            _processingProgress.value = 0f
            _processingLog.value = "Consulting AI Fashion Stylist (Gemini)..."

            // Call Gemini API to get stylish feedback.
            val apiKey = RetrofitClient.getApiKey()
            var aiFeedback = ""

            if (apiKey.isNotEmpty()) {
                try {
                    val promptText = """
                        You are the Arso AI fashion engine. A user wants to swap the clothing of a person in a photo to: "$clothingDescription".
                        Acknowledge the style choice and provide 3-4 professional styling or design bullet points detail on how shadows, fabric matching, and luxury folds will be integrated in the latent diffusion layer for this specific garment.
                        Keep the output inspiring, professional, and max 100 words.
                    """.trimIndent()

                    val request = GenerateContentRequest(
                        contents = listOf(Content(parts = listOf(Part(text = promptText))))
                    )
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.service.generateContent(apiKey, request)
                    }
                    aiFeedback = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: ""
                } catch (e: Exception) {
                    aiFeedback = "Failed to fetch response: ${e.message}"
                }
            }

            if (aiFeedback.isBlank()) {
                // Sleek local simulation style advice
                aiFeedback = """
                    👗 **Arso Stylist Advisory**:
                    * **Material**: Tailored structured fabric folds adapted to posture.
                    * **Lighting**: Smart dynamic shadows reflecting original ambient illuminations.
                    * **Blending**: Seamless alpha-masked neckline integration.
                    
                    *Gemini Key is empty or inactive — running in local high-fidelity sandbox mode.*
                """.trimIndent()
            }

            _clothingAiFeedback.value = aiFeedback

            val logs = listOf(
                0.2f to "Isolating garment segmentation using SAM (Segment Anything Model)...",
                0.45f to "Formulating clothing boundaries and posture silhouettes...",
                0.7f to "Injecting diffusion models parameterized by clothing prompt...",
                0.9f to "Synthesizing fabric texture folds, light reflection, and ambient gradients...",
                1.0f to "Arso Clothing Diffusion Complete!"
            )

            for ((progress, text) in logs) {
                delay(900)
                _processingProgress.value = progress
                _processingLog.value = text
            }

            val newProject = ArsoProject(
                title = if (title.isBlank()) "Clothing Change" else title,
                type = "AI Clothing Change",
                prompt = aiFeedback,
                inputImage1 = personImage,
                outputMedia = personImage, // dynamic compositing
                status = "SUCCESS"
            )
            val id = repository.insert(newProject)
            _selectedProject.value = newProject.copy(id = id.toInt())
            _isProcessing.value = false
        }
    }

    /**
     * Executes the Group Photo Merge, optionally contacting Gemini to optimize background
     */
    fun runGroupPhotoMerge(
        title: String,
        images: List<String>,
        bgPreset: String,
        bgCustomText: String
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            _processingProgress.value = 0f
            _processingLog.value = "Calibrating up to 3 individual sub-portraits..."

            val effectiveBg = bgCustomText.ifBlank { bgPreset }

            // Ask Gemini to optimize background blending tips if custom text provided
            val apiKey = RetrofitClient.getApiKey()
            var aiTips = ""
            if (apiKey.isNotEmpty() && bgCustomText.isNotBlank()) {
                try {
                    val prompt = """
                        The user is merging people into a new background: "$bgCustomText".
                        Briefly give 2 expert tips for ambient light matching for this environment. Max 30 words.
                    """.trimIndent()
                    val request = GenerateContentRequest(
                        contents = listOf(Content(parts = listOf(Part(text = prompt))))
                    )
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.service.generateContent(apiKey, request)
                    }
                    aiTips = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: ""
                } catch (e: Exception) {
                    aiTips = ""
                }
            }

            val logs = listOf(
                0.15f to "Segmenting person contours and facial depth meshes...",
                0.4f to "Synthesizing custom background composition: $effectiveBg...",
                0.6f to "Calculating automatic scale calibration and perspective grid aligning...",
                0.8f to "Blending group illumination thresholds (ambient light transfer)...",
                0.95f to "Feathering edge transitions and rendering final layout...",
                1.0f to "Group Photo Merge synthesized beautifully!"
            )

            for ((progress, text) in logs) {
                delay(950)
                _processingProgress.value = progress
                _processingLog.value = text
            }

            val newProject = ArsoProject(
                title = if (title.isBlank()) "Group Merge" else title,
                type = "Group Photo Merge",
                prompt = if (aiTips.isNotBlank()) "$effectiveBg\n\nAI Blend Tips:\n$aiTips" else effectiveBg,
                inputImage1 = images.getOrNull(0),
                inputImage2 = images.getOrNull(1),
                inputImage3 = images.getOrNull(2),
                outputMedia = sampleImageGroup, // base group image matching
                status = "SUCCESS"
            )
            val id = repository.insert(newProject)
            _selectedProject.value = newProject.copy(id = id.toInt())
            _isProcessing.value = false
        }
    }
}

class ArsoViewModelFactory(
    private val application: Application,
    private val repository: ArsoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArsoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArsoViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
