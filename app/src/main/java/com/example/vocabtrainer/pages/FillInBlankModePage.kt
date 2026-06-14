package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabtrainer.viewmodel.StudyViewModel

@Composable
fun FillInBlankModePage(
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
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (!showAnswer) {
                if (chosenCardSide == 0) {
                    currentCard.frontSide
                } else {
                    currentCard.backSide
                }
            } else {
                if (chosenCardSide == 0) {
                    currentCard.backSide
                } else {
                    currentCard.frontSide
                }
            },
            color = Color.Black,
            fontSize = 25.sp
        )

        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Gebe deine Antwort ein") },
            isError = userInput.isBlank()
        )

        if (showAnswer) {
            if (isCorrect == true) {
                Text(
                    text = "Deine Antwort ist richtig",
                    color = Color(0xFF8EAE5B)
                )

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
                    Text(if (isLastCard) "Ergebnis anzeigen" else "Weiter")
                }
            } else {
                Text(
                    text = "Deine Antwort ist leider falsch.",
                    color = Color(0xFFF44336)
                )

                Text(
                    text = "Die richitge Antwort wäre: $correctAnswer",
                    color = Color.Black
                )
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
                    Text(if (isLastCard) "Ergebnis anzeigen" else "Weiter")
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
                }
            ) {
                Text(
                    text = "Lösung anzeigen",
                    color = Color.Black
                )
            }
        }
    }
}