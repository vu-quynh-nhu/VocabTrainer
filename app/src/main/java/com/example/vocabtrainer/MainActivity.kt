package com.example.vocabtrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vocabtrainer.pages.CreateDeckPage
import com.example.vocabtrainer.pages.CreatePage
import com.example.vocabtrainer.screen.MainScreen
import com.example.vocabtrainer.ui.theme.VocabTrainerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocabTrainerTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "main",
                    builder = {
                        composable("main") {
                            MainScreen(navController = navController)
                        }
                        composable("create_deck") {
                            CreateDeckPage(navController = navController)
                        }
                    })
            }
        }
    }
}

