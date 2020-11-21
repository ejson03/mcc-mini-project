package com.example.myapplication.Activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.Fragments.BroadcastFragment
import com.example.myapplication.Fragments.MainFragment
import com.example.myapplication.Fragments.ScoreFragment
import com.example.myapplication.Fragments.ScreeningFragment
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main.*
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {
    var session: Session? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        session = Session(this)
        val navigationView = findViewById(R.id.navigationView) as NavigationView
        val headerView: View = navigationView.getHeaderView(0)
        val navAppName : TextView = headerView.findViewById(R.id.drawerName)
        val navUsername : TextView = headerView.findViewById(R.id.drawerEmail)
        val navLogo : ImageView = headerView.findViewById(R.id.nav_logo)



        var intent: Intent = getIntent()
        navAppName.text = "One FootWorld"
        navAppName.setTypeface(null , Typeface.BOLD)
        navAppName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F);

        navUsername.setText("Welcome Vedant Sahai")
        navUsername.setTypeface(null , Typeface.BOLD)
        navUsername .setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F);
        navLogo.setImageResource(R.drawable.logo)

        navLogo.getLayoutParams().height = 200
        navLogo.getLayoutParams().width = 200



        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, MainFragment())
                .commit()

        navigationView.setNavigationItemSelectedListener{
            when (it.itemId){
                R.id.livescore ->{
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, ScoreFragment())
                            .commit()
                }
                R.id.News ->{
                    Toast.makeText(this,"Sorry!! Under Progress",Toast.LENGTH_SHORT).show()
                }
                R.id.Played ->{
                    Toast.makeText(this,"Sorry!! Under Progress",Toast.LENGTH_SHORT).show()
                }
                R.id.action_lineup ->{

                    val i: Intent = Intent(application, Line_Up_Final::class.java)
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    application.startActivity(i)
                    this.finish()
                }

                R.id.action_screen ->{
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, ScreeningFragment())
                            .replace(R.id.frameLayout, ScreeningFragment())
                            .commit()
                }
                R.id.action_shop ->{
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, MainFragment())
                            .commit()
                }
                R.id.action_broadcast ->{
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, BroadcastFragment())
                            .commit()
                }
                R.id.action_logout ->{
                    session!!.setLoggedin(false)
                    val i: Intent = Intent(application, LoginActivity::class.java)
                    application.startActivity(i)
                    this.finish()

                }

            }
            it.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       drawerLayout.openDrawer(GravityCompat.START)
         return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        val builder= AlertDialog.Builder(this)
        builder.setMessage(" Exit? ")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes",DialogInterface.OnClickListener{p0,p1->finish()})
        builder.setNegativeButton("No",DialogInterface.OnClickListener{p0,  p1 -> p0.cancel()  })
        val alertDialog = builder.create()
        alertDialog.show()

    }
}


