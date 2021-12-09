package com.example.darts.gamesettings

import android.graphics.Color.parseColor
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.example.darts.R
import com.example.darts.database.AppSettingsDao
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.database.entities.AppSettings
import com.example.darts.databinding.FragmentGameCreationBinding
import com.example.darts.databinding.FragmentGameSettingsBinding
import kotlinx.coroutines.*
import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import androidx.lifecycle.Observer
import java.util.*


class GameSettingsFragment: Fragment() {

    private var _binding: FragmentGameSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DartsDatabase
    private lateinit var appSettingsDao: AppSettingsDao


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameSettingsBinding.inflate(inflater, container, false)

        binding.navigationBar.tvTitle.text = getString(R.string.gs_nav_title);

        val context = requireActivity().applicationContext
        database = DartsDatabase.getInstance(context)
        appSettingsDao = database.appSettingsDao()

        val viewModelFactory = GameSettingsViewModelFactory(appSettingsDao)

        val gameSettingsViewModel = ViewModelProvider(this, viewModelFactory).get(GameSettingsViewModel::class.java)

        binding.gameSettingsViewModel = gameSettingsViewModel

        binding.setLifecycleOwner(this)

        val appSettingsObserver = Observer<AppSettings> { AppSettings ->
            if(AppSettings.language == "FI") {
                binding.btnGsLanguageEnglish.setBackgroundColor(getColor(context, R.color.dark_gray))
                binding.btnGsLanguageFinnish.setBackgroundColor(getColor(context, R.color.main_blue))
                setLocale(requireActivity(), "fi")
            } else {
                binding.btnGsLanguageFinnish.setBackgroundColor(getColor(context, R.color.dark_gray))
                binding.btnGsLanguageEnglish.setBackgroundColor(getColor(context, R.color.main_blue))
                setLocale(requireActivity(), "en")
            }

            if(AppSettings.speedEntryEnabled!!) {
                binding.btnGsPointsSpeedYes.setBackgroundColor(getColor(context, R.color.main_blue))
                binding.btnGsPointsSpeedNo.setBackgroundColor(getColor(context, R.color.dark_gray))
            } else {
                binding.btnGsPointsSpeedYes.setBackgroundColor(getColor(context, R.color.dark_gray))
                binding.btnGsPointsSpeedNo.setBackgroundColor(getColor(context, R.color.main_blue))

            }
        }

        gameSettingsViewModel.getAppSettings().observe(viewLifecycleOwner, appSettingsObserver)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back button
        binding.navigationBar.btnBack.setOnClickListener {
            //d("layhyv", appSettings.value?.language.toString())
            Navigation.findNavController(view).navigate(R.id.action_gameSettingsFragment_to_gameMenuFragment)
        }
    }

    fun setLocale(activity: Activity, languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.getConfiguration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }
}
