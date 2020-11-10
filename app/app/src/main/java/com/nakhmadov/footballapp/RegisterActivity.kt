package com.nakhmadov.footballapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONException
import java.util.*

class RegisterActivity : Activity() {
    var email: EditText? = null
    var password: EditText? = null
    var login: Button? = null
    var register: Button? = null
    var emailtxt: String? = null
    var passwordtxt: String? = null
    var params: MutableList<NameValuePair>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        email = findViewById<View>(R.id.email) as EditText
        password = findViewById<View>(R.id.password) as EditText
        register = findViewById<View>(R.id.registerbtn) as Button
        login = findViewById<View>(R.id.login) as Button
        login!!.setOnClickListener {
            val regactivity = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(regactivity)
            finish()
        }
        register!!.setOnClickListener {
            emailtxt = email!!.text.toString()
            passwordtxt = password!!.text.toString()
            params = ArrayList()
            (params as ArrayList<NameValuePair>).add(BasicNameValuePair("email", emailtxt))
            (params as ArrayList<NameValuePair>).add(BasicNameValuePair("password", passwordtxt))
            val sr =
                ServerRequest()
            val json = sr.getJSON("http://10.0.2.2:8080/register", params)
            if (json != null) {
                try {
                    val jsonstr = json.getString("response")
                    Toast.makeText(application, jsonstr, Toast.LENGTH_LONG).show()
                    Log.d("Hello", jsonstr)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }
}