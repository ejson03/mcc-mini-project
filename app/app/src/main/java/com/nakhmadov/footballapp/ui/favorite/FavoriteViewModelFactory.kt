package com.nakhmadov.footballapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository

class FavoriteViewModelFactory (private val competitionRepository: CompetitionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(competitionRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}