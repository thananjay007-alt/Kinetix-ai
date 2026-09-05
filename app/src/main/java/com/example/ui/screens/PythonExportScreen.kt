package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.IntegrationInstructions
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.api.GeminiBiomechanicalService
import com.example.data.exporter.PythonScriptExporter
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark

@Composable
fun PythonExportScreen(
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    var copiedScript by remember { mutableStateOf(false) }
    var copiedPrompt by remember { mutableStateOf(false) }

    val pythonCode = remember { PythonScriptExporter.generatePythonScript("athlete_clip.mp4") }
    val systemPrompt = remember { GeminiBiomechanicalService.BIOMECHANICAL_SYSTEM_INSTRUCTION }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(com.example.ui.theme.SophisticatedBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("python_export_screen")
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "AI STUDIO & PYTHON SDK",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = com.example.ui.theme.SophisticatedPrimary
                )
                Text(
                    text = "Developer Integration",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = (-0.5).sp,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                    .border(1.dp, com.example.ui.theme.SophisticatedBorder, CircleShape)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "SDK v0.8",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = com.example.ui.theme.SophisticatedPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // AI Studio Testing Quick Guide
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(com.example.ui.theme.SophisticatedPill, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.IntegrationInstructions, contentDescription = null, tint = com.example.ui.theme.SophisticatedSecondary, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "AI Studio System Prompt",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(systemPrompt))
                            copiedPrompt = true
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = com.example.ui.theme.SophisticatedSecondary),
                        border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SophisticatedBorder),
                        shape = CircleShape
                    ) {
                        Icon(if (copiedPrompt) Icons.Default.Check else Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (copiedPrompt) "Copied" else "Copy Prompt", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF141316), RoundedCornerShape(16.dp))
                        .border(1.dp, com.example.ui.theme.SophisticatedBorder, RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = systemPrompt,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = com.example.ui.theme.SophisticatedTextSecondary,
                        lineHeight = 16.sp,
                        maxLines = 8
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Studio testing instructions checklist
                Text(
                    text = "Google AI Studio Configuration:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = com.example.ui.theme.SophisticatedPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                StudioStep(step = "1", text = "Create a new 'System Instructions' prompt in AI Studio.")
                StudioStep(step = "2", text = "Select model: Gemini 1.5 Pro / Gemini Pro (for video reasoning).")
                StudioStep(step = "3", text = "Set Temperature: 0.2 (stops AI hallucination, ensures scientific accuracy).")
                StudioStep(step = "4", text = "Upload MP4 video, type 'Analyze this form', and click Run.")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Python Script Card
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(com.example.ui.theme.SophisticatedPill, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Code, contentDescription = null, tint = com.example.ui.theme.SophisticatedPrimary, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "analyze_movement.py",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Button(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(pythonCode))
                            copiedScript = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = com.example.ui.theme.SophisticatedPrimary,
                            contentColor = com.example.ui.theme.SophisticatedOnPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(if (copiedScript) Icons.Default.Check else Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (copiedScript) "Copied Code" else "Copy Script", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF141316), RoundedCornerShape(16.dp))
                        .border(1.dp, com.example.ui.theme.SophisticatedBorder, RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = pythonCode,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = com.example.ui.theme.SophisticatedPrimary,
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Terminal Execution instructions
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2B2930), RoundedCornerShape(16.dp))
                        .border(1.dp, com.example.ui.theme.SophisticatedBorder.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "QUICK TERMINAL LAUNCH:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = com.example.ui.theme.SophisticatedSecondary,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "pip install google-generativeai\nexport GEMINI_API_KEY=\"your_key\"\npython analyze_movement.py sprint_start.mp4",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun StudioStep(step: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(com.example.ui.theme.SophisticatedPill, CircleShape)
                .border(1.dp, com.example.ui.theme.SophisticatedGreen.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = step, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = com.example.ui.theme.SophisticatedGreen)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, fontSize = 12.sp, color = com.example.ui.theme.SophisticatedTextPrimary)
    }
}
