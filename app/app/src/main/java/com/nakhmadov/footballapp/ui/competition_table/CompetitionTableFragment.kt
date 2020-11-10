package com.nakhmadov.footballapp.ui.competition_table

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL

import com.nakhmadov.footballapp.adapters.competition_table.CompetitionTableAdapter
import com.nakhmadov.footballapp.listeners.TeamClickListener
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.data.repositories.TeamRepository
import com.nakhmadov.footballapp.databinding.CompetitionTableFragmentBinding

class CompetitionTableFragment : Fragment() {

    private lateinit var viewModel: CompetitionTableViewModel
    private lateinit var binding: CompetitionTableFragmentBinding
    private lateinit var adapter: CompetitionTableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CompetitionTableFragmentBinding.inflate(inflater, container, false)

        val competitionId = CompetitionTableFragmentArgs.fromBundle(arguments!!).competitionId
        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val competitionRepository =
            CompetitionRepository.getRepository(database, network, application)

        val viewModelFactory =
            CompetitionTableViewModelFactory(competitionId, competitionRepository)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[CompetitionTableViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.table.observe(viewLifecycleOwner, Observer {

            adapter.addHeaderAndSubmit(it)

        })

        setupRecycler()

        return binding.root
    }

    private fun setupRecycler() {
        binding.tableRecycler.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        binding.tableRecycler.setHasFixedSize(true)
        adapter =
            CompetitionTableAdapter(listener = TeamClickListener { teamId, teamName, teamIsFavorite ->
                findNavController().navigate(
                    CompetitionTableFragmentDirections.actionCompetitionTableFragmentToTeamFragment(
                        teamId,
                        teamName,
                        teamIsFavorite
                    )
                )
            })
        binding.tableRecycler.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val competitionName = CompetitionTableFragmentArgs.fromBundle(arguments!!).competitionName
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = competitionName
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun newInstance() = CompetitionTableFragment()
    }

}
