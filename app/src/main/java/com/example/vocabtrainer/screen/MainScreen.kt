package com.example.vocabtrainer.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vocabtrainer.NavigationItem
import com.example.vocabtrainer.R
import com.example.vocabtrainer.pages.CreatePage
import com.example.vocabtrainer.pages.DeckPage
import com.example.vocabtrainer.pages.StudyPage
import com.example.vocabtrainer.viewmodel.CardViewModel
import com.example.vocabtrainer.viewmodel.DeckViewModel
import com.example.vocabtrainer.viewmodel.StudyViewModel

@Composable
fun MainScreen(
    navController: NavController,
    studyViewModel: StudyViewModel,
    deckViewModel: DeckViewModel,
    cardViewModel: CardViewModel
) {
    val navigationItemList = listOf(
        NavigationItem("Stapel", R.drawable.cards_deck, "decks"),
        NavigationItem("Erstellen", R.drawable.add_deck, "create"),
        NavigationItem("Lernen", R.drawable.learn, "study")
    )
    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "decks"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navigationItemList.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentRoute == navItem.route,
                        onClick = {
                            mainNavController.navigate(navItem.route) {
                                popUpTo(mainNavController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(navItem.icon),
                                contentDescription = "Icon"
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        },
                        alwaysShowLabel = currentRoute == navItem.route,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color(0xFFDDE6C8).copy(alpha = 0.2f),
                            unselectedIconColor = Color(0xFF7FA34A),
                            unselectedTextColor = Color(0xFF7FA34A),
                            selectedIconColor = Color(0xFFC9D8A6),
                            selectedTextColor = Color(0xFFC9D8A6)
                        )
                    )
                }
            }
        }
    ) { contentPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = "decks",
            modifier = Modifier.padding(contentPadding)
        ) {
            composable("decks") {
                DeckPage(
                    navController = navController,
                    deckViewModel = deckViewModel,
                    cardViewModel = cardViewModel
                )
            }
            composable("create") {
                CreatePage(
                    navController = navController,
                    viewModel = deckViewModel
                )
            }
            composable("study") {
                StudyPage(
                    navController = navController,
                    viewModel = studyViewModel
                )
            }
        }
    }
}