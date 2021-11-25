package com.example.darts.gamecreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.darts.InGameSettings
import com.example.darts.MainActivity
import com.example.darts.R
import com.example.darts.databinding.FragmentGameCreationBinding

private var _binding: FragmentGameCreationBinding? = null
private val binding get() = _binding!!
private val FIRST_VIEW = "first_view"
private val SECOND_VIEW = "second_view"
private val THIRD_VIEW = "third_view"
private var currentView = FIRST_VIEW
private lateinit var inGameSettings: InGameSettings

class GameCreationFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameCreationBinding.inflate(inflater, container, false)
        // Setting the navigation bar title
        binding.navigationBar.tvTitle.text = resources.getString(R.string.gc_nav_title)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Navigation bar back button functionality */
        binding.navigationBar.btnBack.setOnClickListener {
            if (currentView == FIRST_VIEW) {
                // Back to game menu fragment
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

        /** Continue button functionality */
        binding.gameCreationFirstView.btnContinue.setOnClickListener {
            binding.gameCreationFirstView.root.visibility = View.INVISIBLE
            binding.gameCreationSecondView.root.visibility = View.VISIBLE
            currentView = SECOND_VIEW
        }
        binding.gameCreationSecondView.btnContinue.setOnClickListener {
            binding.gameCreationSecondView.root.visibility = View.INVISIBLE
            binding.gameCreationThirdView.root.visibility = View.VISIBLE
            currentView = THIRD_VIEW

            // Show starting points
            val startingPointsId = binding.gameCreationFirstView.rgStartingPoints.checkedRadioButtonId
            binding.gameCreationThirdView.tvStartingPoints.text = requireView().findViewById<RadioButton>(startingPointsId).text.toString()

            // Show starts with double
            val startsWithDoubleId = binding.gameCreationFirstView.rgStartWithDouble.checkedRadioButtonId
            val startsWithDouble: Boolean = requireView().findViewById<RadioButton>(startsWithDoubleId).text.toString()
                .equals(resources.getString(R.string.gc_yes))
            binding.gameCreationThirdView.tvStartsWithDouble.text =
                if (startsWithDouble) resources.getString(R.string.gc_yes)
                else resources.getString(R.string.gc_no)

            // Show players
            binding.gameCreationThirdView.tvSelectedPlayers.text = "Tomi ja Lauri"
        }
        binding.gameCreationThirdView.btnContinue.setOnClickListener {
            currentView = FIRST_VIEW
            this.startGame(view)
        }
    }

    /** Create InGameSettings object and start the game with the settings */
    private fun startGame(view: View) {
        // Get starting points from radio group
        val startingPointsId = binding.gameCreationFirstView.rgStartingPoints.checkedRadioButtonId
        val startingPoints: String = requireView().findViewById<RadioButton>(startingPointsId).text.toString()

        // Get starts with double from radio group
        val startsWithDoubleId = binding.gameCreationFirstView.rgStartWithDouble.checkedRadioButtonId
        val startsWithDouble: Boolean = requireView().findViewById<RadioButton>(startsWithDoubleId).text.toString()
            .equals(resources.getString(R.string.gc_yes))

        // TODO: implement players selection
        // List of players IDs
        val playersIds = ArrayList<Int>(10)
        playersIds.add(0)
        playersIds.add(1)

        // Create in-game settings object
        inGameSettings = InGameSettings(startingPoints, startsWithDouble, playersIds)
        val action = GameCreationFragmentDirections.actionGameCreationFragmentToGameScreenFragment(inGameSettings)
        Navigation.findNavController(view).navigate(action)
        MainActivity.isInGame = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}