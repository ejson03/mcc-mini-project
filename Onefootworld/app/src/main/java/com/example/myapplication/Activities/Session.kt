package com.example.myapplication.Activities

import android.content.Context
import android.content.SharedPreferences

class Session(internal var ctx: Context) {
    internal var prefs: SharedPreferences
    internal var editor: SharedPreferences.Editor


    init {
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun setLoggedin(logggedin: Boolean) {
        editor.putBoolean("loggedInmode", logggedin)
        editor.commit()
    }

    fun loggedin(): Boolean {
        return prefs.getBoolean("loggedInmode", false)
    }
}