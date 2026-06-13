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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabtrainer.viewmodel.StudyViewModel

@Composable
fun StudyPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: StudyViewModel
) {
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
                //showStudyModeDialog = true
                viewModel.setHasDifficulty(false)
                viewModel.setStudyModePage("classic_mode")
                viewModel.resetStudyModeSettings()
                navController.navigate("study_mode_settings")
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
                //showStudyModeDialog = true
                viewModel.setHasDifficulty(true)
                viewModel.setStudyModePage("typing_mode")
                viewModel.resetStudyModeSettings()
                navController.navigate("study_mode_settings")
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
                viewModel.setHasDifficulty(true)
                viewModel.setStudyModePage("fill_in_mode")
                viewModel.resetStudyModeSettings()
                navController.navigate("study_mode_settings")
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