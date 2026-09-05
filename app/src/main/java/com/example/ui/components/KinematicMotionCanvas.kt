package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.EmeraldSafe
import kotlin.math.sin

@Composable
fun KinematicMotionCanvas(
    movementTitle: String,
    category: String,
    modifier: Modifier = Modifier
) {
    var manualProgress by remember { mutableFloatStateOf(0.5f) }
    var isAutoPlaying by remember { androidx.compose.runtime.mutableStateOf(true) }

    val infiniteTransition = rememberInfiniteTransition(label = "motionLoop")
    val autoProgress by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "autoMotion"
    )

    val currentPhaseProgress = if (isAutoPlaying) autoProgress else manualProgress

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(com.example.ui.theme.SophisticatedSurface, RoundedCornerShape(28.dp))
            .border(1.dp, com.example.ui.theme.SophisticatedBorder, RoundedCornerShape(28.dp))
            .padding(14.dp)
            .testTag("kinematic_motion_canvas")
    ) {
        Column {
            // Header stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ELITE BIOMECHANICS",
                        color = com.example.ui.theme.SophisticatedPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = movementTitle,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${(currentPhaseProgress * 100).toInt()}% PHASE",
                        color = com.example.ui.theme.SophisticatedPrimary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Canvas Drawing with Sophisticated Dark visual overlays
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF141316), RoundedCornerShape(22.dp))
                    .border(1.dp, com.example.ui.theme.SophisticatedBorder, RoundedCornerShape(22.dp))
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height
                    val cx = w / 2f

                    // Subtle spatial calibration grid
                    val gridColor = Color(0xFF2B2930).copy(alpha = 0.5f)
                    for (x in 20..w.toInt() step 40) {
                        drawLine(gridColor, Offset(x.toFloat(), 0f), Offset(x.toFloat(), h), 0.8f)
                    }
                    for (y in 20..h.toInt() step 40) {
                        drawLine(gridColor, Offset(0f, y.toFloat()), Offset(w, y.toFloat()), 0.8f)
                    }

                    // Ground Plane / Base of Support
                    val groundY = h - 24.dp.toPx()
                    drawLine(
                        color = com.example.ui.theme.SophisticatedBorder,
                        start = Offset(cx - 100.dp.toPx(), groundY),
                        end = Offset(cx + 100.dp.toPx(), groundY),
                        strokeWidth = 3f,
                        cap = StrokeCap.Round
                    )

                    // Base of support boundary highlight
                    drawLine(
                        color = com.example.ui.theme.SophisticatedGreen.copy(alpha = 0.8f),
                        start = Offset(cx - 40.dp.toPx(), groundY),
                        end = Offset(cx + 40.dp.toPx(), groundY),
                        strokeWidth = 5f,
                        cap = StrokeCap.Round
                    )

                    // Plumb Line / Center of Mass (Vertical dashed vector)
                    drawLine(
                        color = com.example.ui.theme.SophisticatedPrimary.copy(alpha = 0.4f),
                        start = Offset(cx, 16.dp.toPx()),
                        end = Offset(cx, groundY),
                        strokeWidth = 1.5f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )

                    // Dynamic kinematic skeleton simulation depending on progress
                    val squatDepth = currentPhaseProgress * 36.dp.toPx()
                    val hipY = h * 0.52f + squatDepth
                    val hipX = cx - (currentPhaseProgress * 12.dp.toPx())

                    val headY = h * 0.22f + (squatDepth * 0.8f)
                    val torsoTiltX = cx + (currentPhaseProgress * 8.dp.toPx())
                    val shoulderY = headY + 20.dp.toPx()

                    val kneeY = h * 0.72f + (squatDepth * 0.35f)
                    val kneeX = cx + 28.dp.toPx() + (currentPhaseProgress * 10.dp.toPx())

                    val footX = cx + 22.dp.toPx()
                    val footY = groundY

                    val backFootX = cx - 24.dp.toPx()
                    val backKneeX = cx - 8.dp.toPx() - (currentPhaseProgress * 8.dp.toPx())

                    // Spine / Torso link
                    drawLine(
                        brush = Brush.linearGradient(listOf(com.example.ui.theme.SophisticatedPrimary, com.example.ui.theme.SophisticatedSecondary)),
                        start = Offset(torsoTiltX, shoulderY),
                        end = Offset(hipX, hipY),
                        strokeWidth = 6f,
                        cap = StrokeCap.Round
                    )

                    // Head
                    drawCircle(
                        color = com.example.ui.theme.SophisticatedTextPrimary,
                        radius = 11.dp.toPx(),
                        center = Offset(torsoTiltX, headY)
                    )

                    // Arms / Upper kinetic segment
                    val elbowX = torsoTiltX + 22.dp.toPx()
                    val elbowY = shoulderY + 12.dp.toPx()
                    val handX = torsoTiltX + 36.dp.toPx() - (currentPhaseProgress * 15.dp.toPx())
                    val handY = shoulderY + (currentPhaseProgress * 10.dp.toPx())

                    drawLine(com.example.ui.theme.SophisticatedPrimary, Offset(torsoTiltX, shoulderY), Offset(elbowX, elbowY), 4.5f, StrokeCap.Round)
                    drawLine(com.example.ui.theme.SophisticatedPrimary, Offset(elbowX, elbowY), Offset(handX, handY), 4.5f, StrokeCap.Round)

                    // Legs / Lower kinetic chain (Front leg)
                    drawLine(com.example.ui.theme.SophisticatedSecondary, Offset(hipX, hipY), Offset(kneeX, kneeY), 5.5f, StrokeCap.Round)
                    drawLine(com.example.ui.theme.SophisticatedSecondary, Offset(kneeX, kneeY), Offset(footX, footY), 5.5f, StrokeCap.Round)

                    // Back leg
                    drawLine(com.example.ui.theme.SophisticatedSecondary.copy(alpha = 0.5f), Offset(hipX, hipY), Offset(backKneeX, kneeY), 4f, StrokeCap.Round)
                    drawLine(com.example.ui.theme.SophisticatedSecondary.copy(alpha = 0.5f), Offset(backKneeX, kneeY), Offset(backFootX, footY), 4f, StrokeCap.Round)

                    // Joint Nodes (Kinematic markers)
                    val jointNodes = listOf(
                        Offset(torsoTiltX, shoulderY) to com.example.ui.theme.SophisticatedPrimary,
                        Offset(elbowX, elbowY) to com.example.ui.theme.SophisticatedPrimary,
                        Offset(handX, handY) to Color.White,
                        Offset(hipX, hipY) to com.example.ui.theme.SophisticatedSecondary,
                        Offset(kneeX, kneeY) to com.example.ui.theme.SophisticatedSecondary,
                        Offset(footX, footY) to com.example.ui.theme.SophisticatedGreen,
                        Offset(backKneeX, kneeY) to com.example.ui.theme.SophisticatedSecondary.copy(alpha = 0.7f),
                        Offset(backFootX, footY) to com.example.ui.theme.SophisticatedGreen.copy(alpha = 0.7f)
                    )

                    for ((pt, col) in jointNodes) {
                        drawCircle(Color.White, radius = 5.5f, center = pt)
                        drawCircle(col, radius = 3.5f, center = pt)
                    }
                }

                // Top-Left Recording Pill (from Design HTML)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .background(Color(0x99000000), CircleShape)
                        .border(1.dp, Color(0x26FFFFFF), CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .background(Color(0xFFEF4444), CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "REC: 00:04:12",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xE6FFFFFF),
                            letterSpacing = 0.8.sp
                        )
                    }
                }

                // Center Biomechanical Angle Indicator (from Design HTML)
                val hipAngle = (140 - (currentPhaseProgress * 45)).toInt()
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(88.dp)
                        .border(1.5.dp, com.example.ui.theme.SophisticatedPrimary.copy(alpha = 0.35f), CircleShape)
                ) {
                    // Axis line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .align(Alignment.Center)
                            .background(com.example.ui.theme.SophisticatedPrimary.copy(alpha = 0.4f))
                    )
                    // Angle chip
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 10.dp, start = 8.dp)
                            .background(com.example.ui.theme.SophisticatedPrimary, RoundedCornerShape(4.dp))
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "$hipAngle.2°",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1C1B1F)
                        )
                    }
                }

                // Bottom Left & Right HUD (from Design HTML)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Kinetic Chain Efficiency pill
                    Box(
                        modifier = Modifier
                            .background(Color(0xCC000000), RoundedCornerShape(12.dp))
                            .border(1.dp, Color(0x1AFFFFFF), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column {
                            Text(
                                text = "KINETIC CHAIN",
                                fontSize = 8.sp,
                                color = com.example.ui.theme.SophisticatedMuted,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                            Text(
                                text = "Efficiency: 94.2%",
                                fontSize = 11.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Kinetic Chain Power Bars (from Design HTML: 4 vertical bars in #D0BCFF)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val barHeight1 = 12.dp + (currentPhaseProgress * 6).dp
                        val barHeight2 = 22.dp - (currentPhaseProgress * 4).dp
                        val barHeight3 = 10.dp + (currentPhaseProgress * 8).dp
                        val barHeight4 = 18.dp

                        Box(modifier = Modifier.width(4.dp).height(barHeight1).background(com.example.ui.theme.SophisticatedPrimary, RoundedCornerShape(2.dp)))
                        Box(modifier = Modifier.width(4.dp).height(barHeight2).background(com.example.ui.theme.SophisticatedPrimary, RoundedCornerShape(2.dp)))
                        Box(modifier = Modifier.width(4.dp).height(barHeight3).background(com.example.ui.theme.SophisticatedPrimary, RoundedCornerShape(2.dp)))
                        Box(modifier = Modifier.width(4.dp).height(barHeight4).background(com.example.ui.theme.SophisticatedPrimary, RoundedCornerShape(2.dp)))
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Playback / Scrubber Control
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.material3.IconButton(
                    onClick = { isAutoPlaying = !isAutoPlaying },
                    modifier = Modifier
                        .size(36.dp)
                        .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                ) {
                    Icon(
                        imageVector = if (isAutoPlaying) Icons.Default.Speed else Icons.Default.PlayArrow,
                        contentDescription = "Toggle animation",
                        tint = com.example.ui.theme.SophisticatedPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Slider(
                    value = currentPhaseProgress,
                    onValueChange = {
                        isAutoPlaying = false
                        manualProgress = it
                    },
                    modifier = Modifier.weight(1f),
                    colors = SliderDefaults.colors(
                        thumbColor = com.example.ui.theme.SophisticatedPrimary,
                        activeTrackColor = com.example.ui.theme.SophisticatedPrimary,
                        inactiveTrackColor = com.example.ui.theme.SophisticatedBorder
                    )
                )
            }
        }
    }
}
