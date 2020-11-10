package com.nakhmadov.footballapp.ui.competition_stats

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

import com.nakhmadov.footballapp.R
import com.nakhmadov.footballapp.adapters.competition_scorers_list.ScorersListAdapter
import com.nakhmadov.footballapp.listeners.PlayerClickListener
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.databinding.CompetitionStatsFragmentBinding

class CompetitionStatsFragment : Fragment() {

    private lateinit var viewModel: CompetitionStatsViewModel
    private lateinit var binding: CompetitionStatsFragmentBinding
    private lateinit var adapter: ScorersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CompetitionStatsFragmentBinding.inflate(inflater, container, false)
        val competitionId = CompetitionStatsFragmentArgs.fromBundle(arguments!!).competitionId
        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val competitionRepository =
            CompetitionRepository.getRepository(database, network, application)

        val viewModelFactory = CompetitionStatsViewModelFactory(
            competitionId,
            competitionRepository
        )

        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[CompetitionStatsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.scorers.observe(viewLifecycleOwner, Observer {
            Log.d("myLogs", "OBSERVER PLAYERS $it")

            adapter.addHeaderAndSubmit(it)

        })


        setupRecycler()

        return binding.root
    }

    private fun setupRecycler() {
        binding.scorersListRecycler.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        binding.scorersListRecycler.setHasFixedSize(true)
        adapter =
            ScorersListAdapter(listener = PlayerClickListener { _ ->
                // TODO NAVIGATE TO PLAYER
            })
        binding.scorersListRecycler.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.top_scorers)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun newInstance() = CompetitionStatsFragment()
    }

}
