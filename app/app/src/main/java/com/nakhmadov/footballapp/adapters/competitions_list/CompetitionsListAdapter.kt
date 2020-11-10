package com.nakhmadov.footballapp.adapters.competitions_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.footballapp.data.db.models.Competition
import com.nakhmadov.footballapp.databinding.CompetitionsListItemBinding
import com.nakhmadov.footballapp.listeners.CompetitionClickListener

class CompetitionsListAdapter(private val listener: CompetitionClickListener) :
    ListAdapter<Competition, RecyclerView.ViewHolder>(CompetitionsListItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CompetitionsListItemViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CompetitionsListItemViewHolder)
            holder.bind(getItem(position), listener)
    }

    class CompetitionsListItemViewHolder private constructor(private val binding: CompetitionsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Competition, listener: CompetitionClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CompetitionsListItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CompetitionsListItemBinding.inflate(inflater, parent, false)
                return CompetitionsListItemViewHolder(
                    binding
                )
            }
        }

    }

}