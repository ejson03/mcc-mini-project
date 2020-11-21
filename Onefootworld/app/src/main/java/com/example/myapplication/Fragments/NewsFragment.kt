package com.example.myapplication.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.Adapters.ScoreAdapter
import com.example.myapplication.R
import com.example.myapplication.model.Score
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.fragment_score.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class NewsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val root = inflater.inflate(R.layout.fragment_played, container, false)



        doAsync {
            val json = URL("http://livescore-api.com/api-client/scores/live.json?key=wbgDD6FoEubmvLZH&secret=6iOa17Wu6OvTnWzT8PwLVcaAHlXc6WTd").readText()

            uiThread {
                val liv = Gson().fromJson(json, Score::class.java)
                val liv1 = liv.data
                val liv2 : JsonArray = liv1.getAsJsonArray("match")
                val liveScore = Gson().fromJson(liv2, Array<Score>::class.java).toList()







                root.recycler_view_score.apply {
                    layoutManager = GridLayoutManager(activity, 1) as RecyclerView.LayoutManager?
                    adapter = ScoreAdapter(liveScore)
                    root.progressBar_score.visibility = View.GONE
                }



            }

        }

        return root
    }
}