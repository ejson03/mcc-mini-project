package com.nakhmadov.footballapp.adapters.competition_table

import com.nakhmadov.footballapp.ui.models.TableItemDomain

sealed class CompetitionTableItem {
    data class DataItem(val tableItem: TableItemDomain) : CompetitionTableItem()
    object HeaderItem : CompetitionTableItem()
}