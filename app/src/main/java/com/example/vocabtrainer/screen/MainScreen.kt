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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vocabtrainer.NavigationItem
import com.example.vocabtrainer.R
import com.example.vocabtrainer.pages.CreateDeckPage
import com.example.vocabtrainer.pages.CreatePage
import com.example.vocabtrainer.pages.DeckPage
import com.example.vocabtrainer.pages.StudyPage

@Composable
fun MainScreen(navController: NavController) {

    val navigationItemList = listOf(
        NavigationItem("Stapel", R.drawable.cards_deck),
        NavigationItem("Erstellen", R.drawable.add_deck),
        NavigationItem("Lernen", R.drawable.learn)
    )

    // this stores the selected tab
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navigationItemList.forEachIndexed {index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(painter = painterResource(navItem.icon), contentDescription = "Icon")
                        },
                        label = {
                            Text(text = navItem.label)
                        },
                        alwaysShowLabel = selectedIndex == index,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color(0xFFFFCC80).copy(alpha = 0.2f),
                            unselectedIconColor = Color(0xFFFF8C00),
                            unselectedTextColor = Color(0xFFFF8C00),
                            selectedIconColor = Color(0xFFFFCC80),
                            selectedTextColor = Color(0xFFFFCC80)
                        )
                    )
                }
            }
        }
    ) { contentPadding ->
        ContentScreen(modifier = Modifier.padding(contentPadding), selectedIndex, navController)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int, navController: NavController) {
    when(selectedIndex) {
        0 -> DeckPage()
        1 -> {
            CreatePage(modifier = modifier, navController = navController)
        }
        2 -> StudyPage()
    }
}