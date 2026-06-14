package com.example.vocabtrainer.models

data class StudySession(
    val cards: List<Card>,
    val currentIndex: Int,
    val correctAnswer: Int,
    val wrongAnswer: Int,
    val wrongCards: List<Card> = emptyList()
)
