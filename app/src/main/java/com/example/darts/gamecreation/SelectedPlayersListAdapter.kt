package com.example.darts.gamecreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.R
import com.example.darts.database.entities.Player

class SelectedPlayersListAdapter(private var selectedPlayers: List<Player>): RecyclerView.Adapter<SelectedPlayersListAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSelectedPlayerName: TextView

        init {
            tvSelectedPlayerName = view.findViewById(R.id.tvSelectedPlayerName)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_selected_players_list_item, viewGroup, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.tvSelectedPlayerName.text = selectedPlayers[position].name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = selectedPlayers.size

}