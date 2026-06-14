package com.example.vocabtrainer.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vocabtrainer.viewmodel.CardViewModel
import com.example.vocabtrainer.viewmodel.DeckViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCardPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    deckViewModel: DeckViewModel,
    cardViewModel: CardViewModel
) {
    var frontSideText by remember { mutableStateOf("") }
    var backSideText by remember { mutableStateOf("") }

    var isExpanded by remember { mutableStateOf(false) }
    var selectedDeck by remember { mutableStateOf(deckViewModel.decks.first()) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Karteikarte erstellen")
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
    ) { contentPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF7FA34A),
                            Color.Black,
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement
                    .spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = frontSideText,
                    onValueChange = { frontSideText = it },
                    label = { Text("Vorderseite") },
                    isError = frontSideText.isBlank()
                )

                TextField(
                    value = backSideText,
                    onValueChange = { backSideText = it },
                    label = { Text("Rückseite") },
                    isError = backSideText.isBlank()
                )

                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = {
                        isExpanded = !isExpanded
                    }
                ) {
                    TextField(
                        value = selectedDeck.name,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = isExpanded
                            )
                        },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = {
                            isExpanded = false
                        }
                    ) {
                        deckViewModel.decks.forEach { deck ->
                            DropdownMenuItem(
                                text = { Text(deck.name) },
                                onClick = {
                                    selectedDeck = deck
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        cardViewModel.addCard(
                            frontSide = frontSideText,
                            backSide = backSideText,
                            deck = selectedDeck
                        )

                        Toast.makeText(
                            context,
                            "Karte erfolgreich erstellt",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.popBackStack()
                    },
                    enabled = frontSideText.isNotBlank() && backSideText.isNotBlank(),
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7FA34A)
                    )
                ) {
                    Text(
                        text = "Erstellen"
                    )
                }
            }
        }
    }
}