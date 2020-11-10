package com.nakhmadov.footballapp.adapters.competition_scorers_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.footballapp.databinding.ScorerDataItemBinding
import com.nakhmadov.footballapp.databinding.ScorerHeaderItemBinding
import com.nakhmadov.footballapp.listeners.PlayerClickListener
import com.nakhmadov.footballapp.ui.models.PlayerDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScorersListAdapter(private val listener: PlayerClickListener) :
    ListAdapter<ScorersListItem, RecyclerView.ViewHolder>(
        ScorersListItemDiffCallback()
    ) {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_DATA = 1

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    fun addHeaderAndSubmit(list: List<PlayerDomain>) {

        adapterScope.launch {
            val items = mutableListOf<ScorersListItem>()
            items.add(ScorersListItem.HeaderItem)
            list.forEach {
                val item =
                    ScorersListItem.DataItem(it)
                items.add(item)
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(
                parent
            )
            ITEM_VIEW_TYPE_DATA -> ScorerViewHolder.from(
                parent
            )
            else -> throw IllegalArgumentException("Unknown ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScorerViewHolder -> holder.bind(
                getItem(position) as ScorersListItem.DataItem,
                listener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return ITEM_VIEW_TYPE_HEADER
        return ITEM_VIEW_TYPE_DATA

    }

    class ScorerViewHolder private constructor(private val binding: ScorerDataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScorersListItem.DataItem, listener: PlayerClickListener) {
            binding.item = item.playerDomain
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ScorerViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ScorerDataItemBinding.inflate(inflater, parent, false)
                return ScorerViewHolder(
                    binding
                )
            }
        }
    }

    class HeaderViewHolder private constructor(private val binding: ScorerHeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ScorerHeaderItemBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(
                    binding
                )
            }
        }
    }

}