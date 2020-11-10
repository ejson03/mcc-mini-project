package com.nakhmadov.footballapp.adapters.competition_table

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nakhmadov.footballapp.databinding.TableDataItemBinding
import com.nakhmadov.footballapp.databinding.TableHeaderItemBinding
import com.nakhmadov.footballapp.listeners.TeamClickListener
import com.nakhmadov.footballapp.ui.models.TableItemDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompetitionTableAdapter(private val listener: TeamClickListener) :
    ListAdapter<CompetitionTableItem, RecyclerView.ViewHolder>(CompetitionTableItemDiffCallback()) {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_DATA = 1

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    fun addHeaderAndSubmit(list: List<TableItemDomain>) {

        adapterScope.launch {
            val items = mutableListOf<CompetitionTableItem>()
            items.add(CompetitionTableItem.HeaderItem)
            list.forEach {
                val item =
                    CompetitionTableItem.DataItem(it)
                items.add(item)
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_DATA
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_DATA -> DataViewHolder.from(parent)
            else -> throw IllegalArgumentException("Unknow ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DataViewHolder -> {
                holder.bind(
                    (getItem(position) as CompetitionTableItem.DataItem).tableItem,
                    listener
                )
            }
        }
    }


    class DataViewHolder private constructor(private val binding: TableDataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TableItemDomain, listener: TeamClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DataViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TableDataItemBinding.inflate(inflater, parent, false)
                return DataViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder private constructor(private val binding: TableHeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TableHeaderItemBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

}