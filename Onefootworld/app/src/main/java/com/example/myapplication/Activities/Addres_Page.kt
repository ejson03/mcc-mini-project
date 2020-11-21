package com.example.myapplication.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import kotlinx.android.synthetic.main.register_tab.*
import org.json.JSONException
import org.json.JSONObject

class Addres_Page : AppCompatActivity() {
    val URL_REGIST : String = getString(R.string.ip_add)+"/football/register.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addres__page)
        val name :EditText = findViewById(R.id.name_address)
        val line1 :EditText = findViewById(R.id.line1_address)
        val area :EditText = findViewById(R.id.area_address)
        val landmark :EditText = findViewById(R.id.landmark_address)
        val city :EditText = findViewById(R.id.city_address)
        val pincode :EditText = findViewById(R.id.pin_address)

        val btnBook : Button = findViewById(R.id.book_now)

        btnBook.setOnClickListener{

        }

    }

    fun Regist(){
        loading.setVisibility(View.VISIBLE)
        reg.setVisibility(View.GONE)
        val name:String = this.regname.getText().toString().trim()
        val email:String = this.regEmail.getText().toString().trim()
        val password:String =this.regpass.getText().toString().trim()

        val stringRequest = object : StringRequest(Method.POST, this.URL_REGIST,
                Response.Listener<String> { response ->
                    try {

                        val obj = JSONObject(response)
                        val success : String = obj.getString("success")
                        if(success=="1") {
                            loading.setVisibility(View.GONE);
                            reg.setVisibility(View.VISIBLE);

                            val i: Intent = Intent(this, MainActivity::class.java)
                            startActivity(i)
                        }
                        else if(success=="0")
                        {

                            Toast.makeText(this, "User Already registered", Toast.LENGTH_LONG).show()
                            reg.setVisibility(View.VISIBLE)
                            loading.setVisibility(View.GONE)
                        }
                        else
                        {
                            Toast.makeText(this, "$response", Toast.LENGTH_LONG).show()
                            reg.setVisibility(View.VISIBLE)
                            loading.setVisibility(View.GONE)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Registeration error ${e.toString()}", Toast.LENGTH_LONG).show()
                        loading.setVisibility(View.GONE);
                        reg.setVisibility(View.VISIBLE);

                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
                        Toast.makeText(applicationContext, "Registeration ${volleyError.toString()}", Toast.LENGTH_LONG).show()
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
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
