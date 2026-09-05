package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MovementPresets
import com.example.data.model.PresetMovement
import com.example.ui.components.KinematicMotionCanvas
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.CoralRisk
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark
import com.example.viewmodel.KinematicUiState

@Composable
fun HomeScreen(
    uiState: KinematicUiState,
    onSelectPreset: (PresetMovement) -> Unit,
    onSelectVideo: (Uri) -> Unit,
    onClearVideo: () -> Unit,
    onRunAnalysis: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Android Photo/Video Picker (Zero storage permission required)
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onSelectVideo(uri)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("home_screen")
    ) {
        // Top Header Banner (from Sophisticated Dark Design)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "ELITE BIOMECHANICS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = com.example.ui.theme.SophisticatedPrimary
                )
                Text(
                    text = "Movement Analysis",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = (-0.5).sp,
                    color = Color.White
                )
            }

            // Athlete/User Avatar badge from design HTML
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                    .border(1.dp, com.example.ui.theme.SophisticatedBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JD",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = com.example.ui.theme.SophisticatedPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Movement ID & Risk Level Summary row (from Sophisticated Dark Design HTML)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "MOVEMENT ID",
                        fontSize = 10.sp,
                        color = com.example.ui.theme.SophisticatedTextSecondary,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (uiState.customVideoUri != null) "Custom Athlete Video" else uiState.selectedPreset.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "RISK LEVEL",
                        fontSize = 10.sp,
                        color = com.example.ui.theme.SophisticatedTextSecondary,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Low / Optimized",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = com.example.ui.theme.SophisticatedGreen
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // API Key Status Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = null,
                        tint = if (uiState.hasApiKey) com.example.ui.theme.SophisticatedGreen else com.example.ui.theme.SophisticatedPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (uiState.hasApiKey) "Gemini Pro Multimodal Active" else "Biomechanical Scientific Presets Active",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = if (uiState.hasApiKey) "Direct video reasoning connected (Temp: 0.2)" else "Set GEMINI_API_KEY in Secrets for custom video upload",
                            fontSize = 10.sp,
                            color = com.example.ui.theme.SophisticatedMuted
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .background(
                            if (uiState.hasApiKey) com.example.ui.theme.SophisticatedGreen.copy(alpha = 0.2f) else com.example.ui.theme.SophisticatedPill,
                            CircleShape
                        )
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (uiState.hasApiKey) "LIVE" else "PREVIEW",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.hasApiKey) com.example.ui.theme.SophisticatedGreen else com.example.ui.theme.SophisticatedPrimary,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section Title: Movement Presets
        Text(
            text = "ATHLETIC MOVEMENT PRESETS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = com.example.ui.theme.SophisticatedPrimary,
            letterSpacing = 1.2.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Horizontal Carousel of Movement Presets
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MovementPresets.presets.forEach { preset ->
                val isSelected = uiState.selectedPreset.id == preset.id && uiState.customVideoUri == null
                PresetCard(
                    preset = preset,
                    isSelected = isSelected,
                    onClick = { onSelectPreset(preset) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Custom Video Upload Section
        Text(
            text = "OR ANALYZE ATHLETE VIDEO",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = com.example.ui.theme.SophisticatedSecondary,
            letterSpacing = 1.2.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("video_upload_card"),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (uiState.customVideoUri != null) com.example.ui.theme.SophisticatedPrimary else com.example.ui.theme.SophisticatedBorder
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                if (uiState.customVideoUri != null) {
                    // Selected Video View
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(com.example.ui.theme.SophisticatedPill, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.VideoFile, contentDescription = null, tint = com.example.ui.theme.SophisticatedPrimary, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Custom Athlete Video Selected",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                                Text(
                                    text = uiState.customVideoUri.lastPathSegment ?: "athlete_clip.mp4",
                                    fontSize = 11.sp,
                                    color = com.example.ui.theme.SophisticatedPrimary,
                                    maxLines = 1
                                )
                            }
                        }
                        IconButton(onClick = onClearVideo) {
                            Icon(Icons.Default.Close, contentDescription = "Remove video", tint = com.example.ui.theme.SophisticatedTextSecondary)
                        }
                    }
                } else {
                    // Upload Prompt View
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(com.example.ui.theme.SophisticatedPill, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Videocam, contentDescription = null, tint = com.example.ui.theme.SophisticatedPrimary, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Upload Video (MP4)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                                Text(
                                    text = "Squat, tennis smash, bowling, sprint, etc.",
                                    fontSize = 11.sp,
                                    color = com.example.ui.theme.SophisticatedMuted
                                )
                            }
                        }

                        Button(
                            onClick = {
                                videoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = com.example.ui.theme.SophisticatedPrimary,
                                contentColor = com.example.ui.theme.SophisticatedOnPrimary
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("upload_video_button")
                        ) {
                            Text("Select Video", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kinematic Motion Canvas
        KinematicMotionCanvas(
            movementTitle = if (uiState.customVideoUri != null) "Custom Athlete Video" else uiState.selectedPreset.title,
            category = if (uiState.customVideoUri != null) "Video Kinematics" else uiState.selectedPreset.category
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error message banner
        if (uiState.errorMessage != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0x33EF4444)),
                border = androidx.compose.foundation.BorderStroke(1.dp, CoralRisk),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = uiState.errorMessage,
                        fontSize = 12.sp,
                        color = Color(0xFFFCA5A5),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismissError) {
                        Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Analysis Execution Status or Trigger Button
        if (uiState.isAnalyzing) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.SophisticatedSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedPrimary),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = com.example.ui.theme.SophisticatedPrimary,
                        modifier = Modifier.size(28.dp),
                        strokeWidth = 3.dp
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Analyzing Biomechanics...",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = com.example.ui.theme.SophisticatedPrimary
                        )
                        Text(
                            text = uiState.analysisStatusText,
                            fontSize = 11.sp,
                            color = com.example.ui.theme.SophisticatedMuted
                        )
                    }
                }
            }
        } else {
            Button(
                onClick = onRunAnalysis,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("run_analysis_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.ui.theme.SophisticatedPrimary,
                    contentColor = com.example.ui.theme.SophisticatedOnPrimary
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ANALYZE FORM WITH GEMINI PRO (TEMP 0.2)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun PresetCard(
    preset: PresetMovement,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .clickable(onClick = onClick)
            .testTag("preset_${preset.id}"),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) com.example.ui.theme.SophisticatedPill else com.example.ui.theme.SophisticatedSurface
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.2.dp,
            if (isSelected) com.example.ui.theme.SophisticatedPrimary else com.example.ui.theme.SophisticatedBorder
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF2B2930), RoundedCornerShape(8.dp))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = preset.category,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = com.example.ui.theme.SophisticatedPrimary
                    )
                }

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(com.example.ui.theme.SophisticatedPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = com.example.ui.theme.SophisticatedOnPrimary, modifier = Modifier.size(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = preset.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 2,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = preset.subtitle,
                fontSize = 10.sp,
                color = com.example.ui.theme.SophisticatedMuted,
                maxLines = 2,
                lineHeight = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "⏱ ${preset.durationSeconds} • ${preset.keyJointsAnalyzed.size} Kinetic Links",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = com.example.ui.theme.SophisticatedSecondary
            )
        }
    }
}
