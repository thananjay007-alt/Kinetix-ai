package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BiomechanicalReport
import com.example.data.model.KineticPhase
import com.example.data.model.RiskSeverity
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.CoralRisk
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark

@Composable
fun ReportView(
    report: BiomechanicalReport,
    modifier: Modifier = Modifier
) {
    var showRawMarkdown by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current
    var copiedNotice by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("biomechanical_report_view")
    ) {
        // Toggle view (Formatted Dashboard vs Raw Markdown)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .background(com.example.ui.theme.SophisticatedSurface, CircleShape)
                    .border(1.dp, com.example.ui.theme.SophisticatedBorder, CircleShape)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (!showRawMarkdown) com.example.ui.theme.SophisticatedPrimary else Color.Transparent)
                        .clickable { showRawMarkdown = false }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Kinematic Dashboard",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (!showRawMarkdown) com.example.ui.theme.SophisticatedOnPrimary else com.example.ui.theme.SophisticatedMuted
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (showRawMarkdown) com.example.ui.theme.SophisticatedPrimary else Color.Transparent)
                        .clickable { showRawMarkdown = true }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Raw Markdown",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (showRawMarkdown) com.example.ui.theme.SophisticatedOnPrimary else com.example.ui.theme.SophisticatedMuted
                    )
                }
            }

            OutlinedButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(report.rawMarkdown))
                    copiedNotice = true
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = com.example.ui.theme.SophisticatedPrimary),
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
                shape = CircleShape
            ) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copy Report", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (copiedNotice) "Copied!" else "Copy", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (showRawMarkdown) {
            // Raw Markdown Display
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "EXACT GEMINI SYSTEM PROMPT OUTPUT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = com.example.ui.theme.SophisticatedSecondary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = report.rawMarkdown,
                        color = com.example.ui.theme.SophisticatedTextPrimary,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        } else {
            // -------------------------------------------------------------
            // SECTION 1: MOVEMENT IDENTIFICATION
            // -------------------------------------------------------------
            SectionHeader(
                stepNumber = "1",
                title = "Movement Identification",
                icon = Icons.Default.FitnessCenter,
                accentColor = com.example.ui.theme.SophisticatedPrimary
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = report.movementName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = report.sportCategory,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = com.example.ui.theme.SophisticatedPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF2B2930), CircleShape)
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Biomechanical Standard Analysis",
                                fontSize = 11.sp,
                                color = com.example.ui.theme.SophisticatedMuted
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // -------------------------------------------------------------
            // SECTION 2: PHASE-BY-PHASE BREAKDOWN
            // -------------------------------------------------------------
            SectionHeader(
                stepNumber = "2",
                title = "Phase-by-Phase Breakdown",
                icon = Icons.Default.Timeline,
                accentColor = com.example.ui.theme.SophisticatedSecondary
            )
            report.phases.forEachIndexed { index, phase ->
                PhaseCard(phase = phase, index = index + 1)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            // -------------------------------------------------------------
            // SECTION 3: KINEMATIC ANALYSIS
            // -------------------------------------------------------------
            SectionHeader(
                stepNumber = "3",
                title = "Kinematic Analysis",
                icon = Icons.Default.AccessibilityNew,
                accentColor = com.example.ui.theme.SophisticatedPrimary
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Kinetic Chain Efficiency Score Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Kinetic Chain Transfer Efficiency",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                        Text(
                            text = "${report.kinematics.efficiencyScore}%",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (report.kinematics.efficiencyScore >= 85) com.example.ui.theme.SophisticatedGreen else com.example.ui.theme.SophisticatedAmber,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(report.kinematics.efficiencyScore / 100f)
                                .height(8.dp)
                                .background(
                                    Brush.horizontalGradient(listOf(com.example.ui.theme.SophisticatedPrimary, com.example.ui.theme.SophisticatedGreen)),
                                    CircleShape
                                )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    KinematicFactorItem(
                        label = "Joint Angles & Mobility",
                        details = report.kinematics.jointAngles,
                        iconColor = com.example.ui.theme.SophisticatedPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    KinematicFactorItem(
                        label = "Spinal Alignment & Core Stability",
                        details = report.kinematics.spinalAlignment,
                        iconColor = com.example.ui.theme.SophisticatedSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    KinematicFactorItem(
                        label = "Footwork & Base of Support",
                        details = report.kinematics.baseOfSupport,
                        iconColor = com.example.ui.theme.SophisticatedGreen
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    KinematicFactorItem(
                        label = "Kinetic Chain Sequencing",
                        details = report.kinematics.kineticChainEfficiency,
                        iconColor = com.example.ui.theme.SophisticatedPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // -------------------------------------------------------------
            // SECTION 4: INJURY RISK ASSESSMENT
            // -------------------------------------------------------------
            SectionHeader(
                stepNumber = "4",
                title = "Injury Risk Assessment",
                icon = Icons.Default.HealthAndSafety,
                accentColor = if (report.injuryAssessment.isSafe) com.example.ui.theme.SophisticatedGreen else com.example.ui.theme.SophisticatedRed
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (report.injuryAssessment.isSafe) com.example.ui.theme.SophisticatedGreen.copy(alpha = 0.5f) else com.example.ui.theme.SophisticatedRed.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (report.injuryAssessment.isSafe) Icons.Default.CheckCircle else Icons.Default.Warning,
                                contentDescription = "Risk status",
                                tint = if (report.injuryAssessment.isSafe) com.example.ui.theme.SophisticatedGreen else com.example.ui.theme.SophisticatedRed,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = report.injuryAssessment.riskLevel.label,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (report.injuryAssessment.isSafe) com.example.ui.theme.SophisticatedGreen else com.example.ui.theme.SophisticatedRed
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = report.injuryAssessment.detailedSummary,
                        fontSize = 13.sp,
                        color = com.example.ui.theme.SophisticatedTextPrimary,
                        lineHeight = 19.sp
                    )

                    if (report.injuryAssessment.detectedFlaws.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "FLAGGED MECHANICAL FLAWS:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = com.example.ui.theme.SophisticatedRed,
                            letterSpacing = 0.8.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        report.injuryAssessment.detectedFlaws.forEach { flaw ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                                    .background(com.example.ui.theme.SophisticatedRed.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                    .border(0.5.dp, com.example.ui.theme.SophisticatedRed.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(com.example.ui.theme.SophisticatedRed, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = flaw,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = com.example.ui.theme.SophisticatedRed
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // -------------------------------------------------------------
            // SECTION 5: CORRECTIVE CUES
            // -------------------------------------------------------------
            SectionHeader(
                stepNumber = "5",
                title = "Corrective Coaching Cues",
                icon = Icons.Default.LinearScale,
                accentColor = com.example.ui.theme.SophisticatedGreen
            )
            report.cues.forEach { cue ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                                .border(1.dp, com.example.ui.theme.SophisticatedPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${cue.number}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = com.example.ui.theme.SophisticatedPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = cue.cueText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            if (cue.targetArea.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Target Area: ${cue.targetArea}",
                                    fontSize = 11.sp,
                                    color = com.example.ui.theme.SophisticatedSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            if (cue.drillRecommendation.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Neuromuscular Drill: ${cue.drillRecommendation}",
                                    fontSize = 11.sp,
                                    color = com.example.ui.theme.SophisticatedMuted
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionHeader(
    stepNumber: String,
    title: String,
    icon: ImageVector,
    accentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                .border(1.dp, accentColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
fun PhaseCard(phase: KineticPhase, index: Int) {
    var expanded by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = phase.timeRange,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = com.example.ui.theme.SophisticatedSecondary,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = phase.phaseName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = com.example.ui.theme.SophisticatedMuted,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = phase.mechanics,
                        fontSize = 12.sp,
                        color = com.example.ui.theme.SophisticatedTextPrimary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun KinematicFactorItem(
    label: String,
    details: String,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2B2930), RoundedCornerShape(16.dp))
            .border(1.dp, com.example.ui.theme.SophisticatedBorder.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .padding(top = 4.dp)
                .background(iconColor, CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = iconColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = details,
                fontSize = 12.sp,
                color = com.example.ui.theme.SophisticatedTextPrimary,
                lineHeight = 17.sp
            )
        }
    }
}
