package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabtrainer.viewmodel.StudyViewModel

@Composable
fun TypingModePage(
    viewModel: StudyViewModel,
    navController: NavController
) {
    val session = viewModel.studySession ?: return
    val chosenCardSide = viewModel.studyMode?.cardSide
    val currentCard = session.cards.getOrNull(session.currentIndex)
    var showAnswer by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }

    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    val correctAnswer = if (chosenCardSide == 0) {
        currentCard?.backSide ?: "keine Karte"
    } else {
        currentCard?.frontSide ?: "keine Karte"
    }
    val isLastCard = session.currentIndex == session.cards.lastIndex

    LaunchedEffect(session.currentIndex) {
        if (session.currentIndex >= session.cards.size) {
            navController.navigate("results") {
                popUpTo("classic_mode") {
                    inclusive = true
                }
            }
        }
    }

    if (currentCard == null) {
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(
                Color(0xFFA7C281)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (chosenCardSide == 0) {
                currentCard.frontSide
            } else {
                currentCard.backSide
            },
            color = Color.Black,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                bottom = 20.dp
            )
        )
        Spacer(modifier = Modifier.height(11.dp))

        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Gebe deine Antwort ein") },
        )

        Spacer(modifier = Modifier.height(11.dp))

        if (showAnswer) {
            if (isCorrect == true) {
                Text(
                    text = "Deine Antwort ist richtig",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (isLastCard) {
                            viewModel.correctAnswer()
                            navController.navigate("results") {
                                popUpTo("fill_in_mode") {
                                    inclusive = true
                                }
                            }
                        } else {
                            viewModel.correctAnswer()
                            userInput = ""
                            showAnswer = false
                            isCorrect = null
                        }
                    }
                ) {
                    Text(
                        text = if (isLastCard) "Ergebnis anzeigen" else "Weiter",
                        color = Color.Black
                    )
                }
            } else {
                Text(
                    text = "Deine Antwort ist leider falsch.",
                    color = Color(0xFFF44336),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "richitge Antwort: $correctAnswer",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(11.dp))

                Button(
                    onClick = {
                        if (isLastCard) {
                            viewModel.wrongAnswer()
                            navController.navigate("results") {
                                popUpTo("fill_in_mode") {
                                    inclusive = true
                                }
                            }
                        } else {
                            viewModel.wrongAnswer()
                            userInput = ""
                            showAnswer = false
                            isCorrect = null
                        }
                    }
                ) {
                    Text(
                        text = if (isLastCard) "Ergebnis anzeigen" else "Weiter",
                        color = Color.Black
                    )
                }
            }
        } else {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6F943C)
                ),
                onClick = {
                    isCorrect = userInput.trim() == correctAnswer.trim()
                    showAnswer = true
                },
                enabled = userInput.isNotBlank()
            ) {
                Text(
                    text = "Lösung anzeigen",
                    color = Color.Black
                )
            }
        }
    }
}