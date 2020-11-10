package com.nakhmadov.footballapp.adapters.competitions_list

import androidx.recyclerview.widget.DiffUtil
import com.nakhmadov.footballapp.data.db.models.Competition

class CompetitionsListItemDiffCallback: DiffUtil.ItemCallback<Competition>() {
    override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {
        return oldItem.competitionId == newItem.competitionId
    }

    override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {
        return oldItem == newItem
    }

}