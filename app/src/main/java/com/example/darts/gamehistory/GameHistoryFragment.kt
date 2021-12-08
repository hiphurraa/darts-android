package com.example.darts.gamehistory

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.R
import com.example.darts.database.DartsDatabase
import com.example.darts.databinding.FragmentGameHistoryBinding

private var _binding: FragmentGameHistoryBinding? = null
private val binding get() = _binding!!

class GameHistoryFragment: Fragment() {

    private lateinit var database: DartsDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_history, container, false)

        val application = requireNotNull(this.activity).application

        val context = requireActivity().applicationContext
        database = DartsDatabase.getInstance(context)

        val viewModelFactory = GameHistoryViewModelFactory(database.gameDao(), application)

        val gameHistoryViewModel = ViewModelProvider(this, viewModelFactory).get(GameHistoryViewModel::class.java)

        binding.gameHistoryViewModel = gameHistoryViewModel
        binding.setLifecycleOwner(this)

        val adapter = HistoryAdapter()
        binding.gameHistoryRecyclerView.adapter = adapter

        gameHistoryViewModel.games.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        d("lauhyv", gameHistoryViewModel.games.value.toString())

        val manager = LinearLayoutManager(activity)
        binding.gameHistoryRecyclerView.layoutManager = manager


        // Setting the navigaton bar title
        binding.navigationBar.tvTitle.text = resources.getString(R.string.gh_nav_title)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Back to game menu fragment */
        binding.navigationBar.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_gameHistoryFragment_to_gameMenuFragment)
        }

        //d("lauhyv", binding.gameHistoryViewModel?.getAllGames().toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/*

val gameSettingsViewModel = ViewModelProvider(this, viewModelFactory).get(GameSettingsViewModel::class.java)

        binding.gameSettingsViewModel = gameSettingsViewModel

        binding.setLifecycleOwner(this)

        val appSettingsObserver = Observer<AppSettings> { AppSettings ->
            if(AppSettings.language == "FI") {
                binding.btnGsLanguageEnglish.setBackgroundColor(getColor(context, R.color.dark_gray))
                binding.btnGsLanguageFinnish.setBackgroundColor(getColor(context, R.color.main_blue))
            } else {
                binding.btnGsLanguageFinnish.setBackgroundColor(getColor(context, R.color.dark_gray))
                binding.btnGsLanguageEnglish.setBackgroundColor(getColor(context, R.color.main_blue))
            }

            if(AppSettings.speedEntryEnabled!!) {
                binding.btnGsPointsSpeedYes.setBackgroundColor(getColor(context, R.color.main_blue))
                binding.btnGsPointsSpeedNo.setBackgroundColor(getColor(context, R.color.dark_gray))
            } else {
                binding.btnGsPointsSpeedYes.setBackgroundColor(getColor(context, R.color.dark_gray))
                binding.btnGsPointsSpeedNo.setBackgroundColor(getColor(context, R.color.main_blue))

            }
        }

        gameSettingsViewModel.getAppSettings().observe(viewLifecycleOwner, appSettingsObserver)
 */