package com.nakhmadov.footballapp.ui.team.tabs.matches

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.nakhmadov.football_data.util.TEAM_ID_KEY

import com.nakhmadov.footballapp.adapters.matches_list.MatchesListAdapter
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import com.nakhmadov.footballapp.data.repositories.StatRepository
import com.nakhmadov.footballapp.data.repositories.TeamRepository
import com.nakhmadov.footballapp.databinding.TeamMatchesFragmentBinding
import com.nakhmadov.footballapp.listeners.TeamClickListener

class TeamMatchesFragment : Fragment() {

    private lateinit var viewModel: TeamMatchesViewModel
    private lateinit var binding: TeamMatchesFragmentBinding
    private lateinit var adapter: MatchesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamMatchesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var teamId = -1

        arguments?.takeIf { it.containsKey(TEAM_ID_KEY) }?.apply {
            teamId = getInt(TEAM_ID_KEY)
        }
        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val teamRepository = TeamRepository.getRepository(database, network, application)
        val viewModelFactory = TeamMatchesViewModelFactory(teamId, teamRepository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[TeamMatchesViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.matches.observe(this, Observer {
            adapter.submitTeamMatches(it)
            Log.d("myLogs", "OBSERVER TEAM MATCHES $it")

        })

        setupRecycler()

        Log.d("myLogs", "TEAMMATCHES ID: $teamId")
    }

    private fun setupRecycler() {
        binding.matchesRecycler.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        binding.matchesRecycler.setHasFixedSize(true)
        adapter = MatchesListAdapter(listener = TeamClickListener { _, _, _ -> })
        binding.matchesRecycler.adapter = adapter

    }

    companion object {
        fun newInstance() = TeamMatchesFragment()
    }

}
