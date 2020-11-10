package com.nakhmadov.footballapp.ui.favorite

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

import com.nakhmadov.footballapp.R
import com.nakhmadov.footballapp.adapters.favorite_list.FavoriteListAdapter
import com.nakhmadov.footballapp.adapters.favorite_list.FavoriteListItem
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.databinding.FavoriteFragmentBinding
import com.nakhmadov.footballapp.listeners.CompetitionClickListener
import com.nakhmadov.footballapp.listeners.TeamClickListener

class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var adapter: FavoriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val competitionRepository =
            CompetitionRepository.getRepository(database, network, application)
        val viewModelFactory = FavoriteViewModelFactory(competitionRepository)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[FavoriteViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.favorites.observe(viewLifecycleOwner, Observer {
            Log.d("myLogs", "FAVORITES $it")
            adapter.submitFavoriteList(it)
        })

        setupRecycler()

        return binding.root
    }

    private fun setupRecycler() {
        adapter =
            FavoriteListAdapter(teamClickListener = TeamClickListener { id, name, isFavorite ->
                findNavController().navigate(
                    FavoriteFragmentDirections.actionFavoriteFragmentToTeamFragment(
                        teamId = id,
                        teamName = name,
                        teamIsFavorite = isFavorite
                    )
                )
            }, competitionClickListener = CompetitionClickListener { id, name, isFavorite ->
                findNavController().navigate(
                    FavoriteFragmentDirections.actionFavoriteFragmentToCompetitionDetail(
                        competitionId = id,
                        competitionName = name,
                        isFavorite = isFavorite
                    )
                )
            })

        binding.favoriteListRecycler.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        binding.favoriteListRecycler.setHasFixedSize(true)
        binding.favoriteListRecycler.adapter = adapter
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }

}
