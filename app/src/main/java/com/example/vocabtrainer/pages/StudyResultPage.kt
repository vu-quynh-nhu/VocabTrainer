package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vocabtrainer.viewmodel.StudyViewModel

@Composable
fun StudyResultPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    studyViewModel: StudyViewModel
) {
    val session = studyViewModel.studySession ?: return

    Column(
        modifier = modifier.fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4F6B24),
                        Color(0xFF5E7F2C),
                        Color(0xFF6F943C),
                        Color(0xFF7FA34A),
                        Color(0xFF8EAE5B),
                        Color(0xFF9CBD72),
                        Color(0xFFA7C281),
                        Color(0xFFACC587),
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("richtige Antworten: ${session.correctAnswer}")
        Text("falsche Antworten: ${session.wrongAnswer}")

        if (session.wrongCards.isNotEmpty() &&
            studyViewModel.selectedStudyModePage == "classic_mode") {
            Text("Möchtest du die falsch beantworteten Karten wiederholen?")

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("main") {
                            popUpTo("main") {
                                inclusive = false
                            }
                        }
                    }
                ) {
                    Text("Schließen")
                }

                Button(
                    onClick = {
                        studyViewModel.repeatWrongCards()
                        navController.navigate("classic_mode") {
                            popUpTo("results") {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("Ja")
                }
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (studyViewModel.selectedStudyModePage != "classic_mode") {
                    Button(
                        onClick = {
                            studyViewModel.restartSession()

                            when (studyViewModel.selectedStudyModePage) {
                                "typing_mode" -> navController.navigate("typing_mode") {
                                    popUpTo("results") {
                                        inclusive = true
                                    }
                                }

                                "fill_in_mode" -> navController.navigate("fill_in_mode") {
                                    popUpTo("results") {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Nochmal versuchen")
                    }
                }

                Button(
                    onClick = {
                        navController.navigate("main") {
                            popUpTo("main") {
                                inclusive = false
                            }
                        }
                    }
                ) {
                    Text("Schließen")
                }
            }
        }
    }
}