package com.nakhmadov.footballapp

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONException
import java.util.*

class LoginActivity : Activity() {
    var email: EditText? = null
    var password: EditText? = null
    var res_email: EditText? = null
    var code: EditText? = null
    var login: Button? = null
    var cont: Button? = null
    var cont_code: Button? = null
    var cancel: Button? = null
    var cancel1: Button? = null
    var register: Button? = null
    var emailtxt: String? = null
    var passwordtxt: String? = null
    var email_res_txt: String? = null
    var code_txt: String? = null
    var params: MutableList<NameValuePair>? = null
    var pref: SharedPreferences? = null
    var reset: Dialog? = null
    var sr: ServerRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sr = ServerRequest()
        email = findViewById<View>(R.id.email) as EditText
        password = findViewById<View>(R.id.password) as EditText
        login = findViewById<View>(R.id.loginbtn) as Button
        register = findViewById<View>(R.id.register) as Button
        pref = getSharedPreferences("AppPref", Context.MODE_PRIVATE)
        register!!.setOnClickListener {
            val regactivity = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(regactivity)
            finish()
        }
        login!!.setOnClickListener {
            emailtxt = email!!.text.toString()
            passwordtxt = password!!.text.toString()
            params = ArrayList()
            (params as ArrayList<NameValuePair>).add(BasicNameValuePair("email", emailtxt))
            (params as ArrayList<NameValuePair>).add(BasicNameValuePair("password", passwordtxt))
            val sr =
                ServerRequest()
            val ip = getString(R.string.ip)
            val url = ip.plus("/login")
            val json = sr.getJSON(url, params)
//            val json = sr.getJSON("http://10.0.2.2:8080/login", params)
            if (json != null) {
                try {
                    val jsonstr = json.getString("response")
                    if (json.getBoolean("res")) {
                        val token = json.getString("token")
                        val grav = json.getString("grav")
                        val edit = pref?.edit()
                        //Storing Data using SharedPreferences
                        edit?.putString("token", token)
                        edit?.putString("grav", grav)
                        edit?.commit()
                        val profactivity =
                            Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(profactivity)
                        finish()
                    }
                    Toast.makeText(application, jsonstr, Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }
}