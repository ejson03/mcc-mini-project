package com.nakhmadov.footballapp.adapters.teams_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.footballapp.listeners.TeamClickListener
import com.nakhmadov.footballapp.data.db.models.Team
import com.nakhmadov.footballapp.databinding.TeamItemBinding

class TeamsListAdapter(private val listener: TeamClickListener) :
    ListAdapter<Team, RecyclerView.ViewHolder>(TeamsListItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TeamViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TeamViewHolder).bind(getItem(position), listener)
    }

    class TeamViewHolder private constructor(private val binding: TeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Team, listener: TeamClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TeamViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TeamItemBinding.inflate(inflater, parent, false)
                return TeamViewHolder(binding)
            }
        }

    }
}