package com.nakhmadov.footballapp.ui.competition_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import com.nakhmadov.footballapp.data.repositories.StatRepository
import kotlin.IllegalArgumentException

class CompetitionDetailViewModelFactory(
    private val competitionId: Int,
    private val isFavorite: Boolean,
    private val competitionRepository: CompetitionRepository,
    private val matchRepository: MatchRepository,
    private val statRepository: StatRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompetitionDetailViewModel::class.java))
            return CompetitionDetailViewModel(
                competitionId,
                isFavorite,
                competitionRepository,
                matchRepository,
                statRepository
            ) as T
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}