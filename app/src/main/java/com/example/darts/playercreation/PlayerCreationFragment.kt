package com.example.darts.playercreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darts.R
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.databinding.FragmentPlayerCreationBinding
import com.example.darts.gamecreation.PlayerRecyclerAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerCreationFragment: Fragment() {

    private var _binding: FragmentPlayerCreationBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DartsDatabase
    private lateinit var playerDao: PlayerDao




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPlayerCreationBinding.inflate(inflater, container, false)

        val context = requireActivity().applicationContext
        database = DartsDatabase.getInstance(context)
        playerDao = database.playerDao()

        binding.navigationBar.tvTitle.text = resources.getString(R.string.pc_nav_title)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Back button */
        binding.navigationBar.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_playerCreationFragment_to_gameCreationFragment)
        }

        /** Create player button */
        binding.btnContinue.setOnClickListener {

            val playerName: String = binding.etPlayerName.text.toString()
            val autoSelect = binding.cbAutoSelectPlayer.isChecked

            val validationMsg: String = validatePlayerName(playerName)

            if (validationMsg == "ok") {
                GlobalScope.launch {
                    playerDao.insertPlayer(playerName, autoSelect)
                }
                Navigation.findNavController(view).navigate(R.id.action_playerCreationFragment_to_gameCreationFragment)

            } else {
                Toast.makeText(requireActivity().applicationContext, validationMsg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validatePlayerName (playerName: String): String {
        // TODO: Validate more thoroughly
        return if (playerName.length > 20) resources.getString(R.string.pc_name_too_long)
        else "ok"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}