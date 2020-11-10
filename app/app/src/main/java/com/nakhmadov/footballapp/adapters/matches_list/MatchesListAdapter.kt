package com.nakhmadov.footballapp.adapters.matches_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.football_data.util.STATUS_MATCH_FINISHED
import com.nakhmadov.football_data.util.STATUS_MATCH_SCHEDULED
import com.nakhmadov.footballapp.databinding.CompetitionMatchesHeaderItemBinding
import com.nakhmadov.footballapp.databinding.CompetitionMatchesMatchItemBinding
import com.nakhmadov.footballapp.databinding.MatchesListItemBinding
import com.nakhmadov.footballapp.databinding.TeamMatchItemBinding
import com.nakhmadov.footballapp.listeners.TeamClickListener
import com.nakhmadov.footballapp.ui.models.MatchDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MatchesListAdapter(private val listener: TeamClickListener) :
    ListAdapter<MatchesListItem, RecyclerView.ViewHolder>(MatchesListItemDiffCallback()) {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_MATCH = 1
    private val ITEM_VIEW_TYPE_COMPETITION_MATCH = 2
    private val ITEM_VIEW_TYPE_TEAM_MATCH = 3

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun submitMatches(list: List<MatchDomain>) {
        adapterScope.launch {
            val date = if (list.isNotEmpty()) list[0].matchDate else null
            val items = mutableListOf<MatchesListItem>()
            date?.let {
                items.add(MatchesListItem.HeaderItem(it))
            }

            list.forEach {
                items.add(MatchesListItem.MatchItem(it))
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    fun submitTeamMatches(list: List<MatchDomain>) {
        adapterScope.launch {
            val items = mutableListOf<MatchesListItem>()
            list.forEach {
                items.add(MatchesListItem.TeamMatchItem(it))
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    fun submitCompetitionMatches(list: List<MatchDomain>, statusType: Int) {

        val items = mutableListOf<MatchesListItem>()
        val keysDate: TreeMap<String, String> = TreeMap()
        for (matchDomain in list) {
            matchDomain.matchDate?.let { keysDate.put(it.substring(0, 10), it) }
        }

        val keys = keysDate.keys

        if (statusType == STATUS_MATCH_FINISHED)

            for (i in keys.size - 1 downTo 0) {
                items.add(MatchesListItem.HeaderItem(keysDate[keys.elementAt(i)]!!))
                for (matchDomain in list) {
                    matchDomain.matchDate?.let { date ->
                        if (date.substring(0, 10) == keys.elementAt(i))
                            items.add(MatchesListItem.CompetitionMatchItem(matchDomain))
                    }
                }
            }
        else if (statusType == STATUS_MATCH_SCHEDULED)

            for (i in 0 until keys.size) {
                items.add(MatchesListItem.HeaderItem(keysDate[keys.elementAt(i)]!!))
                for (matchDomain in list) {
                    matchDomain.matchDate?.let { date ->
                        if (date.substring(0, 10) == keys.elementAt(i))
                            items.add(MatchesListItem.CompetitionMatchItem(matchDomain))
                    }
                }
            }

        submitList(items)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_MATCH -> MatchViewHolder.from(parent)
            ITEM_VIEW_TYPE_COMPETITION_MATCH -> CompetitionMatchViewHolder.from(parent)
            ITEM_VIEW_TYPE_TEAM_MATCH -> TeamMatchViewHolder.from(parent)
            else -> throw IllegalArgumentException("Unknown View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MatchViewHolder -> holder.bind(
                (getItem(position) as MatchesListItem.MatchItem).matchDomain,
                listener
            )
            is CompetitionMatchViewHolder -> holder.bind(
                (getItem(position) as MatchesListItem.CompetitionMatchItem).matchDomain,
                listener
            )
            is TeamMatchViewHolder -> holder.bind(
                (getItem(position) as MatchesListItem.TeamMatchItem).matchDomain
            )
            is HeaderViewHolder -> holder.bind((getItem(position) as MatchesListItem.HeaderItem).text)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MatchesListItem.HeaderItem -> ITEM_VIEW_TYPE_HEADER
            is MatchesListItem.MatchItem -> ITEM_VIEW_TYPE_MATCH
            is MatchesListItem.CompetitionMatchItem -> ITEM_VIEW_TYPE_COMPETITION_MATCH
            is MatchesListItem.TeamMatchItem -> ITEM_VIEW_TYPE_TEAM_MATCH
            else -> throw IllegalArgumentException("Unknown Item type")
        }
    }


    class MatchViewHolder private constructor(private val binding: MatchesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MatchDomain, listener: TeamClickListener) {
            binding.teamListener = listener
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MatchViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MatchesListItemBinding.inflate(inflater, parent, false)
                return MatchViewHolder(binding)
            }
        }
    }


    class TeamMatchViewHolder private constructor(private val binding: TeamMatchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MatchDomain) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TeamMatchViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TeamMatchItemBinding.inflate(inflater, parent, false)
                return TeamMatchViewHolder(binding)
            }
        }

    }

    class CompetitionMatchViewHolder private constructor(private val binding: CompetitionMatchesMatchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MatchDomain, listener: TeamClickListener) {
            binding.listener = listener
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CompetitionMatchViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CompetitionMatchesMatchItemBinding.inflate(inflater, parent, false)
                return CompetitionMatchViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder private constructor(private val binding: CompetitionMatchesHeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.header = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CompetitionMatchesHeaderItemBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }
}