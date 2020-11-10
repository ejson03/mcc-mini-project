package com.nakhmadov.footballapp.adapters.teams_list

import androidx.recyclerview.widget.DiffUtil
import com.nakhmadov.footballapp.data.db.models.Team

class TeamsListItemDiffCallback : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.teamId == newItem.teamId
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem == newItem
    }

}