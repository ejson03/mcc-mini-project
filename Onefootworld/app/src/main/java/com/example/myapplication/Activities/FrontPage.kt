package com.example.myapplication.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import java.util.*
import kotlin.concurrent.schedule

class FrontPage : AppCompatActivity() {
    val timer: Timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front_page)
        Timer("SettingUp", false).schedule(2000) {
            startActivity(Intent(applicationContext, LogoActivity::class.java))
            finish()
        }
    }
}
