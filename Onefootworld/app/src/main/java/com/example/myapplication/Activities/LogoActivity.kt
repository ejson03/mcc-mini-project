package com.example.myapplication.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.myapplication.R


class LogoActivity : AppCompatActivity() {

    var session: Session? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)
        session = Session(this)
        if (session!!.loggedin()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else if (!session!!.loggedin())
        {

                startActivity(Intent(this, LoginActivity::class.java))
                finish()

        }

    }
}
