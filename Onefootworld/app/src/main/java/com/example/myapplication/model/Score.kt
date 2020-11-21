package com.example.myapplication.model


import com.google.gson.JsonArray
import com.google.gson.JsonObject


data class Score(
        val score: String,
        val away_name:String,
        val home_name: String,
        val success : Boolean,
        val data : JsonObject,
        val matches : JsonArray
)
