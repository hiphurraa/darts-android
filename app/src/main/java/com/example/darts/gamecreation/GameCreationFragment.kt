package com.example.darts.gamecreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.darts.R
import com.example.darts.databinding.FragmentGameCreationBinding

private var _binding: FragmentGameCreationBinding? = null
// This property is only valid between onCreateView and
// onDestroyView.
private val binding get() = _binding!!
private val FIRST_VIEW = "first"
private val SECOND_VIEW = "second"
private val THIRD_VIEW = "third"
private var currentView = FIRST_VIEW

class GameCreationFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationBar.btnBack.setOnClickListener {
            if (currentView == FIRST_VIEW) {
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

        binding.gameCreationFirstView.btnContinue.setOnClickListener {
            binding.gameCreationFirstView.root.visibility = View.INVISIBLE
            binding.gameCreationSecondView.root.visibility = View.VISIBLE
            currentView = SECOND_VIEW
        }

        binding.gameCreationSecondView.btnContinue.setOnClickListener {
            binding.gameCreationSecondView.root.visibility = View.INVISIBLE
            binding.gameCreationThirdView.root.visibility = View.VISIBLE
            currentView = THIRD_VIEW
        }
    }
}