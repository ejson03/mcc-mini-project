package com.nakhmadov.footballapp.ui.matches

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
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.nakhmadov.footballapp.adapters.matches_list.MatchesListAdapter


import com.nakhmadov.footballapp.data.db.getDatabase
import com.nakhmadov.footballapp.data.remote.ApiService
import com.nakhmadov.footballapp.data.repositories.MatchRepository
import com.nakhmadov.footballapp.databinding.MatchesFragmentBinding
import com.nakhmadov.footballapp.listeners.TeamClickListener
import java.util.*

class MatchesFragment : Fragment() {

    private lateinit var viewModel: MatchesViewModel
    private lateinit var binding: MatchesFragmentBinding
    private lateinit var adapter: MatchesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MatchesFragmentBinding.inflate(inflater, container, false)

        val application = (activity as AppCompatActivity).application
        val database = getDatabase(application)
        val network = ApiService.retrofitApi
        val matchRepository = MatchRepository.getRepository(database, network, application)
        val viewModelFactory = MatchesViewModelFactory(matchRepository)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[MatchesViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.showCalendarDialog.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    MaterialDialog(context!!).show {
                        datePicker { _, datetime ->
                            viewModel.dismissDialog(convertDateToStringDate(datetime))
                        }
                        lifecycleOwner(this@MatchesFragment)
                    }
                }
            }
        })

        viewModel.date.observe(viewLifecycleOwner, Observer{
            it?.let {
                viewModel.processDate(it)
            }
        })

        viewModel.matches.observe(viewLifecycleOwner, Observer {
            Log.d("myLogs", "OBSERVER MATCHES LIST $it")
            adapter.submitMatches(it)
        })

        setupRecycler()

        return binding.root
    }

    private fun convertDateToStringDate(date: Calendar): String {
        val year = "${date.year}"
        val month = if (date.month + 1 < 10) "0${date.month + 1}" else "${date.month + 1}"
        val day = if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else "${date.dayOfMonth}"
        return "$year-$month-$day"
    }

    private fun setupRecycler() {
        adapter =
            MatchesListAdapter(listener = TeamClickListener { id: Int, name: String, isFavorite: Boolean ->
                findNavController().navigate(
                    MatchesFragmentDirections.actionMatchesFragmentToTeamFragment(
                        id,
                        name,
                        isFavorite
                    )
                )
            })

        binding.matchesRecycler.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        binding.matchesRecycler.setHasFixedSize(true)
        binding.matchesRecycler.adapter = adapter
    }

    companion object {
        fun newInstance() = MatchesFragment()
    }

}
