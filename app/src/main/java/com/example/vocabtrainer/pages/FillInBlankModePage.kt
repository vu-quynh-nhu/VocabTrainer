package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    val correctAnswer = if (chosenCardSide == 0) {
        currentCard?.frontSide ?: "keine Karte"
    } else {
        currentCard?.backSide ?: "keine Karte"
    }

    val word = if (chosenCardSide == 0) {
        currentCard?.frontSide ?: "keine Karte"
    } else {
        currentCard?.backSide ?: "keine Karte"
    }
    val blankWord = remember(
        session.currentIndex,
        word,
        viewModel.studyMode?.difficulty
    ) {
        viewModel.blankWords(
            word = word,
            viewModel.studyMode?.difficulty ?: 0
        )
    }
    val gapCount = blankWord.count {
        it == '*'
    }
    var userInput by remember {
        mutableStateOf(List(gapCount) {""} )
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

    LaunchedEffect(blankWord) {
        userInput = List(gapCount) {""}
    }

    if (currentCard == null) {
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFA7C281)
            )
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var gapIndex = 0

            blankWord.forEach { ch ->
                if (ch == '*') {
                    val currentGap = gapIndex

                    OutlinedTextField(
                        value = userInput[currentGap],
                        onValueChange = {
                            userInput = userInput.toMutableList().apply {
                                this[currentGap] = it.take(1)
                            }
                        },
                        modifier = Modifier.width(60.dp),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFF5E7F2C),
                            unfocusedContainerColor = Color(0xFF5E7F2C),
                            focusedBorderColor = Color(0xFF5E7F2C),
                            unfocusedBorderColor = Color(0xFF5E7F2C),
                            cursorColor = Color.Black
                        )
                    )
                    gapIndex++
                } else {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .border(
                                width = 4.dp,
                                color = Color(0xFF5E7F2C),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(ch.toString())
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                            userInput = List(gapCount) {""}
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
                            userInput = List(gapCount) {""}
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
                    val userWord = buildString {
                        var gapIndex = 0

                        blankWord.forEach { ch ->
                            if (ch == '*') {
                                append(
                                    userInput[gapIndex].ifBlank {
                                        "*"
                                    }
                                )
                                gapIndex++
                            } else {
                                append(ch)
                            }
                        }
                    }

                    isCorrect = userWord == correctAnswer
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