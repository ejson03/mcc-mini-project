package com.nakhmadov.footballapp.ui.competitions_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import java.security.Provider

class CompetitionsListViewModelFactory(private val competitionRepository: CompetitionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompetitionsListViewModel::class.java))
            return CompetitionsListViewModel(competitionRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}