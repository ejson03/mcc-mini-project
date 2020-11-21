package com.example.myapplication.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.myapplication.R

class Line_Up_Final : AppCompatActivity() {
    private var _xDelta: Int = 0
    private var _yDelta: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_line__up)
        var clickCount = 11
       var rootLayout = findViewById<View>(R.id.view_root) as ViewGroup
        //assigning variables
        var img1 = rootLayout.findViewById<View>(R.id.player1) as ImageView
        img1.setOnTouchListener(ChoiceOnTouchListener())

        var img2 = rootLayout.findViewById<View>(R.id.player2) as ImageView
        img2.setOnTouchListener(ChoiceOnTouchListener())

        var img3 = rootLayout.findViewById<View>(R.id.player3) as ImageView
        img3.setOnTouchListener(ChoiceOnTouchListener())

        var img4 = rootLayout.findViewById<View>(R.id.player4) as ImageView
        img4.setOnTouchListener(ChoiceOnTouchListener())

        var img5 = rootLayout.findViewById<View>(R.id.player5) as ImageView
        img5.setOnTouchListener(ChoiceOnTouchListener())

        var img6 = rootLayout.findViewById<View>(R.id.player6) as ImageView
        img6.setOnTouchListener(ChoiceOnTouchListener())

        var img7 = rootLayout.findViewById<View>(R.id.player7) as ImageView
        img7.setOnTouchListener(ChoiceOnTouchListener())

        var img8 = rootLayout.findViewById<View>(R.id.player8) as ImageView
        img8.setOnTouchListener(ChoiceOnTouchListener())

        var img9 = rootLayout.findViewById<View>(R.id.player9) as ImageView
        img9.setOnTouchListener(ChoiceOnTouchListener())

        var img10 = rootLayout.findViewById<View>(R.id.player10) as ImageView
        img10.setOnTouchListener(ChoiceOnTouchListener())

        var img11 = rootLayout.findViewById<View>(R.id.player11) as ImageView
        img11.setOnTouchListener(ChoiceOnTouchListener())

        val imgs = arrayOf<ImageView>(img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11)

        var plus = rootLayout.findViewById<View>(R.id.plus) as ImageView
        var minus = rootLayout.findViewById<View>(R.id.minus) as ImageView
        var reset = rootLayout.findViewById<View>(R.id.reset) as ImageView


        minus.setOnClickListener {
            if(clickCount>0) {

                var imgnew = imgs[clickCount-1]
                imgnew.setVisibility(View.GONE)

                clickCount -= 1
            }
        }

        plus.setOnClickListener {
            if(clickCount<11) {

                var imgnew = imgs[clickCount]
                imgnew.setVisibility(View.VISIBLE)
                clickCount += 1

            }
        }

        reset.setOnClickListener{
            val i: Intent = Intent(application, Line_Up_Final::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(i)
            this.finish()

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val i: Intent = Intent(application, MainActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(i)

    }
    inner class ChoiceOnTouchListener : View.OnTouchListener {

        override fun onTouch(v: View, event: MotionEvent): Boolean {

            val X = event.rawX.toInt()
            val Y = event.rawY.toInt()

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = v.layoutParams as RelativeLayout.LayoutParams
                    _xDelta = X - lParams.leftMargin
                    _yDelta = Y - lParams.topMargin
                }

                MotionEvent.ACTION_UP -> {
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                }
                MotionEvent.ACTION_POINTER_UP -> {
                }
                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = v.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.leftMargin = X - _xDelta
                    layoutParams.topMargin = Y - _yDelta
                    layoutParams.rightMargin = 0
                    layoutParams.bottomMargin = 0
                    if(layoutParams.leftMargin<30)
                    {
                        layoutParams.leftMargin=30
                    }
                    if(layoutParams.leftMargin>1200)
                    {
                        layoutParams.leftMargin=1200
                    }
                    if(layoutParams.topMargin<30)
                    {
                        layoutParams.topMargin=30
                    }
                    if(layoutParams.topMargin>2000)
                    {
                        layoutParams.topMargin=2000
                    }

                    v.layoutParams = layoutParams
                }
            }
            return true
        }
   }



}
