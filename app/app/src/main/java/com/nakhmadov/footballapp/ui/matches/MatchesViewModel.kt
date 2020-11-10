package com.nakhmadov.footballapp.ui.matches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MatchesViewModel(private val matchRepository: MatchRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _showCalendarDialog = MutableLiveData<Boolean>()
    val showCalendarDialog: LiveData<Boolean> = _showCalendarDialog

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    val matches = matchRepository.matchesOnTheDate

    init {

        val calendar = GregorianCalendar()
        val today = Date()
        calendar.time = today
        setDate(convertDateToStringDate(calendar))
    }

    fun chooseDay() {
        _showCalendarDialog.value = true
    }

    fun dismissDialog(date: String) {
        _showCalendarDialog.value = false
        setDate(date)
    }

    fun processDate(date: String) {
        uiScope.launch {
            matchRepository.fetchAndSaveMatchesOnTheDate(date)
        }
        matchRepository.getMatchesOnTheDate(date)
    }

    private fun setDate(date: String) {
        _date.value = date
        Log.d("myLogs", "$date")
    }

    private fun convertDateToStringDate(date: Calendar): String {
        val year = "${date.year}"
        val month = if (date.month + 1 < 10) "0${date.month + 1}" else "${date.month + 1}"
        val day = if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else "${date.dayOfMonth}"
        return "$year-$month-$day"
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
