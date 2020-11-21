package com.example.myapplication.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.Broadcast
import com.example.myapplication.model.Screening


class ScreeningAdapter(private val screening: List<Screening>) : RecyclerView.Adapter<ScreeningAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val screenings = screening[position]
        holder.eventName.text=screenings.eventName
        holder.date.text = screenings.date
        holder.location.text = screenings.location


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.screening, parent,false)
        val holder = ViewHolder(view)

        return holder
    }
    override fun getItemCount()= screening.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val eventName:TextView  = itemView.findViewById(R.id.eventName_screening)
        val date :TextView = itemView.findViewById(R.id.date_screening)
        val location  :TextView= itemView.findViewById(R.id.location_screening)

    }

}