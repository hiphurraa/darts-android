package com.example.darts.gamecreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darts.InGameSettings
import com.example.darts.MainActivity
import com.example.darts.R
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.databinding.FragmentGameCreationBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameCreationFragment: Fragment() {

    private var _binding: FragmentGameCreationBinding? = null
    private val binding get() = _binding!!

    private val FIRST_VIEW = "first_view"
    private val SECOND_VIEW = "second_view"
    private val THIRD_VIEW = "third_view"
    private var currentView = FIRST_VIEW

    private lateinit var database: DartsDatabase
    private lateinit var playerDao: PlayerDao



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameCreationBinding.inflate(inflater, container, false)

        val context = requireActivity().applicationContext

        GlobalScope.launch {
            database = DartsDatabase.getInstance(context)
            playerDao = database.playerDao()
            val players = playerDao.getAll()
            binding.gameCreationSecondView.rvPlayersList.adapter = PlayerRecyclerAdapter(players)
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

            /** Show players */
            binding.gameCreationThirdView.tvSelectedPlayers.text = "Tomi ja Lauri"
        }

        /** Start game button */
        binding.gameCreationThirdView.btnContinue.setOnClickListener {
            currentView = FIRST_VIEW
            this.startGame(view)
        }

        /** Create new player button */
        binding.gameCreationSecondView.btnCreateNewPlayer.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_gameCreationFragment_to_playerCreationFragment)
        }
    }



    /** Create InGameSettings object and start the game with the settings */
    private fun startGame(view: View) {
        /** Get starting points from radio group */
        val startingPointsId = binding.gameCreationFirstView.rgStartingPoints.checkedRadioButtonId
        val startingPoints: String = requireView().findViewById<RadioButton>(startingPointsId).text.toString()

        /** Get starts with double from radio group */
        val startsWithDoubleId = binding.gameCreationFirstView.rgStartWithDouble.checkedRadioButtonId
        val startsWithDouble: Boolean = requireView().findViewById<RadioButton>(startsWithDoubleId).text.toString()
            .equals(resources.getString(R.string.gc_yes))

        // TODO: implement players selection
        val playersIds = ArrayList<Int>(10)
        playersIds.add(0)
        playersIds.add(1)

        /** Create in-game settings object */
        val inGameSettings = InGameSettings(startingPoints, startsWithDouble, playersIds)
        val action = GameCreationFragmentDirections.actionGameCreationFragmentToGameScreenFragment(inGameSettings)
        Navigation.findNavController(view).navigate(action)
        MainActivity.isInGame = true
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}