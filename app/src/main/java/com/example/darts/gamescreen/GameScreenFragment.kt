package com.example.darts.gamescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.darts.InGameSettings
import com.example.darts.databinding.FragmentGameScreenBinding

private var _binding: FragmentGameScreenBinding? = null
private val binding get() = _binding!!
private lateinit var inGameSettings: InGameSettings

class GameScreenFragment: Fragment() {

    val args :GameScreenFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameScreenBinding.inflate(inflater, container, false)
        inGameSettings = args.inGameSettings
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val startingPoints: String = inGameSettings.startingPoints
        val startsWithDouble: String = if(inGameSettings.startsWithDouble) "Yes" else "No"
        var playersIds: String = ""
        inGameSettings.playersIds.iterator().forEach {
                playersIds += it.toString() + " "
        }
        binding.tvSettings.text = "Starting points: " + startingPoints +
                " Starts with double: " + startsWithDouble +
                " Players IDs: " + playersIds
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}