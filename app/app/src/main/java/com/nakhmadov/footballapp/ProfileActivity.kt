package com.nakhmadov.footballapp

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import org.apache.http.NameValuePair

class ProfileActivity : Activity() {
    var pref: SharedPreferences? = null
    var token: String? = null
    var grav: String? = null
    var oldpasstxt: String? = null
    var newpasstxt: String? = null
    var web: WebView? = null
    var chgpass: Button? = null
    var chgpassfr: Button? = null
    var cancel: Button? = null
    var logout: Button? = null
    var dlg: Dialog? = null
    var oldpass: EditText? = null
    var newpass: EditText? = null
    var params: List<NameValuePair>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        web = findViewById<View>(R.id.webView) as WebView
        chgpass = findViewById<View>(R.id.chgbtn) as Button
        logout = findViewById<View>(R.id.logout) as Button
        logout!!.setOnClickListener {
            val edit = pref!!.edit()
            //Storing Data using SharedPreferences
            edit.putString("token", "")
            edit.commit()
            val loginactivity = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(loginactivity)
            finish()
        }
        pref = getSharedPreferences("AppPref", Context.MODE_PRIVATE)
        token = pref?.getString("token", "")
        grav = pref?.getString("grav", "")
        web!!.settings.useWideViewPort = true
        web!!.settings.loadWithOverviewMode = true
        web!!.loadUrl(grav)
    }
}