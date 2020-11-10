package com.nakhmadov.footballapp.adapters.team_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nakhmadov.football_data.util.TEAM_ID_KEY
import com.nakhmadov.footballapp.ui.team.tabs.matches.TeamMatchesFragment
import com.nakhmadov.footballapp.ui.team.tabs.squad.TeamSquadFragment

@Suppress("DEPRECATION")
class TeamPageAdapter(fm: FragmentManager, private val teamId: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()
        bundle.putInt(TEAM_ID_KEY, teamId)
        return when(position) {
            0 -> {
                val fragment =
                    TeamSquadFragment()
                fragment.arguments = bundle
                fragment
            }
            1 -> {
                val fragment = TeamMatchesFragment()
                fragment.arguments = bundle
                fragment
            }

            else -> throw IllegalArgumentException("Unknown Fragment")
        }

    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Squad"
            1 -> "Matches"
            else -> throw IllegalArgumentException("Unknown tab")
        }
    }
}
