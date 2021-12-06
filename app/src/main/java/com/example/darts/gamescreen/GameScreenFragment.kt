package com.example.darts.gamescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darts.databinding.FragmentGameScreenBinding

class GameScreenFragment: Fragment() {

    val args :GameScreenFragmentArgs by navArgs()

    private var _binding: FragmentGameScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var settings: Settings

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameScreenBinding.inflate(inflater, container, false)

        val context = requireActivity().applicationContext
        settings = args.settings
        val game = Game(context, settings)
        game.start("Unnamed game")

        return binding.root
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

    fun scoreInput() {

    }
}