package com.example.vocabtrainer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StudyViewModel : ViewModel() {
    var hasDifficultyMode by mutableStateOf(false)
        private set

    var difficultyIndex by mutableIntStateOf(0)
        private set

    var selectedDifficulty by mutableStateOf("")
    private set

    var selectedStudyModePage by mutableStateOf("")
        private set

    var isTimed by mutableStateOf(false)
        private set

    var timedIndex by mutableIntStateOf(0)
        private set

    fun setHasDifficulty(value: Boolean) {
        hasDifficultyMode = value
    }

    fun setDifficultyMode(selectedDifficultyOption: Int) {
        difficultyIndex = selectedDifficultyOption

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
        timedIndex = selectedTimeOption
        isTimed = selectedTimeOption == 1
    }

    fun resetStudyModeSettings() {
        setDifficultyMode(0)
        setIsTimed(0)

    }
}