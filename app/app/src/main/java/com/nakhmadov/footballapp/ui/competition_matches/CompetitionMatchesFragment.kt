package com.nakhmadov.footballapp.ui.competition_matches

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

import com.nakhmadov.footballapp.adapters.matches_list.MatchesListAdapter
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import com.nakhmadov.footballapp.data.repositories.StatRepository
import com.nakhmadov.footballapp.data.repositories.TeamRepository
import com.nakhmadov.footballapp.databinding.CompetitionMatchesFragmentBinding
import com.nakhmadov.footballapp.listeners.TeamClickListener

class CompetitionMatchesFragment : Fragment() {


    private lateinit var viewModel: CompetitionMatchesViewModel
    private lateinit var binding: CompetitionMatchesFragmentBinding
    private lateinit var adapter: MatchesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CompetitionMatchesFragmentBinding.inflate(inflater, container, false)

        val competitionId = CompetitionMatchesFragmentArgs.fromBundle(arguments!!).competitionId
        val statusType = CompetitionMatchesFragmentArgs.fromBundle(arguments!!).status
        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val matchRepository = MatchRepository.getRepository(database, network, application)

        val viewModelFactory =
            CompetitionMatchesViewModelFactory(competitionId, statusType, matchRepository)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[CompetitionMatchesViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.matches.observe(viewLifecycleOwner, Observer {
            Log.d("myLogs", "OBSERVER COMPETITION MATCHES $it")
            adapter.submitCompetitionMatches(it, statusType)

        })

        setupRecycler()

        return binding.root
    }

    private fun setupRecycler() {
        binding.matchesRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.matchesRecycler.setHasFixedSize(true)
        adapter =
            MatchesListAdapter(listener = TeamClickListener { teamId, teamName, teamIsFavorite ->
                findNavController().navigate(
                    CompetitionMatchesFragmentDirections.actionCompetitionMatchesFragmentToTeamFragment(
                        teamId,
                        teamName,
                        teamIsFavorite
                    )
                )
            })
        binding.matchesRecycler.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CompetitionMatchesViewModel::class.java)

        val competitionName = CompetitionMatchesFragmentArgs.fromBundle(arguments!!).competitionName
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = competitionName
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun newInstance() = CompetitionMatchesFragment()
    }

}
