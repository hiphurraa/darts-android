package com.example.darts.gamemenu

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.darts.R
import com.example.darts.database.AppSettingsDao
import com.example.darts.database.DartsDatabase
import com.example.darts.databinding.FragmentGameMenuBinding
import kotlinx.coroutines.runBlocking

private var _binding: FragmentGameMenuBinding? = null
// This property is only valid between onCreateView and
// onDestroyView.
private val binding get() = _binding!!

class GameMenuFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {
        _binding = FragmentGameMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNewGame.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_gameMenuFragment_to_gameCreationFragment)
        }

        binding.btnHistory.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_gameMenuFragment_to_gameHistoryFragment)
        }

        binding.btnSettings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_gameMenuFragment_to_gameSettingsFragment)
        }
    }
}