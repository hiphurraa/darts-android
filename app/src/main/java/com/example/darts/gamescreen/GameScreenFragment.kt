package com.example.darts.gamescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darts.R
import com.example.darts.databinding.FragmentGameScreenBinding

class GameScreenFragment: Fragment() {

    val args :GameScreenFragmentArgs by navArgs()

    private var _binding: FragmentGameScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var settings: Settings
    private lateinit var game: Game
    private var factor: Int = 1
    private lateinit var playersListAdapter: PlayersListAdapter
    private lateinit var turnsListAdapter: TurnsListAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameScreenBinding.inflate(inflater, container, false)

        val context = requireActivity().applicationContext
        settings = args.settings
        game = Game(context, settings)
        game.start("Unnamed game")

        setupGameUI()
        binding.btn1.setOnClickListener { scoreInput(1) }
        binding.btn2.setOnClickListener { scoreInput(2) }
        binding.btn3.setOnClickListener { scoreInput(3) }
        binding.btn4.setOnClickListener { scoreInput(4) }
        binding.btn5.setOnClickListener { scoreInput(5) }
        binding.btn6.setOnClickListener { scoreInput(6) }
        binding.btn7.setOnClickListener { scoreInput(7) }
        binding.btn8.setOnClickListener { scoreInput(8) }
        binding.btn9.setOnClickListener { scoreInput(9) }
        binding.btn10.setOnClickListener { scoreInput(10) }
        binding.btn11.setOnClickListener { scoreInput(11) }
        binding.btn12.setOnClickListener { scoreInput(12) }
        binding.btn13.setOnClickListener { scoreInput(13) }
        binding.btn14.setOnClickListener { scoreInput(14) }
        binding.btn15.setOnClickListener { scoreInput(15) }
        binding.btn16.setOnClickListener { scoreInput(16) }
        binding.btn17.setOnClickListener { scoreInput(17) }
        binding.btn18.setOnClickListener { scoreInput(18) }
        binding.btn19.setOnClickListener { scoreInput(19) }
        binding.btn20.setOnClickListener { scoreInput(20) }
        binding.btn25.setOnClickListener { scoreInput(25) }
        binding.btnMiss.setOnClickListener { scoreInput(0) }
        binding.btnUndo.setOnClickListener { undo() }
        binding.btnFactor2.setOnClickListener { factorButtonsHandler(2) }
        binding.btnFactor3.setOnClickListener { factorButtonsHandler(3) }
        binding.btnOk.setOnClickListener { okButtonHandler() }
        binding.btnMenu.setOnClickListener {
            turnsListAdapter.notifyDataSetChanged()
            when (binding.clGameMenu.visibility) {
                View.GONE -> binding.clGameMenu.visibility = View.VISIBLE
                View.VISIBLE -> binding.clGameMenu.visibility = View.GONE
            }
        }
        binding.btnQuitGame.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_gameScreenFragment_to_gameMenuFragment)
        }

        return binding.root
    }



    private fun okButtonHandler() {
        if (game.previewState) {
            game.previewState = false
            updateGameUI()
        }
    }



    private fun factorButtonsHandler (clickedFactor: Int) {
        if (!game.previewState) {
            factor = if (clickedFactor == factor) 1
            else clickedFactor
            updateGameUI()
        }
    }



    private fun scoreInput(points: Int) {
        if (!game.previewState){
            when (points) {
                0 -> game.newToss(0, 1)
                else -> game.newToss(points, factor)
            }
            factor = 1
            updateGameUI()
        }
    }



    private fun undo() {
        if (game.orderNumber != 0) {
            if (game.getCurrentPlayer().iToss == 0 && !game.previewState) {
                game.previewState = true
                updateGameUI()
            } else {
                game.previewState = false
                game.cancelPreviousToss()
                factor = 1
                updateGameUI()
            }
        }
        else {
            Toast.makeText(context, resources.getString(R.string.gs_no_previous_turn), Toast.LENGTH_SHORT).show()
        }
    }



    private fun updateGameUI() {
        var currentTurn = game.getCurrentTurn()
        var iToss = game.getCurrentPlayer().iToss
        var currentPlayer = game.getCurrentPlayer()

        /** Preview state UI logic */
        if(game.previewState) {
            currentTurn = game.getPreviousTurn()
            iToss = -1
            currentPlayer = game.getPreviousPlayer()
            binding.btnOk.setBackgroundColor(resources.getColor(R.color.main_blue))
        }
        else {
            binding.btnOk.setBackgroundColor(resources.getColor(R.color.light_gray))
        }

        /** Update the players list */
        game.updatePlayerHighlight()
        playersListAdapter.notifyDataSetChanged()

        /** Current player score and name */
        binding.tvCurrentPlayerScore.text = currentPlayer.pointsLeft.toString()
        binding.tvCurrentPlayerName.text = currentPlayer.name

        /** Score input values */
        val currentTosses = currentTurn.tosses

        if (currentTosses[0] == null) binding.tvScoreDartOne.text = ""
        else binding.tvScoreDartOne.text = currentTosses[0]!!.points.toString()

        if (currentTosses[1] == null) binding.tvScoreDartTwo.text = ""
        else binding.tvScoreDartTwo.text = currentTosses[1]!!.points.toString()

        if (currentTosses[2] == null) binding.tvScoreDartThree.text = ""
        else binding.tvScoreDartThree.text = currentTosses[2]!!.points.toString()

        /** Score input balls highlights */
        binding.flScoreDartOne.setBackgroundResource(R.drawable.round_gray_bg)
        binding.flScoreDartTwo.setBackgroundResource(R.drawable.round_gray_bg)
        binding.flScoreDartThree.setBackgroundResource(R.drawable.round_gray_bg)

        when (iToss) {
            0 -> binding.flScoreDartOne.setBackgroundResource(R.drawable.round_gray_bg_focused)
            1 -> binding.flScoreDartTwo.setBackgroundResource(R.drawable.round_gray_bg_focused)
            2 -> binding.flScoreDartThree.setBackgroundResource(R.drawable.round_gray_bg_focused)
        }

        /** Factor elements */
        if (currentTosses[0] == null) binding.tvFactorDartOne.text = ""
        else binding.tvFactorDartOne.text = currentTosses[0]!!.factor.toString()

        if (currentTosses[1] == null) binding.tvFactorDartTwo.text = ""
        else binding.tvFactorDartTwo.text = currentTosses[1]!!.factor.toString()

        if (currentTosses[2] == null) binding.tvFactorDartThree.text = ""
        else binding.tvFactorDartThree.text = currentTosses[2]!!.factor.toString()

        /** First factor ball */
        if (currentTurn.tosses[0] != null) {
            binding.tvFactorDartOne.text = currentTurn.tosses[0]!!.factor.toString() + "x"
            when (currentTurn.tosses[0]!!.factor){
                1 -> binding.flFactorDartOne.visibility = View.GONE
                else -> binding.flFactorDartOne.visibility = View.VISIBLE
            }
        } else binding.flFactorDartOne.visibility = View.GONE

        /** Second factor ball */
        if (currentTurn.tosses[1] != null) {
            binding.tvFactorDartTwo.text = currentTurn.tosses[1]!!.factor.toString() + "x"
            when (currentTurn.tosses[1]!!.factor){
                1 -> binding.flFactorDartTwo.visibility = View.GONE
                else -> binding.flFactorDartTwo.visibility = View.VISIBLE
            }
        } else binding.flFactorDartTwo.visibility = View.GONE

        /** Third factor ball */
        if (currentTurn.tosses[2] != null) {
            binding.tvFactorDartThree.text = currentTurn.tosses[2]!!.factor.toString() + "x"
            when (currentTurn.tosses[2]!!.factor){
                1 -> binding.flFactorDartThree.visibility = View.GONE
                else -> binding.flFactorDartThree.visibility = View.VISIBLE
            }
        } else binding.flFactorDartThree.visibility = View.GONE

        /** Current toss factor ball */
        when(iToss) {
            0 -> {
                when(factor) {
                    1 -> binding.flFactorDartOne.visibility = View.GONE
                    else -> {
                        binding.tvFactorDartOne.text = factor.toString() + "x"
                        binding.flFactorDartOne.visibility = View.VISIBLE
                    }

                }
            }
            1 -> {
                when(factor) {
                    1 -> binding.flFactorDartTwo.visibility = View.GONE
                    else -> {
                        binding.tvFactorDartTwo.text = factor.toString() + "x"
                        binding.flFactorDartTwo.visibility = View.VISIBLE
                    }
                }
            }
            2 -> {
                when(factor) {
                    1 -> binding.flFactorDartThree.visibility = View.GONE
                    else -> {
                        binding.tvFactorDartThree.text = factor.toString() + "x"
                        binding.flFactorDartThree.visibility = View.VISIBLE
                    }
                }
            }
        }

        if (currentTurn.isBust) {
            binding.flBust.visibility = View.VISIBLE
        }
        else {
            binding.flBust.visibility = View.GONE
        }

        if (game.isGameOver) {
            Toast.makeText(context, "Game over!", Toast.LENGTH_SHORT).show()
        }
    }



    private fun setupGameUI() {
        binding.tvCurrentPlayerScore.text = settings.startingPoints.toString()
        binding.flScoreDartOne.setBackgroundResource(R.drawable.round_gray_bg_focused)
        binding.tvCurrentPlayerName.text = game.getCurrentPlayer().name
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playersListAdapter = PlayersListAdapter(settings.players)
        binding.rvPlayersList.adapter = playersListAdapter
        binding.rvPlayersList.layoutManager = LinearLayoutManager(activity)
        turnsListAdapter = TurnsListAdapter(game.turns, requireContext())
        binding.rvTurnsList.adapter = turnsListAdapter
        val turnsLayoutManager = LinearLayoutManager(activity)
        turnsLayoutManager.reverseLayout = true;
        turnsLayoutManager.stackFromEnd = true;
        binding.rvTurnsList.layoutManager = turnsLayoutManager
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}