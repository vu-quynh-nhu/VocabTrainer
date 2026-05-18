package com.example.vocabtrainer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StudyViewModel : ViewModel() {
    var hasDifficultyMode by mutableStateOf(false)
        private set

    var selectedDifficulty by mutableStateOf("")
    private set

    var selectedStudyModePage by mutableStateOf("")
        private set

    var isTimed by mutableStateOf(false)
        private set

    fun setHasDifficulty(value: Boolean) {
        hasDifficultyMode = value
    }

    fun setDifficultyMode(selectedDifficultyOption: Int) {
        when (selectedDifficultyOption) {
            0 -> {
                selectedDifficulty = "easy"
            }
            1 -> {
                selectedDifficulty = "medium"
            }
            2 -> {
                selectedDifficulty = "hard"
            }
        }
    }

    fun setStudyModePage(studyModePage: String) {
        selectedStudyModePage = studyModePage
    }

    fun setIsTimed(selectedTimeOption: Int) {
        isTimed = selectedTimeOption == 1
    }
}