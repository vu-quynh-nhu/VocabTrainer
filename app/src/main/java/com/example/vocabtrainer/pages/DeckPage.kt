package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.vocabtrainer.models.Deck
import com.example.vocabtrainer.viewmodel.CardViewModel
import com.example.vocabtrainer.viewmodel.DeckViewModel

@Composable
fun DeckPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    deckViewModel: DeckViewModel,
    cardViewModel: CardViewModel
) {
    var selectedDeckOnPress by remember { mutableStateOf<Deck?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (selectedDeckOnPress != null) {
        Dialog(
            onDismissRequest = { selectedDeckOnPress = null }
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
                    "Stapel löschen"
                )
            },
            text = {
                Text(
                    "${selectedDeckOnPress?.name} wirklich löschen? Alle Karten im Stapel werden mitgelöscht."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedDeckOnPress?.let { deck ->
                            cardViewModel.deleteCardsInsideDeck(deck)
                            deckViewModel.deleteDeck(deck)
                        }

                        showDeleteDialog = false
                        selectedDeckOnPress = null
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

    Column(
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
                        //Color(0xFF8CA364)
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Meine Stapel",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                top = 30.dp,
                bottom = 10.dp
            )
        )

        if (deckViewModel.decks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(
                            20.dp
                        )
                        .background(
                            color = Color.White.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(
                            horizontal = 20.dp,
                            vertical = 12.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Du hast noch keinen Stapel angelegt. " +
                                "Klick unten auf der Navigationsleiste auf das +, " +
                                "um einen Stapel zu erstellen.",
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                items(deckViewModel.decks) { deck ->
                    val cardAmount = cardViewModel.cards.count {
                        it.deck == deck
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(6.dp)
                                .aspectRatio(1f)
                                .combinedClickable(
                                    onClick = {
                                        deckViewModel.selectedDeck(deck)
                                        navController.navigate("inside_deck")
                                    },
                                    onLongClick = {
                                        selectedDeckOnPress = deck
                                    }
                                ),
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

                        Text(
                            text = "$cardAmount ${if (cardAmount == 1) "Karte" else "Karten"}",
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}