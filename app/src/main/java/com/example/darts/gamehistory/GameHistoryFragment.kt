package com.example.darts.gamehistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.darts.R
import com.example.darts.databinding.FragmentGameHistoryBinding

private var _binding: FragmentGameHistoryBinding? = null
private val binding get() = _binding!!

class GameHistoryFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameHistoryBinding.inflate(inflater, container, false)
        // Setting the navigaton bar title
        binding.navigationBar.tvTitle.text = resources.getString(R.string.gh_nav_title)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Back to game menu fragment */
        binding.navigationBar.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_gameHistoryFragment_to_gameMenuFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}