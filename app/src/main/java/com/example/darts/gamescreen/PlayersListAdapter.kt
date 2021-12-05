package com.example.darts.gamescreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.R
import com.example.darts.database.entities.Player

class PlayersListAdapter(private var players: List<Player>): RecyclerView.Adapter<PlayersListAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIngamePlayerName: TextView

        init {
            tvIngamePlayerName = view.findViewById(R.id.tvIngamePlayerName)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_ingame_players_list_item, viewGroup, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.tvIngamePlayerName.text = players[position].name
    }

    override fun getItemCount() = players.size

}