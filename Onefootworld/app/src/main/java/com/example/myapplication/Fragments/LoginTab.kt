package com.example.myapplication.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Activities.MainActivity
import com.example.myapplication.R
import com.example.myapplication.Activities.Session
import kotlinx.android.synthetic.main.login_tab.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class LoginTab : Fragment(),View.OnClickListener {
    val URL_REGIST : String = "http://192.168.0.105/football/login.php"
    var session: Session? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.login_tab,container,false)
        val btnLogin = v.findViewById<Button>(R.id.login)
        Log.d("ronak",URL_REGIST)
        val logemail = v.findViewById<EditText>(R.id.logemail)
        session = Session(activity!!.applicationContext)




        btnLogin.setOnClickListener {

            val nemail = logemail.text.toString()


            if ( logemail.getText().isEmpty()) {
                Toast.makeText(activity, "Fill up all details", Toast.LENGTH_SHORT).show()
            }
            else {
                if (isValidEmail(nemail)) {
                    LoginFun()
                    }

                else
                {
                    Toast.makeText(activity,"Email-ID incorrect",Toast.LENGTH_SHORT).show()

                }
            }
        }
        return v
    }
    fun isValidEmail(s:String): Boolean
            = Patterns.EMAIL_ADDRESS.matcher(s).matches()


    override fun onClick(v: View?) {

    }

    fun LoginFun() {
        loading1.setVisibility(View.VISIBLE)
        login.setVisibility(View.GONE)
        val email: String = this.logemail.getText().toString().trim()
        val password: String = this.logpass.getText().toString().trim()
        Log.d("ronak",URL_REGIST)
        session = Session(activity!!.applicationContext)
        val stringRequest = object : StringRequest(
            Method.POST, this.URL_REGIST,
            Response.Listener<String> { response ->
                try {

                    val jsonobj = JSONObject(response)
                    val success: String = jsonobj.getString("success").trim()
                    val jsonArray : JSONArray = jsonobj.getJSONArray("login")

                    if (success.equals("1")) {
                        session!!.setLoggedin(true)
                        for(i in 0..jsonArray.length()-1)
                        {
                            val jobject : JSONObject = jsonArray.getJSONObject(i)
                            val uname:String = jobject .getString("name").trim()
                            val uemail:String = jobject .getString("email").trim()
                            val intent: Intent = Intent(context, MainActivity::class.java)

                            intent.putExtra("name",uname)
                            intent.putExtra("email",uemail)
                            startActivity(intent)

                        }


                        //activity!!.finish()
                    }
                    else if (success == "0") {
                        Toast.makeText(activity, "Invalid E-mail or password", Toast.LENGTH_LONG).show()
                        login.setVisibility(View.VISIBLE)
                        loading1.setVisibility(View.GONE)
                    } else {
                        Toast.makeText(activity, "hello", Toast.LENGTH_LONG).show()
                        login.setVisibility(View.VISIBLE)
                        loading1.setVisibility(View.GONE)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()

                    Toast.makeText(activity, "$e ", Toast.LENGTH_LONG).show()
                    login.setVisibility(View.VISIBLE)
                    loading1.setVisibility(View.GONE)
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(activity, "Registeration ${volleyError.toString()}", Toast.LENGTH_LONG).show()
                    login.setVisibility(View.VISIBLE)
                    loading1.setVisibility(View.GONE)
                }
            }) {
            override fun getParams(): MutableMap<String, String> {
                var params: MutableMap<String, String> = HashMap()
                params.put("email", email)
                params.put("password", password)
                return params
            }

        }
        var requestQueue: RequestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)

        login.setVisibility(View.VISIBLE)
        loading1.setVisibility(View.GONE)
    }
}