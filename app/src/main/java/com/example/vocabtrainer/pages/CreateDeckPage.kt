package com.example.vocabtrainer.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.vocabtrainer.viewmodel.DeckViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDeckPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DeckViewModel
) {
    var deckName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Stapel erstellen")
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
                .imePadding()
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
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = deckName,
                    onValueChange = { deckName = it },
                    label = { Text("Name des Stapels") },
                    isError = deckName.isBlank()
                )

                Button(
                    onClick = {
                        if (viewModel.isDeckAlreadyCreated(deckName)) {
                            Toast.makeText(
                                context,
                                "Stapel existiert bereits",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        viewModel.addDeck(
                            name = deckName
                        )

                        Toast.makeText(
                            context,
                            "Stapel erfolgreich erstellt",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.popBackStack()
                    },
                    enabled = deckName.isNotBlank(),
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7FA34A)
                    )
                ) {
                    Text(
                        text = "Erstellen",
                        color = Color.White
                    )
                }
            }
        }
    }
}