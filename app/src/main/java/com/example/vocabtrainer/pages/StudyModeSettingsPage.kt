package com.example.vocabtrainer.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabtrainer.models.Deck
import com.example.vocabtrainer.screen.components.SegmentedButton
import com.example.vocabtrainer.viewmodel.CardViewModel
import com.example.vocabtrainer.viewmodel.DeckViewModel
import com.example.vocabtrainer.viewmodel.StudyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyModeSettingsPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    studyViewModel: StudyViewModel,
    deckViewModel: DeckViewModel,
    cardViewModel: CardViewModel
) {
    var currentStep by remember { mutableIntStateOf(1) }

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
            when (currentStep) {
                1 -> StepOne(
                    modifier,
                    deckViewModel,
                    studyViewModel,
                    cardViewModel,
                    contentPadding,
                    {
                        currentStep = 2
                    },
                )
                2 -> StepTwo(
                    modifier,
                    navController,
                    studyViewModel,
                    cardViewModel,
                    contentPadding
                )
            }
        }
    }
}

@Composable
fun StepOne(
    modifier: Modifier = Modifier,
    viewModel: DeckViewModel,
    studyViewModel: StudyViewModel,
    cardViewModel: CardViewModel,
    contentPadding: PaddingValues,
    onNextStep: () -> Unit
) {
    var selectedDeck by remember { mutableStateOf<Deck?>(null) }

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text (
            text = "Wähle einen Stapel",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(viewModel.decks.filter { deck ->
                cardViewModel.cards.any { card ->
                    card.deck == deck
                }
            }) { deck ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        border = if (selectedDeck == deck) {
                            BorderStroke(
                                width = 3.dp,
                                color = Color(0xFF6F943C)
                            )
                        } else {
                            null
                        },
                        modifier = Modifier
                            .padding(6.dp)
                            .aspectRatio(1f),
                        onClick = {
                            selectedDeck = deck
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = deck.name,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                selectedDeck?.let {
                    studyViewModel.selectedStudyDeck(it)
                }

                onNextStep()
            },
            enabled = selectedDeck != null,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7FA34A)
            )
        ) {
            Text(
                text = "Weiter"
            )
        }
    }
}

@Composable
fun StepTwo(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: StudyViewModel,
    cardViewModel: CardViewModel,
    contentPadding: PaddingValues
) {
    val difficultyOptions = listOf("leicht", "mittel", "schwer")
    val cardSideOptions = listOf("Vorderseite", "Rückseite")
    val cardsOfSelectedDeck = cardViewModel.cards.filter {
        it.deck == viewModel.selectedStudyDeck
    }.shuffled()

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
            text = "Kartenansicht",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        SegmentedButton(
            options = cardSideOptions,
            selectedIndex = viewModel.cardSideIndex,
            onSelectedIndex = {
                viewModel.setCardSide(it)
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
                    viewModel.createStudySession(
                        selectedDeck = viewModel.selectedStudyDeck,
                        difficultyIndex = viewModel.difficultyIndex,
                        cardSideIndex = viewModel.cardSideIndex
                    )

                    viewModel.setStudySession(cardsOfSelectedDeck)

                    when (viewModel.selectedStudyModePage) {
                        "classic_mode" -> navController.navigate("classic_mode") {
                            popUpTo("study_mode_settings") {
                                inclusive = true
                            }
                        }

                        "typing_mode" -> navController.navigate("typing_mode"){
                            popUpTo("study_mode_settings") {
                                inclusive = true
                            }
                        }

                        "fill_in_mode" -> navController.navigate("fill_in_mode") {
                            popUpTo("study_mode_settings") {
                                inclusive = true
                            }
                        }
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