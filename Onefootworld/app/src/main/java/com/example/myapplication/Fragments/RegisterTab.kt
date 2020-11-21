package com.example.myapplication.Fragments

import android.content.Intent
import android.os.Bundle

import android.support.v4.app.Fragment
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
import com.example.myapplication.Activities.LoginActivity
import com.example.myapplication.Activities.MainActivity
import com.example.myapplication.R
import com.example.myapplication.Activities.Session
import kotlinx.android.synthetic.main.register_tab.*
import org.json.JSONException
import org.json.JSONObject


class RegisterTab : View.OnClickListener,Fragment() {

    val URL_REGIST : String = "http://192.168.0.105/football/register.php"
    var session: Session? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.register_tab, container, false)
        val btnclick = v.findViewById<Button>(R.id.reg)
        var name: EditText = v.findViewById(R.id.regname)
        var email:EditText = v.findViewById(R.id.regEmail)
        var pass:EditText = v.findViewById(R.id.regpass)
        var cpass:EditText = v.findViewById(R.id.regcpass)



        btnclick.setOnClickListener {
            val npass = pass.text.toString()
            val ncpass = cpass.text.toString()
            val nemail = email.text.toString()

            if (name.getText().isEmpty() || email.getText().isEmpty() || pass.getText().isEmpty() || cpass.getText().isEmpty()) {
                Toast.makeText(activity, "Fill up all details", Toast.LENGTH_SHORT).show()
            } else {
                if (isValidEmail(nemail)) {
                    if (npass.length > 5) {

                        if (npass == ncpass) {
                            Regist()
                        } else {
                            Toast.makeText(activity, "Passwords do no match ", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(activity, "Password minimum 6 character long", Toast.LENGTH_LONG).show()
                    }
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


    fun Regist(){
        loading.setVisibility(View.VISIBLE)
        reg.setVisibility(View.GONE)
        val name:String = this.regname.getText().toString().trim()
        val email:String = this.regEmail.getText().toString().trim()
        val password:String =this.regpass.getText().toString().trim()
        session = Session(activity!!.applicationContext)
        val stringRequest = object : StringRequest(Method.POST, this.URL_REGIST,
            Response.Listener<String> { response ->
                try {

                    val obj = JSONObject(response)
                    val success : String = obj.getString("success")
                    if(success=="1") {
                        Toast.makeText(activity,"Registered Successfully",Toast.LENGTH_LONG).show()
                        loading.setVisibility(View.GONE);
                        reg.setVisibility(View.VISIBLE);
                        session!!.setLoggedin(true)
                        activity!!.finish()
                        val i: Intent = Intent(context, LoginActivity::class.java)
                        startActivity(i)
                    }
                    else if(success=="0")
                    {

                        Toast.makeText(activity, "User Already registered", Toast.LENGTH_LONG).show()
                        reg.setVisibility(View.VISIBLE)
                        loading.setVisibility(View.GONE)
                    }
                    else
                    {
                        Toast.makeText(activity, "$response", Toast.LENGTH_LONG).show()
                        reg.setVisibility(View.VISIBLE)
                        loading.setVisibility(View.GONE)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Registeration error ${e.toString()}", Toast.LENGTH_LONG).show()
                    loading.setVisibility(View.GONE);
                    reg.setVisibility(View.VISIBLE);

                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(activity, "Registeration ${volleyError.toString()}", Toast.LENGTH_LONG).show()
                    loading.setVisibility(View.GONE);
                    reg.setVisibility(View.VISIBLE);

                }
            })
        {
            override fun getParams(): MutableMap<String, String> {
                var params:MutableMap<String,String> = HashMap()
                params.put("name",name)
                params.put("email",email)
                params.put("password",password)
                return params
            }


            override fun getHeaders(): MutableMap<String, String> {
                var headers:MutableMap<String,String> = HashMap()
                headers.put("name",name)
                headers.put("email",email)
                headers.put("password",password)
                return headers
            }
        }
        var requestQueue:RequestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }


    }



