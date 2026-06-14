package com.example.vocabtrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vocabtrainer.pages.ClassicModePage
import com.example.vocabtrainer.pages.CreateCardPage
import com.example.vocabtrainer.pages.CreateDeckPage
import com.example.vocabtrainer.pages.CreatePage
import com.example.vocabtrainer.pages.DeckPage
import com.example.vocabtrainer.pages.FillInBlankModePage
import com.example.vocabtrainer.pages.InsideDeckPage
import com.example.vocabtrainer.pages.StudyModeSettingsPage
import com.example.vocabtrainer.pages.StudyPage
import com.example.vocabtrainer.pages.StudyResultPage
import com.example.vocabtrainer.pages.TypingModePage
import com.example.vocabtrainer.screen.MainScreen
import com.example.vocabtrainer.ui.theme.VocabTrainerTheme
import com.example.vocabtrainer.viewmodel.CardViewModel
import com.example.vocabtrainer.viewmodel.DeckViewModel
import com.example.vocabtrainer.viewmodel.StudyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            VocabTrainerTheme {
                val navController = rememberNavController()
                val studyViewModel: StudyViewModel = viewModel()
                val deckViewModel: DeckViewModel = viewModel()
                val cardViewModel: CardViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "main",
                    builder = {
                        composable("main") {
                            MainScreen(
                                navController = navController,
                                studyViewModel = studyViewModel,
                                deckViewModel = deckViewModel,
                                cardViewModel = cardViewModel
                            )
                        }
                        composable("inside_deck") {
                            InsideDeckPage(
                                navController = navController,
                                deckViewModel = deckViewModel,
                                cardViewModel = cardViewModel
                            )
                        }
                        composable("create_deck") {
                            CreateDeckPage(
                                navController = navController,
                                viewModel = deckViewModel
                            )
                        }
                        composable("create_card") {
                            CreateCardPage(
                                navController = navController,
                                deckViewModel = deckViewModel,
                                cardViewModel = cardViewModel
                            )
                        }
                        composable("study_mode_settings") {
                            StudyModeSettingsPage(
                                navController = navController,
                                studyViewModel = studyViewModel,
                                deckViewModel = deckViewModel,
                                cardViewModel = cardViewModel
                            )
                        }
                        composable("classic_mode") {
                            ClassicModePage(
                                navController = navController,
                                viewModel = studyViewModel,
                            )
                        }
                        composable("typing_mode") {
                            TypingModePage(
                                navController = navController,
                                viewModel = studyViewModel
                            )
                        }
                        composable("fill_in_mode") {
                            FillInBlankModePage(
                                navController = navController,
                                viewModel = studyViewModel
                            )
                        }
                        composable("results") {
                            StudyResultPage(
                                navController = navController,
                                studyViewModel = studyViewModel
                            )
                        }
                    }
                )
            }
        }
    }
}

