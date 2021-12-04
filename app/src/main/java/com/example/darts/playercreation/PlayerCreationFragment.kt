package com.example.darts.playercreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.darts.R
import com.example.darts.databinding.FragmentPlayerCreationBinding

private var _binding: FragmentPlayerCreationBinding? = null
private val binding get() = _binding!!

class PlayerCreationFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPlayerCreationBinding.inflate(inflater, container, false)
        // Setting the navigaton bar title
        binding.navigationBar.tvTitle.text = resources.getString(R.string.pc_nav_title)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back button
        binding.navigationBar.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_playerCreationFragment_to_gameCreationFragment)
        }

        // Continue button
        binding.btnContinue.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_playerCreationFragment_to_gameCreationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}