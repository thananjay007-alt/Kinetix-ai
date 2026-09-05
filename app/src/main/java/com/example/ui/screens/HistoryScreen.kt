package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AnalysisEntity
import com.example.data.model.RiskSeverity
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    historyList: List<AnalysisEntity>,
    onSelectReport: (AnalysisEntity) -> Unit,
    onToggleFavorite: (AnalysisEntity) -> Unit,
    onDeleteReport: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(com.example.ui.theme.SophisticatedBackground)
            .padding(16.dp)
            .testTag("history_screen")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "BIOMECHANICAL ARCHIVE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = com.example.ui.theme.SophisticatedPrimary
                )
                Text(
                    text = "Saved Movement Analyses",
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
                    text = "${historyList.size} RECORDED",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = com.example.ui.theme.SophisticatedPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (historyList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(com.example.ui.theme.SophisticatedSurface, CircleShape)
                            .border(1.dp, com.example.ui.theme.SophisticatedBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = com.example.ui.theme.SophisticatedMuted,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Movement Analyses Yet",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Run an analysis on any athletic movement preset or upload an athlete's video.",
                        fontSize = 13.sp,
                        color = com.example.ui.theme.SophisticatedMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(historyList, key = { it.id }) { item ->
                    HistoryItemCard(
                        item = item,
                        formattedDate = dateFormat.format(Date(item.timestamp)),
                        onClick = { onSelectReport(item) },
                        onToggleFav = { onToggleFavorite(item) },
                        onDelete = { onDeleteReport(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(
    item: AnalysisEntity,
    formattedDate: String,
    onClick: () -> Unit,
    onToggleFav: () -> Unit,
    onDelete: () -> Unit
) {
    val riskColor = when (item.injuryRiskLevel) {
        "SAFE" -> com.example.ui.theme.SophisticatedGreen
        "LOW" -> com.example.ui.theme.SophisticatedGreen
        "MODERATE" -> com.example.ui.theme.SophisticatedAmber
        "HIGH" -> com.example.ui.theme.SophisticatedRed
        else -> com.example.ui.theme.SophisticatedPrimary
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("history_card_${item.id}"),
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
                            .background(riskColor.copy(alpha = 0.15f), CircleShape)
                            .border(0.8.dp, riskColor.copy(alpha = 0.4f), CircleShape)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = item.injuryRiskLevel,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = riskColor,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.sportCategory,
                        fontSize = 11.sp,
                        color = com.example.ui.theme.SophisticatedMuted
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onToggleFav, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = if (item.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Favorite",
                            tint = if (item.isFavorite) com.example.ui.theme.SophisticatedAmber else com.example.ui.theme.SophisticatedMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete",
                            tint = com.example.ui.theme.SophisticatedMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.movementName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )

            if (item.keyCuesSummary.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.keyCuesSummary,
                    fontSize = 12.sp,
                    color = com.example.ui.theme.SophisticatedTextPrimary,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedDate,
                    fontSize = 11.sp,
                    color = com.example.ui.theme.SophisticatedMuted,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "Efficiency: ${item.efficiencyScore}%",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = com.example.ui.theme.SophisticatedPrimary,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}
