package com.example.myapplication.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.Score

class ScoreAdapter(private val livescore: List<Score>) : RecyclerView.Adapter<ScoreAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val livescore = livescore[position]


        holder.score.text = livescore.score.toString()
        holder.away_name.text = livescore.away_name.toString()
        holder.home_name.text = livescore.home_name.toString()


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.livescore, parent,false)
        val holder = ViewHolder(view)
        return holder
    }
    override fun getItemCount()= livescore.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val score  = itemView.findViewById<TextView>(R.id.score)
        val home_name  = itemView.findViewById<TextView>(R.id.home_name)
        val away_name  = itemView.findViewById<TextView>(R.id.away_name)




    }

}