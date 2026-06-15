package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.vocabtrainer.models.Card
import com.example.vocabtrainer.viewmodel.CardViewModel
import com.example.vocabtrainer.viewmodel.DeckViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsideDeckPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    deckViewModel: DeckViewModel,
    cardViewModel: CardViewModel
) {
    val selectedDeck = deckViewModel.selectedDeck
    val cardsInsideDeck = cardViewModel.cards.filter {
        it.deck == selectedDeck
    }

    var selectedCard by remember { mutableStateOf<Card?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (selectedCard != null) {
        Dialog(
            onDismissRequest = { selectedCard = null }
        ) {
            Card {
                Column {
                    ListItem(
                        headlineContent = { Text("Löschen") },
                        trailingContent = {
                            Icon(Icons.Default.Delete, null)
                        },
                        modifier = Modifier.clickable {
                            showDeleteDialog = true
                        }
                    )

                    ListItem(
                        headlineContent = { Text("Bearbeiten") },
                        trailingContent = {
                            Icon(Icons.Default.Edit, null)
                        },
                        modifier = Modifier.clickable {

                        }
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text(
                    "Karte löschen"
                )
            },
            text = {
                Text(
                    "Karte wirklich löschen?"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedCard?.let { card ->
                            cardViewModel.deleteCard(card)
                        }

                        showDeleteDialog = false
                        selectedCard = null
                    }
                ) {
                    Text(
                        text = "Löschen",
                        color = Color.White
                    )
                }
            },
            dismissButton =  {
                Button(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        text = "Abbrechen",
                        color = Color.White
                    )
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = selectedDeck?.name ?: "unbekannter Stapel")
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
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn {
                    items(cardsInsideDeck) { card ->
                        ElevatedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            modifier = Modifier
                                .size(width = 400.dp, height = 170.dp)
                                .padding(vertical = 8.dp)
                                .combinedClickable(
                                    onClick = {},
                                    onLongClick = {
                                        selectedCard = card
                                    }
                                ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 15.dp
                            ),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = card.frontSide,
                                        color = Color.Black,
                                        fontSize = 22.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                HorizontalDivider(
                                    color = Color.LightGray
                                )

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = card.backSide,
                                        color = Color.Black,
                                        fontSize = 22.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}