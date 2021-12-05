package com.example.darts.gamescreen

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darts.InGameSettings
import com.example.darts.database.DartsDatabase
import com.example.darts.database.GameDao
import com.example.darts.database.entities.Game
import com.example.darts.databinding.FragmentGameScreenBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameScreenFragment: Fragment() {

    val args :GameScreenFragmentArgs by navArgs()

    private var _binding: FragmentGameScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var inGameSettings: InGameSettings

    private lateinit var database: DartsDatabase
    private lateinit var gameDao: GameDao


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameScreenBinding.inflate(inflater, container, false)

        inGameSettings = args.inGameSettings

        val context = requireActivity().applicationContext
        database = DartsDatabase.getInstance(context)
        gameDao = database.gameDao()
        val game: Game = Game(0, System.currentTimeMillis(), inGameSettings.startingPoints, "Unnamed game" )

        GlobalScope.launch {
            val id = gameDao.insertGame(game)
            d("CREATED_GAME", id.toString())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPlayersList.adapter = PlayersListAdapter(inGameSettings.selectedPlayers)
        binding.rvPlayersList.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}