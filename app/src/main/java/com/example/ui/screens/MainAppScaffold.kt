package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ReportView
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.TextSecondaryDark
import com.example.viewmodel.KinematicViewModel

@Composable
fun MainAppScaffold(
    viewModel: KinematicViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val historyList by viewModel.historyList.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBg,
        bottomBar = {
            NavigationBar(
                containerColor = com.example.ui.theme.SophisticatedSurface,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .testTag("main_bottom_nav")
                    .border(
                        1.dp,
                        com.example.ui.theme.SophisticatedBorder
                    )
            ) {
                NavigationBarItem(
                    selected = uiState.activeTab == 0,
                    onClick = { viewModel.selectTab(0) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = "Analyze",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Analyze",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.activeTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = com.example.ui.theme.SophisticatedPrimary,
                        selectedTextColor = com.example.ui.theme.SophisticatedPrimary,
                        indicatorColor = com.example.ui.theme.SophisticatedPill,
                        unselectedIconColor = com.example.ui.theme.SophisticatedTextSecondary,
                        unselectedTextColor = com.example.ui.theme.SophisticatedTextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_analyze")
                )

                NavigationBarItem(
                    selected = uiState.activeTab == 1,
                    onClick = { viewModel.selectTab(1) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Assessment,
                            contentDescription = "Report",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Report",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.activeTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = com.example.ui.theme.SophisticatedPrimary,
                        selectedTextColor = com.example.ui.theme.SophisticatedPrimary,
                        indicatorColor = com.example.ui.theme.SophisticatedPill,
                        unselectedIconColor = com.example.ui.theme.SophisticatedTextSecondary,
                        unselectedTextColor = com.example.ui.theme.SophisticatedTextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_report")
                )

                NavigationBarItem(
                    selected = uiState.activeTab == 2,
                    onClick = { viewModel.selectTab(2) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Archive",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Archive",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.activeTab == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = com.example.ui.theme.SophisticatedPrimary,
                        selectedTextColor = com.example.ui.theme.SophisticatedPrimary,
                        indicatorColor = com.example.ui.theme.SophisticatedPill,
                        unselectedIconColor = com.example.ui.theme.SophisticatedTextSecondary,
                        unselectedTextColor = com.example.ui.theme.SophisticatedTextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_history")
                )

                NavigationBarItem(
                    selected = uiState.activeTab == 3,
                    onClick = { viewModel.selectTab(3) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Python Export",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Python Export",
                            fontSize = 11.sp,
                            fontWeight = if (uiState.activeTab == 3) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = com.example.ui.theme.SophisticatedPrimary,
                        selectedTextColor = com.example.ui.theme.SophisticatedPrimary,
                        indicatorColor = com.example.ui.theme.SophisticatedPill,
                        unselectedIconColor = com.example.ui.theme.SophisticatedTextSecondary,
                        unselectedTextColor = com.example.ui.theme.SophisticatedTextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_export")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.activeTab) {
                0 -> HomeScreen(
                    uiState = uiState,
                    onSelectPreset = { viewModel.selectPreset(it) },
                    onSelectVideo = { viewModel.selectCustomVideo(it) },
                    onClearVideo = { viewModel.clearCustomVideo() },
                    onRunAnalysis = { viewModel.analyzeCurrentSelection() },
                    onDismissError = { viewModel.dismissError() }
                )
                1 -> {
                    val report = uiState.activeReport
                    if (report != null) {
                        ReportView(report = report)
                    } else {
                        HomeScreen(
                            uiState = uiState,
                            onSelectPreset = { viewModel.selectPreset(it) },
                            onSelectVideo = { viewModel.selectCustomVideo(it) },
                            onClearVideo = { viewModel.clearCustomVideo() },
                            onRunAnalysis = { viewModel.analyzeCurrentSelection() },
                            onDismissError = { viewModel.dismissError() }
                        )
                    }
                }
                2 -> HistoryScreen(
                    historyList = historyList,
                    onSelectReport = { viewModel.loadHistoryReport(it) },
                    onToggleFavorite = { viewModel.toggleFavorite(it.id, it.isFavorite) },
                    onDeleteReport = { viewModel.deleteHistory(it) }
                )
                3 -> PythonExportScreen()
            }
        }
    }
}
