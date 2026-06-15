package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabtrainer.viewmodel.StudyViewModel

@Composable
fun ClassicModePage(
    viewModel: StudyViewModel,
    navController: NavController
) {
    var showAnswer by remember { mutableStateOf(false) }
    val chosenCardSide = viewModel.studyMode?.cardSide
    val session = viewModel.studySession ?: return
    val currentCard = session.cards.getOrNull(session.currentIndex)

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
        modifier = Modifier.fillMaxSize()
            .background(
                Color(0xFFA7C281)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${session.currentIndex + 1} von ${session.cards.size} Karten"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .size(width = 300.dp, height = 550.dp)
                .padding(bottom = 20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
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
            }
        }

        if (showAnswer) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    ),
                    onClick = {
                        viewModel.wrongAnswer()
                        showAnswer = false
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        null,
                        tint = Color.Black
                    )
                }

                Button(
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    onClick = {
                        viewModel.correctAnswer()
                        showAnswer = false
                    }
                ) {
                    Icon(
                        Icons.Default.Check,
                        null,
                        tint = Color.Black
                    )
                }
            }
        } else {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6F943C)
                ),
                onClick = {
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