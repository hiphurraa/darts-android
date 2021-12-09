package com.example.darts.gamecreation

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darts.gamescreen.Settings
import com.example.darts.MainActivity
import com.example.darts.R
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.database.entities.Player as PlayerEntity
import com.example.darts.databinding.FragmentGameCreationBinding
import com.example.darts.gamescreen.Player
import com.example.darts.playercreation.PlayerCreationFragmentDirections

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameCreationFragment: Fragment() {

    val args : GameCreationFragmentArgs by navArgs()


    private var _binding: FragmentGameCreationBinding? = null
    private val binding get() = _binding!!

    private val FIRST_VIEW = "first_view"
    private val SECOND_VIEW = "second_view"
    private val THIRD_VIEW = "third_view"
    private var currentView = FIRST_VIEW

    private lateinit var database: DartsDatabase
    private lateinit var playerDao: PlayerDao
    private var selectedPlayers: MutableList<Player> = mutableListOf()

    companion object {
        lateinit var playerEntities: List<PlayerEntity>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameCreationBinding.inflate(inflater, container, false)

        /** If returning from player creation resume the settings */
        if (args.settings.fromPlayerCreation) {
            binding.gameCreationFirstView.root.visibility = View.INVISIBLE
            binding.gameCreationSecondView.root.visibility = View.VISIBLE
            currentView = SECOND_VIEW
            when (args.settings.startingPoints) {
                501 -> binding.gameCreationFirstView.rgStartingPoints.check(R.id.rbStartingPoints501)
                301 -> binding.gameCreationFirstView.rgStartingPoints.check(R.id.rbStartingPoints301)
            }
            when (args.settings.startsWithDouble) {
                true ->  binding.gameCreationFirstView.rgStartWithDouble.check(R.id.rbStartWithDoubleYes)
                false ->  binding.gameCreationFirstView.rgStartWithDouble.check(R.id.rbStartWithDoubleNo)
            }
        }

        val context = requireActivity().applicationContext
        database = DartsDatabase.getInstance(context)
        playerDao = database.playerDao()

        GlobalScope.launch {
            playerEntities = playerDao.getAll()
            if (playerEntities.isEmpty()) {
                binding.gameCreationSecondView.tvNoPlayersYet.visibility = View.VISIBLE
            }
            else {
                binding.gameCreationSecondView.tvNoPlayersYet.visibility = View.GONE
            }
            binding.gameCreationSecondView.rvPlayersList.adapter = PlayersListAdapter(playerEntities)
            binding.gameCreationSecondView.rvPlayersList.layoutManager = LinearLayoutManager(activity)
        }

        binding.navigationBar.tvTitle.text = resources.getString(R.string.gc_nav_title)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Navigation bar back button functionality */
        binding.navigationBar.btnBack.setOnClickListener {
            if (currentView == FIRST_VIEW) {
                /** Back to game menu fragment */
                Navigation.findNavController(view).navigate(R.id.action_gameCreationFragment_to_gameMenuFragment)
            }
            else if (currentView == SECOND_VIEW) {
                binding.gameCreationSecondView.root.visibility = View.INVISIBLE
                binding.gameCreationFirstView.root.visibility = View.VISIBLE
                currentView = FIRST_VIEW
            }
            else if (currentView == THIRD_VIEW) {
                binding.gameCreationThirdView.root.visibility = View.INVISIBLE
                binding.gameCreationSecondView.root.visibility = View.VISIBLE
                currentView = SECOND_VIEW
            }
        }

        /** First view continue button */
        binding.gameCreationFirstView.btnContinue.setOnClickListener {
            binding.gameCreationFirstView.root.visibility = View.INVISIBLE
            binding.gameCreationSecondView.root.visibility = View.VISIBLE
            currentView = SECOND_VIEW
        }

        /** Second view continue button */
        binding.gameCreationSecondView.btnContinue.setOnClickListener {
            binding.gameCreationSecondView.root.visibility = View.INVISIBLE
            binding.gameCreationThirdView.root.visibility = View.VISIBLE
            currentView = THIRD_VIEW

            /** Show starting points */
            val startingPointsId = binding.gameCreationFirstView.rgStartingPoints.checkedRadioButtonId
            binding.gameCreationThirdView.tvStartingPoints.text = requireView().findViewById<RadioButton>(startingPointsId).text.toString()

            /** Show starts with double */
            val startsWithDoubleId = binding.gameCreationFirstView.rgStartWithDouble.checkedRadioButtonId
            val startsWithDouble: Boolean = requireView().findViewById<RadioButton>(startsWithDoubleId).text.toString()
                .equals(resources.getString(R.string.gc_yes))
            binding.gameCreationThirdView.tvStartsWithDouble.text =
                if (startsWithDouble) resources.getString(R.string.gc_yes)
                else resources.getString(R.string.gc_no)

            /** Show selected players */
            selectedPlayers.clear()
            playerEntities.forEach {

                if (it.defaultSelected) {
                    val newPlayer = Player(it.id, it.name)
                    selectedPlayers.add(newPlayer)
                }

            }

            binding.gameCreationThirdView.rvSelectedPlayersList.adapter = SelectedPlayersListAdapter(selectedPlayers)
            binding.gameCreationThirdView.rvSelectedPlayersList.layoutManager = LinearLayoutManager(activity)
        }

        /** 'Start game' button */
        binding.gameCreationThirdView.btnContinue.setOnClickListener {

            /** At least 1 player selected, but not over 10 players */
            if (selectedPlayers.isEmpty()) {
                Toast.makeText(view.context, resources.getString(R.string.gc_no_players_selected), Toast.LENGTH_SHORT).show()
            }
            else if (selectedPlayers.size > 10) {
                Toast.makeText(view.context, resources.getString(R.string.gc_too_many_players_selected), Toast.LENGTH_SHORT).show()
            }
            else {
                currentView = FIRST_VIEW
                this.startGame(view)
            }
        }

        /** 'Create new player' button */
        binding.gameCreationSecondView.btnCreateNewPlayer.setOnClickListener {
            /** Get starting points from radio group */
            val startingPointsId = binding.gameCreationFirstView.rgStartingPoints.checkedRadioButtonId
            val startingPoints: Int = requireView().findViewById<RadioButton>(startingPointsId).text.toString().toInt()

            /** Get starts with double from radio group */
            val startsWithDoubleId = binding.gameCreationFirstView.rgStartWithDouble.checkedRadioButtonId
            val startsWithDouble: Boolean = requireView().findViewById<RadioButton>(startsWithDoubleId).text.toString()
                .equals(resources.getString(R.string.gc_yes))

            /** Create in-game settings object */
            val inGameSettings = Settings(startingPoints, startsWithDouble, selectedPlayers, false)
            val action = GameCreationFragmentDirections.actionGameCreationFragmentToPlayerCreationFragment(inGameSettings)
            Navigation.findNavController(view).navigate(action)
        }
    }



    /** Create Settings object and start the game with the settings */
    private fun startGame(view: View) {

        /** Get starting points from radio group */
        val startingPointsId = binding.gameCreationFirstView.rgStartingPoints.checkedRadioButtonId
        val startingPoints: Int = requireView().findViewById<RadioButton>(startingPointsId).text.toString().toInt()

        /** Get starts with double from radio group */
        val startsWithDoubleId = binding.gameCreationFirstView.rgStartWithDouble.checkedRadioButtonId
        val startsWithDouble: Boolean = requireView().findViewById<RadioButton>(startsWithDoubleId).text.toString()
            .equals(resources.getString(R.string.gc_yes))

        /** Create in-game settings object */
        val inGameSettings = Settings(startingPoints, startsWithDouble, selectedPlayers, false)
        val action = GameCreationFragmentDirections.actionGameCreationFragmentToGameScreenFragment(inGameSettings)
        Navigation.findNavController(view).navigate(action)
        MainActivity.isInGame = true
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}