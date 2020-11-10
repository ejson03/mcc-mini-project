package com.nakhmadov.football_data.util

import android.app.Application
import android.content.Context
import com.nakhmadov.footballapp.R
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale

fun convertDate(date: String, context: Context): String {
    val splitDate = date.substring(0, 10)
    val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("GMT")
    val resultDate = dateFormat.parse(date)

    val dayOfWeek = when(resultDate?.day) {
        0 -> context.getString(R.string.sunday)
        1 -> context.getString(R.string.monday)
        2 -> context.getString(R.string.thuesday)
        3 -> context.getString(R.string.wednesday)
        4 -> context.getString(R.string.thursday)
        5 -> context.getString(R.string.friday)
        6 -> context.getString(R.string.saturday)
        else -> throw IllegalArgumentException("Unknown day")
    }

    val month = when(resultDate.month) {
        0 -> context.getString(R.string.january)
        1 -> context.getString(R.string.february)
        2 -> context.getString(R.string.march)
        3 -> context.getString(R.string.april)
        4 -> context.getString(R.string.may)
        5 -> context.getString(R.string.june)
        6 -> context.getString(R.string.july)
        7 -> context.getString(R.string.august)
        8 -> context.getString(R.string.september)
        9 -> context.getString(R.string.october)
        10 -> context.getString(R.string.november)
        11 -> context.getString(R.string.december)
        else -> throw IllegalArgumentException("Unknown month")
    }

    val dayOfMonth = resultDate.date
    val year = splitDate.substring(0, 4)

    return context.getString(R.string.getDate, dayOfWeek, dayOfMonth, month, year)

}