package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabtrainer.viewmodel.StudyViewModel

@Composable
fun StudyResultPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    studyViewModel: StudyViewModel
) {
    val session = studyViewModel.studySession ?: return
    val percentageScore = session.correctAnswer * 100 / session.cards.size

    val resultText = when {
        percentageScore >= 95 -> "Sehr gut!"
        percentageScore >= 75 -> "Gut gemacht, weiter so!"
        percentageScore >= 50 -> "Du bist auf dem richtigen Weg!"
        else -> "Übung macht den Meister!"
    }

    Column(
        modifier = modifier.fillMaxSize()
            .background(
                Color(0xFFA7C281)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .size(width = 400.dp, height = 270.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                    24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(
                    12.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "- Ergebnis -",
                    color = Color.Black,
                    fontSize = 20.sp,
                )

                Text(
                    text = resultText,
                    color = Color.Black,
                    fontSize = 22.sp,
                )

                Text(
                    text = "richtige Antworten: ${session.correctAnswer}",
                    color = Color.Black,
                    fontSize = 16.sp,
                )

                Text(
                    text = "falsche Antworten: ${session.wrongAnswer}",
                    color = Color.Black,
                    fontSize = 16.sp,
                )

                if (session.wrongCards.isNotEmpty() &&
                    studyViewModel.selectedStudyModePage == "classic_mode") {
                    Text("Möchtest du die falsch beantworteten Karten wiederholen?")

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(
                            top = 20.dp
                        )
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
                            Text(
                                text = "Schließen",
                                color = Color.White
                            )
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
                            Text(
                                text = "Ja",
                                color = Color.White
                            )
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
                                Text(
                                    text = "Nochmal versuchen",
                                    color = Color.White
                                )
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
                            Text(
                                text = "Schließen",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}