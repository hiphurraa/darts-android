package com.example.darts.gamemenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.darts.R
import com.example.darts.databinding.FragmentGameMenuBinding

class GameMenuFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_menu, container, false)

        return binding.root
    }
}