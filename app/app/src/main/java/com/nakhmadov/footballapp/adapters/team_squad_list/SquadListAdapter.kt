package com.nakhmadov.footballapp.adapters.team_squad_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.footballapp.listeners.PlayerClickListener
import com.nakhmadov.footballapp.data.db.models.Player
import com.nakhmadov.footballapp.databinding.PlayerDataItemBinding
import com.nakhmadov.footballapp.databinding.PlayerHeaderItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SquadListAdapter(private val listener: PlayerClickListener) :
    ListAdapter<SquadListItem, RecyclerView.ViewHolder>(SquadListItemDiffCallback()) {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_DATA = 1
    private val GOALKEEPER_HEADER = "Goalkeeper"
    private val DEFENDER_HEADER = "Defender"
    private val MIDFIELDER_HEADER = "Midfielder"
    private val ATTACKER_HEADER = "Attacker"

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    fun addHeadersAndSubmit(list: List<Player>) {

        adapterScope.launch {
            val items = mutableListOf<SquadListItem>()
            items.add(SquadListItem.HeaderItem("Goalkeepers"))
            list.forEach {
                if (it.position == GOALKEEPER_HEADER) {
                    items.add(SquadListItem.DataItem(it))
                }
            }

            items.add(SquadListItem.HeaderItem("Defenders"))
            list.forEach {
                if (it.position == DEFENDER_HEADER) {
                    items.add(SquadListItem.DataItem(it))
                }
            }

            items.add(SquadListItem.HeaderItem("Midfielders"))
            list.forEach {
                if (it.position == MIDFIELDER_HEADER) {
                    items.add(SquadListItem.DataItem(it))
                }
            }

            items.add(SquadListItem.HeaderItem("Attackers"))
            list.forEach {
                if (it.position == ATTACKER_HEADER) {
                    items.add(SquadListItem.DataItem(it))
                }
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_DATA -> DataViewHolder.from(parent)
            else -> throw IllegalArgumentException("Unknown View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DataViewHolder -> holder.bind(
                (getItem(position) as SquadListItem.DataItem).player,
                listener
            )
            is HeaderViewHolder -> holder.bind((getItem(position) as SquadListItem.HeaderItem).header)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SquadListItem.HeaderItem -> ITEM_VIEW_TYPE_HEADER
            is SquadListItem.DataItem -> ITEM_VIEW_TYPE_DATA
            else -> throw IllegalArgumentException("Unknown Item type")
        }
    }

    class HeaderViewHolder private constructor(private val binding: PlayerHeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: String) {
            binding.position = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = PlayerHeaderItemBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    class DataViewHolder private constructor(private val binding: PlayerDataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Player, listener: PlayerClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DataViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = PlayerDataItemBinding.inflate(inflater, parent, false)
                return DataViewHolder(binding)
            }
        }
    }
}