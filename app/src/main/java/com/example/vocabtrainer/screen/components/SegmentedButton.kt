package com.example.vocabtrainer.screen.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SegmentedButton(
    modifier: Modifier = Modifier,
    options: List<String>,
    onSelectedIndex: (Int) -> Unit
) {
    var selectedOptionIndex by remember { mutableIntStateOf(0) }

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedOptionIndex = index
                    onSelectedIndex(index)
                },
                selected = index == selectedOptionIndex,
                label = { Text(label) },
                colors = SegmentedButtonDefaults.colors(
                    activeBorderColor = Color(0xFFC9D8A6),
                    activeContainerColor = Color(0xFFDDE6C8).copy(alpha = 0.2f),
                    activeContentColor = Color(0xFFC9D8A6)
                )
            )
        }
    }
}