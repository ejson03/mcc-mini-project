package com.nakhmadov.footballapp.ui.team.tabs.squad

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

import com.nakhmadov.footballapp.listeners.PlayerClickListener
import com.nakhmadov.footballapp.adapters.team_squad_list.SquadListAdapter
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import com.nakhmadov.footballapp.data.repositories.StatRepository
import com.nakhmadov.footballapp.data.repositories.TeamRepository
import com.nakhmadov.footballapp.databinding.TeamSquadFragmentBinding

class TeamSquadFragment : Fragment() {

    private lateinit var viewModel: TeamSquadViewModel
    private lateinit var binding: TeamSquadFragmentBinding
    private lateinit var adapter: SquadListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamSquadFragmentBinding.inflate(inflater, container, false)
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
        val viewModelFactory = TeamSquadViewModelFactory(teamId, teamRepository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[TeamSquadViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.squad.observe(viewLifecycleOwner, Observer {

            Log.d("myLogs", "OBSERVER SQUAD $it")
            adapter.addHeadersAndSubmit(it)

        })

        setupRecycler()

        Log.d("myLogs", "TEAM SQUAD ID: $teamId")
    }

    private fun setupRecycler() {
        binding.squadRecycler.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        binding.squadRecycler.setHasFixedSize(true)
        adapter = SquadListAdapter(listener = PlayerClickListener {

        })
        binding.squadRecycler.adapter = adapter

    }

    companion object {
        fun newInstance() = TeamSquadFragment()
    }

}
