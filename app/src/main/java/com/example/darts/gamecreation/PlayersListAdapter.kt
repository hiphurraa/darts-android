package com.example.darts.gamecreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.R
import com.example.darts.database.DartsDatabase
import com.example.darts.database.PlayerDao
import com.example.darts.database.entities.Player
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayersListAdapter(private var players: List<Player>): RecyclerView.Adapter<PlayersListAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlayerName: TextView
        val cbPlayerSelected : CheckBox
        val playerDao: PlayerDao

        init {
            tvPlayerName = view.findViewById(R.id.tvPlayerName)
            cbPlayerSelected = view.findViewById(R.id.cbPlayerSelected)

            val context = view.context
            val database = DartsDatabase.getInstance(context)
            playerDao = database.playerDao()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_players_list_item, viewGroup, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.tvPlayerName.text = players[position].name
        viewHolder.cbPlayerSelected.isChecked = players[position].defaultSelected

        viewHolder.cbPlayerSelected.setOnCheckedChangeListener { _, isChecked ->
            GlobalScope.launch {
                viewHolder.playerDao.ChangeDefaultSelected(players[position].id, isChecked)
                GameCreationFragment.players = viewHolder.playerDao.getAll()
            }
        }
    }

    override fun getItemCount() = players.size

}