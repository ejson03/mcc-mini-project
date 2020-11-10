package com.nakhmadov.footballapp.ui.competition_teams

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL

import com.nakhmadov.footballapp.listeners.TeamClickListener
import com.nakhmadov.footballapp.adapters.teams_list.TeamsListAdapter
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.data.repositories.TeamRepository
import com.nakhmadov.footballapp.databinding.CompetitionTeamsFragmentBinding

class CompetitionTeamsFragment : Fragment() {

    private lateinit var viewModel: CompetitionTeamsViewModel
    private lateinit var binding: CompetitionTeamsFragmentBinding
    private lateinit var adapter: TeamsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CompetitionTeamsFragmentBinding.inflate(inflater, container, false)

        val competitionId = CompetitionTeamsFragmentArgs.fromBundle(arguments!!).competitionId
        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val competitionRepository =
            CompetitionRepository.getRepository(database, network, application)

        val viewModelFactory =
            CompetitionTeamsViewModelFactory(competitionId, competitionRepository)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[CompetitionTeamsViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.teams.observe(viewLifecycleOwner, Observer {

            Log.d("myLogs", "OBSERVER COMPETITION TeAMS $it")
            adapter.submitList(it)

        })

        setupRecycler()
        return binding.root
    }

    private fun setupRecycler() {
        binding.teamsListRecycler.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        binding.teamsListRecycler.setHasFixedSize(true)
        adapter =
            TeamsListAdapter(listener = TeamClickListener { teamId, teamName, teamIsFavorite ->
                findNavController().navigate(
                    CompetitionTeamsFragmentDirections.actionCompetitionTeamsFragmentToTeamFragment(
                        teamId,
                        teamName,
                        teamIsFavorite
                    )
                )
            })

        binding.teamsListRecycler.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val competitionName = CompetitionTeamsFragmentArgs.fromBundle(arguments!!).competitionName
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = competitionName
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun newInstance() = CompetitionTeamsFragment()
    }

}
