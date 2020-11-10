package com.nakhmadov.footballapp.adapters.favorite_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.footballapp.data.db.models.Competition
import com.nakhmadov.footballapp.data.db.models.Team
import com.nakhmadov.footballapp.databinding.FavoriteCompetitionItemBinding
import com.nakhmadov.footballapp.databinding.FavoriteHeaderItemBinding
import com.nakhmadov.footballapp.databinding.FavoriteTeamItemBinding
import com.nakhmadov.footballapp.listeners.CompetitionClickListener
import com.nakhmadov.footballapp.listeners.TeamClickListener
import com.nakhmadov.footballapp.ui.models.FavoriteDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteListAdapter(
    private val teamClickListener: TeamClickListener,
    private val competitionClickListener: CompetitionClickListener
) : ListAdapter<FavoriteListItem, RecyclerView.ViewHolder>(FavoriteListItemDiffCallback()) {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_COMPETITION = 1
    private val ITEM_VIEW_TYPE_TEAM = 2

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    fun submitFavoriteList(list: List<FavoriteDomain>) {
        adapterScope.launch {
            val items = mutableListOf<FavoriteListItem>()

            list.forEach {
                if (it.isCompetition == null) {
                    val team = Team(
                        teamId = it.id!!,
                        name = it.name!!,
                        emblemUrl = it.emblemUrl,
                        isFavorite = true
                    )
                    items.add(FavoriteListItem.TeamItem(team))
                } else {
                    val competition = Competition(
                        competitionId = it.id!!,
                        emblemUrl = it.emblemUrl,
                        title = it.name!!,
                        isFavorite = true
                    )
                    items.add(FavoriteListItem.CompetitionItem(competition))
                }
            }

            Log.d("myLogs", "SUBMITTING FAVORITE $items")

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_COMPETITION -> CompetitionViewHolder.from(parent)
            ITEM_VIEW_TYPE_TEAM -> TeamViewHolder.from(parent)
            else -> throw IllegalArgumentException("Unknown ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CompetitionViewHolder -> holder.bind(
                (getItem(position) as FavoriteListItem.CompetitionItem).competition,
                competitionClickListener
            )
            is TeamViewHolder -> holder.bind(
                (getItem(position) as FavoriteListItem.TeamItem).team,
                teamClickListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FavoriteListItem.HeaderItem -> ITEM_VIEW_TYPE_HEADER
            is FavoriteListItem.CompetitionItem -> ITEM_VIEW_TYPE_COMPETITION
            is FavoriteListItem.TeamItem -> ITEM_VIEW_TYPE_TEAM
            else -> throw IllegalArgumentException("Unknown List ViewType")
        }
    }

    class HeaderViewHolder private constructor(private val binding: FavoriteHeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = FavoriteHeaderItemBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    class CompetitionViewHolder private constructor(private val binding: FavoriteCompetitionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Competition, listener: CompetitionClickListener) {
            binding.competition = item
            binding.competitionListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CompetitionViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = FavoriteCompetitionItemBinding.inflate(inflater, parent, false)
                return CompetitionViewHolder(binding)
            }
        }
    }

    class TeamViewHolder private constructor(private val binding: FavoriteTeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Team, listener: TeamClickListener) {
            binding.team = item
            binding.teamListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TeamViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = FavoriteTeamItemBinding.inflate(inflater, parent, false)
                return TeamViewHolder(binding)
            }
        }
    }

}