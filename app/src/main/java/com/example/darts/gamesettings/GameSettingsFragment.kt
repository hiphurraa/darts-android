package com.example.darts.gamesettings

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.darts.R
import com.example.darts.database.AppSettingsDao
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.database.entities.AppSettings
import com.example.darts.databinding.FragmentGameCreationBinding
import com.example.darts.databinding.FragmentGameSettingsBinding
import kotlinx.coroutines.*

class GameSettingsFragment: Fragment() {

    private var _binding: FragmentGameSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DartsDatabase
    private lateinit var appSettingsDao: AppSettingsDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameSettingsBinding.inflate(inflater, container, false)

        val context = requireActivity().applicationContext
        database = DartsDatabase.getInstance(context)
        appSettingsDao = database.appSettingsDao()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var appSettings: AppSettings?
        runBlocking {
            appSettings = getSettings()
        }
        if(appSettings == null) {
            runBlocking {
                appSettings = insertDefaultSettings()
            }
        }

        // Button clicks
        binding.btnGsLanguageEnglish.setOnClickListener {
            val newSettings = AppSettings(appSettings?.id!!, "ENG", appSettings?.speedEntryEnabled!!)
            updateSettings(newSettings)
        }

        binding.btnGsLanguageFinnish.setOnClickListener {
            val newSettings = AppSettings(appSettings?.id!!, "FI", appSettings?.speedEntryEnabled!!)
            updateSettings(newSettings)
        }

        binding.btnGsPointsSpeedNo.setOnClickListener {
            val newSettings = AppSettings(appSettings?.id!!, appSettings?.language!!, false)
            updateSettings(newSettings)
        }

        binding.btnGsPointsSpeedYes.setOnClickListener {
            val newSettings = AppSettings(appSettings?.id!!, appSettings?.language!!, true)
            updateSettings(newSettings)
        }
    }

     private suspend fun getSettings(): AppSettings? {
        var appSettings: AppSettings? = null
        var job = GlobalScope.launch {
            appSettings = appSettingsDao.getSettings()
        }
        job.join()
        return appSettings
    }

    private fun insertDefaultSettings(): AppSettings {
        val defaultAppSettings: AppSettings = AppSettings(1, "FI", false)
        GlobalScope.launch {
            appSettingsDao.insertSettings("FI", false)
        }
        return defaultAppSettings
    }

    private fun updateSettings(updatedAppSettings: AppSettings) {
        GlobalScope.launch(context =  Dispatchers.Default){
            appSettingsDao.updateAppSettings(updatedAppSettings)
        }
    }
}