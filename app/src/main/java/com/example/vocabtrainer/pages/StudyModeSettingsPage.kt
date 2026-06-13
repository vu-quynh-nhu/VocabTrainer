package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabtrainer.screen.components.SegmentedButton
import com.example.vocabtrainer.viewmodel.StudyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyModeSettingsPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: StudyViewModel
) {
    val difficultyOptions = listOf("leicht", "mittel", "schwer")
    val timedOptions = listOf("Nein", "Ja")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "go back",
                        )
                    }
                }
            )
        }
    ) {
        contentPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = Color.Black
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (viewModel.hasDifficultyMode) {
                    Text(
                        text = "Schwierigkeitsgrad",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    SegmentedButton(
                        options = difficultyOptions,
                        selectedIndex = viewModel.difficultyIndex,
                        onSelectedIndex = {
                            viewModel.setDifficultyMode(it)
                        }
                    )

                    Spacer(modifier = Modifier.size(12.dp))
                }

                Text(
                    text = "Auf Zeit",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                SegmentedButton(
                    options = timedOptions,
                    selectedIndex = viewModel.timedIndex,
                    onSelectedIndex = {
                        viewModel.setIsTimed(it)
                    }
                )

                Spacer(modifier = Modifier.size(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = {
                            when (viewModel.selectedStudyModePage) {
                                "classic_mode" -> navController.navigate("classic_mode") {
                                    popUpTo("study_mode_settings") {
                                        inclusive = true
                                    }
                                }
                                "typing_mode" -> navController.navigate("typing_mode")
                                "fill_in_mode" -> navController.navigate("fill_in_mode")
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7FA34A)
                        )
                    ) {
                        Text(
                            text = "Start"
                        )
                    }
                }
            }
        }
    }
}