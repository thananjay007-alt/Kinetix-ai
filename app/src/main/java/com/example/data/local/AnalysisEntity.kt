package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "biomechanical_analyses")
data class AnalysisEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val movementName: String,
    val sportCategory: String,
    val videoUriOrName: String,
    val injuryRiskLevel: String, // "SAFE", "LOW", "MODERATE", "HIGH"
    val fullMarkdown: String,
    val keyCuesSummary: String,
    val efficiencyScore: Int = 85,
    val isFavorite: Boolean = false
)
