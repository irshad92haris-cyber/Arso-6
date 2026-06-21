package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "arso_projects")
data class ArsoProject(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val type: String, // "Face Swap (Image)", "Face Swap (Video)", "AI Clothing Change", "Group Photo Merge"
    val timestamp: Long = System.currentTimeMillis(),
    val prompt: String? = null,
    val inputImage1: String? = null,
    val inputImage2: String? = null,
    val inputImage3: String? = null,
    val outputMedia: String? = null,
    val status: String = "SUCCESS", // "PROCESSING", "SUCCESS", "FAILED"
    val isFavorite: Boolean = false
) : Serializable
