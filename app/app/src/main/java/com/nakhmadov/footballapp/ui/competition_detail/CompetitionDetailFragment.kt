package com.nakhmadov.footballapp.ui.competition_detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nakhmadov.football_data.util.STATUS_MATCH_FINISHED
import com.nakhmadov.football_data.util.STATUS_MATCH_SCHEDULED
import com.nakhmadov.footballapp.R
import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.CompetitionRepository
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import com.nakhmadov.footballapp.data.repositories.StatRepository
import com.nakhmadov.footballapp.data.repositories.TeamRepository

import com.nakhmadov.footballapp.databinding.CompetitionDetailFragmentBinding

class CompetitionDetailFragment : Fragment() {


    private lateinit var viewModel: CompetitionDetailViewModel
    private lateinit var binding: CompetitionDetailFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CompetitionDetailFragmentBinding.inflate(inflater, container, false)
        val competitionId = CompetitionDetailFragmentArgs.fromBundle(arguments!!).competitionId
        val isFavorite = CompetitionDetailFragmentArgs.fromBundle(arguments!!).isFavorite
        val competitionName = CompetitionDetailFragmentArgs.fromBundle(arguments!!).competitionName

        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val competitionRepository =
            CompetitionRepository.getRepository(database, network, application)
        val matchRepository = MatchRepository.getRepository(database, network, application)
        val statsRepository = StatRepository.getRepository(database)

        val viewModelFactory = CompetitionDetailViewModelFactory(
            competitionId,
            isFavorite,
            competitionRepository,
            matchRepository,
            statsRepository
        )
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[CompetitionDetailViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.getLastCompetitionResult(competitionId)
        viewModel.getFirstTablePosition(competitionId)

        viewModel.lastResultAsDomain.observe(viewLifecycleOwner, Observer {
            viewModel.checkCountOfDone(
                if (it == null) 0 else 1,
                if (viewModel.firstTablePositionAsDomain.value == null) 0 else 1
            )
        })

        viewModel.firstTablePositionAsDomain.observe(viewLifecycleOwner, Observer {
            viewModel.checkCountOfDone(
                if (viewModel.lastResultAsDomain.value == null) 0 else 1,
                if (it == null) 0 else 1
            )
        })

        viewModel.isFavoriteCompetition.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it)
                    binding.addFavoriteButton.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
                else
                    binding.addFavoriteButton.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
            }
        })

        viewModel.navigateFixturesEvent.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    CompetitionDetailFragmentDirections.actionCompetitionDetailToCompetitionMatches(
                        competitionId = competitionId,
                        status = STATUS_MATCH_SCHEDULED,
                        competitionName = competitionName
                    )
                )
            }
        })

        viewModel.navigateResultsEvent.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    CompetitionDetailFragmentDirections.actionCompetitionDetailToCompetitionMatches(
                        competitionId = competitionId,
                        status = STATUS_MATCH_FINISHED,
                        competitionName = competitionName
                    )
                )
            }
        })

        viewModel.navigateTableEvent.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    CompetitionDetailFragmentDirections.actionCompetitionDetailToCompetitionTableFragment(
                        competitionId = competitionId,
                        competitionName = competitionName
                    )
                )
            }
        })

        viewModel.navigateTeamsEvent.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    CompetitionDetailFragmentDirections.actionCompetitionDetailToCompetitionTeamsFragment(
                        competitionId = competitionId,
                        competitionName = competitionName
                    )
                )
            }
        })

        viewModel.navigateStatsEvent.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    CompetitionDetailFragmentDirections.actionCompetitionDetailToCompetitionStatsFragment(
                        competitionId = competitionId
                    )
                )
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val competitionName = CompetitionDetailFragmentArgs.fromBundle(arguments!!).competitionName

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = competitionName
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    companion object {
        fun newInstance() = CompetitionDetailFragment()
    }

}
