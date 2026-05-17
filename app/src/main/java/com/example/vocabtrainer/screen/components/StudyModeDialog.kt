package com.example.vocabtrainer.screen.components

import android.app.Dialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Composable
fun StudyModeDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val difficulty = listOf("leicht", "mittel", "schwer")
    val timed = listOf("Ja", "Nein")
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = modifier
                .size(
                    width = screenWidth * 0.9f,
                    height = screenHeight * 0.5f
                )
            //.padding(16.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    text = "Schwierigkeitsgrad",
                    fontSize = 12.sp
                )

                SegmentedButton(
                    options = difficulty
                )

                Text(
                    text = "Auf Zeit",
                    fontSize = 12.sp
                )

                SegmentedButton(
                    options = timed
                )

                Button(
                    onClick = {},
                ) {
                    Text(
                        text = "lernen"
                    )
                }
            }
        }
    }
}