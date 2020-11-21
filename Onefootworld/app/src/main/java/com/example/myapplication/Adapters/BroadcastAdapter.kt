package com.example.myapplication.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.Broadcast


class BroadcastAdapter(private val broadcast: List<Broadcast>) : RecyclerView.Adapter<BroadcastAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val broadcasts = broadcast[position]
        holder.country.setText(broadcasts.country_name)
        holder.channel_name.text = broadcasts.channel_name
        holder.league_name.text = broadcasts.league_name


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.broadcast, parent,false)
        val holder = ViewHolder(view)

        return holder
    }
    override fun getItemCount()= broadcast.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val country:TextView  = itemView.findViewById(R.id.country)
        val league_name :TextView = itemView.findViewById(R.id.channel_name)
        val channel_name  :TextView= itemView.findViewById(R.id.league_name)

    }

}