package com.example.darts.gamescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameScreenBinding.inflate(inflater, container, false)

        val context = requireActivity().applicationContext
        settings = args.settings
        game = Game(context, settings)
        game.start("Unnamed game")

        setupGameUI()
        binding.btnFactor2.setOnClickListener { factorHandler(2) }
        binding.btnFactor3.setOnClickListener { factorHandler(3) }
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
        binding.btn50.setOnClickListener { scoreInput(50) }
        binding.btnUndo.setOnClickListener { undo() }

        return binding.root
    }

    fun factorHandler (clickedFactor: Int) {
        if (clickedFactor == factor) factor = 0
        else factor = clickedFactor
        updateGameUI()
    }

    fun scoreInput(points: Int) {
        when (points) {
            25 -> game.newToss(25, 1)
            50 -> game.newToss(25, 2)
            else -> game.newToss(points, factor)
        }
        updateGameUI()
    }

    fun undo() {
        game.cancelPreviousToss()
        updateGameUI()
    }

    fun updateGameUI() {
        binding.tvCurrentPlayerScore.text = game.getCurrentPlayer().score.toString()
        binding.tvCurrentPlayerName.text = game.getCurrentPlayer().name
        binding.flScoreDartOne.setBackgroundResource(R.drawable.round_gray_bg)
        binding.flScoreDartTwo.setBackgroundResource(R.drawable.round_gray_bg)
        binding.flScoreDartThree.setBackgroundResource(R.drawable.round_gray_bg)
        /** Score input values */
        val currentTosses = game.getCurrentTurn().tosses

        if (currentTosses[0] == null) binding.tvScoreDartOne.text = ""
        else binding.tvScoreDartOne.text = currentTosses[0]!!.points.toString()

        if (currentTosses[1] == null) binding.tvScoreDartTwo.text = ""
        else binding.tvScoreDartTwo.text = currentTosses[1]!!.points.toString()

        if (currentTosses[2] == null) binding.tvScoreDartThree.text = ""
        else binding.tvScoreDartThree.text = currentTosses[2]!!.points.toString()

        when (game.iToss) {
            0 -> binding.flScoreDartOne.setBackgroundResource(R.drawable.round_gray_bg_focused)
            1 -> binding.flScoreDartTwo.setBackgroundResource(R.drawable.round_gray_bg_focused)
            2 -> binding.flScoreDartThree.setBackgroundResource(R.drawable.round_gray_bg_focused)
        }
        val turn: Turn = game.getCurrentTurn()

        if (turn.tosses[0] != null) {
            binding.tvFactorDartOne.text = turn.tosses[0]!!.factor.toString()
            when (turn.tosses[0]!!.factor){
                1 -> binding.flFactorDartOne.visibility = View.GONE
                2 -> binding.flFactorDartOne.visibility = View.VISIBLE
                3 -> binding.flFactorDartOne.visibility = View.VISIBLE
            }
        } else binding.flFactorDartOne.visibility = View.GONE

        if (turn.tosses[1] != null) {
            binding.tvFactorDartTwo.text = turn.tosses[1]!!.factor.toString()
            when (turn.tosses[1]!!.factor){
                1 -> binding.flFactorDartTwo.visibility = View.GONE
                2 -> binding.flFactorDartTwo.visibility = View.VISIBLE
                3 -> binding.flFactorDartTwo.visibility = View.VISIBLE
            }
        } else binding.flFactorDartTwo.visibility = View.GONE

        if (turn.tosses[2] != null) {
            binding.tvFactorDartThree.text = turn.tosses[2]!!.factor.toString()
            when (turn.tosses[2]!!.factor){
                1 -> binding.flFactorDartThree.visibility = View.GONE
                2 -> binding.flFactorDartThree.visibility = View.VISIBLE
                3 -> binding.flFactorDartThree.visibility = View.VISIBLE
            }
        } else binding.flFactorDartThree.visibility = View.GONE
    }

    fun setupGameUI() {
        binding.tvCurrentPlayerScore.text = settings.startingPoints.toString()
        binding.flScoreDartOne.setBackgroundResource(R.drawable.round_gray_bg_focused)
        binding.tvCurrentPlayerName.text = game.getCurrentPlayer().name
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPlayersList.adapter = PlayersListAdapter(settings.players)
        binding.rvPlayersList.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}