package com.example.darts.gamesettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.darts.R
import com.example.darts.databinding.FragmentGameSettingsBinding

class GameSettingsFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentGameSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }
}