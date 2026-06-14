package com.example.vocabtrainer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vocabtrainer.models.Deck

class DeckViewModel : ViewModel() {
    private val _decks = mutableStateListOf<Deck>()
    val decks: List<Deck> = _decks

    var selectedDeck by mutableStateOf<Deck?>(null)
        private set

    fun addDeck(
        name: String,
    ) {
        _decks.add(
            Deck(
                name = name
            )
        )
    }

    fun selectedDeck(deck: Deck) {
        selectedDeck = deck
    }

    fun isDeckAlreadyCreated(deckName: String): Boolean {
        val name = deckName.trim()

        return decks.any {
            it.name.trim().equals(
                name,
                ignoreCase = true
            )
        }
    }

    fun deleteDeck(selectedDeck: Deck) {
        _decks.remove(selectedDeck)
    }
}