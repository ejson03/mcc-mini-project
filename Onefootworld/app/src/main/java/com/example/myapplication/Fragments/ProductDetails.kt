package com.example.myapplication.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.myapplication.Activities.Addres_Page
import com.example.myapplication.Activities.Line_Up_Final
import com.example.myapplication.Activities.MainActivity
import com.example.myapplication.R

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_row.*
import kotlinx.android.synthetic.main.productdetails.*
import kotlinx.android.synthetic.main.productdetails.image
import kotlinx.android.synthetic.main.productdetails.product_name


class ProductDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.productdetails)

        val title = intent.getStringExtra("product_name")
        val description = intent.getStringExtra("description")

        val club= intent.getStringExtra("club_name").toString()
        val imageurl = intent.getStringExtra("imageurl")
        val price = intent.getStringExtra("product_price").toString()


        product_name.text = title
        product_description.text = description
        club_name.text = club
        product_price.text = price
        Picasso.get().load(imageurl).into(image)

        book_now.setOnClickListener{
            Toast.makeText(this,"You will get the confirmation from the seller",Toast.LENGTH_LONG).show()
            val i: Intent = Intent(application, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(i)
            this.finish()

        }
    }
}