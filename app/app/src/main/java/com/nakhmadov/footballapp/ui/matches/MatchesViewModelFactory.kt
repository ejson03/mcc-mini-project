package com.nakhmadov.footballapp.ui.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.MatchRepository

class MatchesViewModelFactory(private val matchRepository: MatchRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchesViewModel::class.java))
            return MatchesViewModel(matchRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}