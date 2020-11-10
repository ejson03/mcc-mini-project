package com.nakhmadov.footballapp.ui.competitions_list

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

import com.nakhmadov.footballapp.adapters.competitions_list.CompetitionsListAdapter
import com.nakhmadov.footballapp.listeners.CompetitionClickListener
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.databinding.CompetitionsListFragmentBinding

class CompetitionsListFragment : Fragment() {

    private lateinit var viewModel: CompetitionsListViewModel
    private lateinit var binding: CompetitionsListFragmentBinding
    private lateinit var adapter: CompetitionsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val competitionRepository =
            CompetitionRepository.getRepository(database, network, application)

        val viewModelFactory = CompetitionsListViewModelFactory(competitionRepository)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[CompetitionsListViewModel::class.java]
        binding = CompetitionsListFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.isInternetConnected.observe(viewLifecycleOwner, Observer {
            it?.let {
                // TODO HANDLE INTERNET CONNECT ERROR
            }
        })

        viewModel.competitions.observe(viewLifecycleOwner, Observer {
            Log.d("myLogs", "COMPETITIONS OBSERVER: $it")

            adapter.submitList(it)

        })

        setupRecycler()

        return binding.root
    }

    private fun setupRecycler() {
        binding.competitionsListRecycler.layoutManager =
            LinearLayoutManager(context, VERTICAL, false)
        binding.competitionsListRecycler.setHasFixedSize(true)

        adapter =
            CompetitionsListAdapter(listener = CompetitionClickListener { id, title, isFavorite ->
                findNavController().navigate(
                    CompetitionsListFragmentDirections.actionCompetitionsListFragmentToCompetitionDetail(
                        competitionId = id,
                        competitionName = title,
                        isFavorite = isFavorite
                    )
                )
            })

        binding.competitionsListRecycler.adapter = adapter
    }

    companion object {
        fun newInstance() = CompetitionsListFragment()
    }

}
