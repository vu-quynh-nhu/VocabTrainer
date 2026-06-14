package com.example.vocabtrainer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vocabtrainer.models.Card
import com.example.vocabtrainer.models.Deck
import com.example.vocabtrainer.models.StudyMode
import com.example.vocabtrainer.models.StudySession

class StudyViewModel : ViewModel() {
    var hasDifficultyMode by mutableStateOf(false)
        private set

    var difficultyIndex by mutableIntStateOf(0)
        private set

    var selectedDifficulty by mutableStateOf("")
    private set

    var selectedStudyModePage by mutableStateOf("")
        private set

    var cardSideIndex by mutableIntStateOf(0)
        private set

    var selectedStudyDeck by mutableStateOf<Deck?>(null)
        private set

    var studyMode by mutableStateOf<StudyMode?>(null)
        private set

    var studySession by mutableStateOf<StudySession?>(null)
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

    fun setCardSide(selectedCardSide: Int) {
        cardSideIndex = selectedCardSide
    }

    fun createStudySession(
        selectedDeck: Deck?,
        difficultyIndex: Int,
        cardSideIndex: Int,
        ) {
        studyMode = StudyMode(
            deck = selectedDeck,
            difficulty = difficultyIndex,
            cardSide = cardSideIndex,
        )
    }

    fun setStudySession(cards: List<Card>) {
        studySession = StudySession(
            cards = cards,
            currentIndex = 0,
            correctAnswer = 0,
            wrongAnswer = 0,
        )
    }

    fun correctAnswer() {
        val session = studySession ?: return

        studySession = session.copy(
            currentIndex = session.currentIndex + 1,
            correctAnswer = session.correctAnswer + 1
        )
    }

    fun wrongAnswer() {
        val session = studySession ?: return
        val currentCard = session.cards.getOrNull(session.currentIndex) ?: return

        studySession = session.copy(
            currentIndex = session.currentIndex + 1,
            wrongAnswer = session.wrongAnswer + 1,
            wrongCards = session.wrongCards + currentCard
        )
    }

    fun repeatWrongCards() {
        val session = studySession ?: return

        studySession = StudySession(
            cards = session.wrongCards,
            currentIndex = 0,
            correctAnswer = 0,
            wrongAnswer = 0
        )
    }

    fun restartSession() {
        val session = studySession ?: return

        studySession = session.copy(
            currentIndex = 0,
            correctAnswer = 0,
            wrongAnswer = 0
        )
    }

    fun selectedStudyDeck(deck: Deck) {
        selectedStudyDeck = deck
    }

    fun resetStudyModeSettings() {
        setDifficultyMode(0)
        setCardSide(0)
    }
}