package com.example.vocabtrainer.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.vocabtrainer.models.Card
import com.example.vocabtrainer.models.Deck

class CardViewModel : ViewModel() {
    private val _cards = mutableStateListOf<Card>()
    val cards: List<Card> = _cards

    fun addCard(
        frontSide: String,
        backSide: String,
        deck: Deck
    ) {
        _cards.add(
            Card(
                frontSide = frontSide,
                backSide = backSide,
                deck = deck
            )
        )
    }

    fun deleteCardsInsideDeck(selectedDeck: Deck) {
        _cards.removeAll { card ->
            card.deck == selectedDeck
        }
    }

    fun deleteCard(selectedCard: Card) {
        _cards.remove(selectedCard)
    }
}