package com.example.data.model

data class KineticPhase(
    val phaseName: String,
    val timeRange: String,
    val mechanics: String,
    val keyFocus: String = ""
)

data class KinematicAnalysis(
    val jointAngles: String,
    val spinalAlignment: String,
    val baseOfSupport: String,
    val kineticChainEfficiency: String,
    val efficiencyScore: Int = 85 // 0-100%
)

data class InjuryRiskAssessment(
    val riskLevel: RiskSeverity,
    val isSafe: Boolean,
    val detectedFlaws: List<String>,
    val detailedSummary: String
)

enum class RiskSeverity(val label: String, val colorHex: Long) {
    SAFE("Optimal Form / Safe", 0xFF10B981),
    LOW("Minor Inefficiency", 0xFF06B6D4),
    MODERATE("Caution Required", 0xFFF59E0B),
    HIGH("High Risk Flaw", 0xFFEF4444)
}

data class CorrectiveCue(
    val number: Int,
    val cueText: String,
    val targetArea: String,
    val drillRecommendation: String = ""
)

data class BiomechanicalReport(
    val movementName: String,
    val sportCategory: String,
    val phases: List<KineticPhase>,
    val kinematics: KinematicAnalysis,
    val injuryAssessment: InjuryRiskAssessment,
    val cues: List<CorrectiveCue>,
    val rawMarkdown: String
)
