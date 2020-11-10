package com.nakhmadov.footballapp.ui.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nakhmadov.footballapp.R
import com.nakhmadov.footballapp.adapters.team_page.TeamPageAdapter
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import com.nakhmadov.footballapp.data.repositories.StatRepository
import com.nakhmadov.footballapp.data.repositories.TeamRepository

import com.nakhmadov.footballapp.databinding.TeamFragmentBinding

class TeamFragment : Fragment() {

    private lateinit var viewModel: TeamViewModel
    private lateinit var binding: TeamFragmentBinding
    private lateinit var teamPageAdapter: TeamPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamFragmentBinding.inflate(inflater, container, false)
        val teamId = TeamFragmentArgs.fromBundle(arguments!!).teamId
        val teamIsFavorite = TeamFragmentArgs.fromBundle(arguments!!).teamIsFavorite

        teamPageAdapter = TeamPageAdapter(childFragmentManager, teamId)

        binding.viewpager.adapter = teamPageAdapter

        binding.tabs.setupWithViewPager(binding.viewpager)

        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val teamRepository = TeamRepository.getRepository(database, network, application)

        val viewModelFactory = TeamViewModelFactory(teamId, teamIsFavorite, teamRepository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[TeamViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isFavoriteTeam.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it)
                    binding.addFavoriteButton.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
                else
                    binding.addFavoriteButton.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val competitionName = TeamFragmentArgs.fromBundle(arguments!!).teamName

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = competitionName
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun newInstance() = TeamFragment()
    }

}
