package com.example.vocabtrainer.screen.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabtrainer.viewmodel.StudyViewModel

@Composable
fun StudyModeDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudyViewModel
) {
    val difficultyOptions = listOf("leicht", "mittel", "schwer")
    val timedOptions = listOf("Nein", "Ja")

    Dialog(
        onDismissRequest = { onDismissRequest() },

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight(),

                ) {

                if (viewModel.hasDifficultyMode) {
                    Text(
                        text = "Schwierigkeitsgrad",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    SegmentedButton(
                        options = difficultyOptions,
                        onSelectedIndex = {
                            viewModel.setDifficultyMode(it)
                        }
                    )

                    Spacer(modifier = Modifier.size(12.dp))
                }

                Text(
                    text = "Auf Zeit",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                SegmentedButton(
                    options = timedOptions,
                    onSelectedIndex = {
                        viewModel.setIsTimed(it)
                    }
                )

                Spacer(modifier = Modifier.size(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7FA34A)
                        )
                    ) {
                        Text(
                            text = "Lernen"
                        )
                    }
                }
            }
        }
    }
}