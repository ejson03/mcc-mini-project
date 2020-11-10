package com.nakhmadov.footballapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository

class FavoriteViewModel(private val competitionRepository: CompetitionRepository) : ViewModel() {

    val favorites = competitionRepository.favorites

    init {
        competitionRepository.getFavorites()
    }
}
