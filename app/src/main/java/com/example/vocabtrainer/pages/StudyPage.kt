package com.example.vocabtrainer.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.example.vocabtrainer.screen.components.StudyModeDialog

@Composable
fun StudyPage(modifier: Modifier = Modifier) {
    var showStudyModeDialog by remember { mutableStateOf(false) }

    if (showStudyModeDialog) {
        StudyModeDialog(
            onConfirm = {  },
            onDismiss = { showStudyModeDialog = false },
            modifier = modifier

        )
    }

    Column(
        modifier = modifier.fillMaxSize()
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
            text = "Wähle einen Lernmodus aus",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                top = 30.dp
            )
        )
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
            .padding(top = 30.dp)
                .fillMaxWidth(0.9f)
                .height(150.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            onClick = {
                showStudyModeDialog = true
            }
        ) {
            Text(
                text = "Klassisch",
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 1.dp)
            )
            Text(
                text = "Überprüfe dein Wissen eigentständig.",
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 0.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
            )
        }

        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(0.9f)
                .height(150.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            onClick = {

            }
        ) {
            Text(
                text = "Eintippen",
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 1.dp)
            )
            Text(
                text = "Überprüfe dein Wissen eigentständig.",
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 0.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
            )
        }

        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(0.9f)
                .height(150.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            onClick = {

            }
        ) {
            Text(
                text = "Fülle die Lücke",
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 1.dp)
            )
            Text(
                text = "Überprüfe dein Wissen eigentständig.",
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 0.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
            )
        }
    }
}