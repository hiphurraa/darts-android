package com.example.darts.gamescreen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.R

class PlayersListAdapter(private var players: MutableList<Player>): RecyclerView.Adapter<PlayersListAdapter.MyViewHolder>() {

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
        val player = players[position]
        var latestScore = 0 // Total points from players previous turn
        var playerText = ""

        if (player.latestTurn != null){
            /** Add all tosses to score */
            player.latestTurn!!.tosses.forEach {
                if (it != null) {
                    latestScore += it.points * it.factor
                }
            }
            /** Show the latest score beside the player name */
            playerText = player.name + " - " + player.pointsLeft + " (viimeisin: " + latestScore + ")"
        }
        else {
            /** Show only remaining point, as there's no latest score */
            playerText = player.name + " - " + player.pointsLeft
        }

        viewHolder.tvIngamePlayerName.text = playerText

        /** Highlight the current player with blue background & white text */
        if(players[position].isCurrentPlayer) {
            viewHolder.tvIngamePlayerName.setTextColor(Color.rgb(255, 255, 255))
            viewHolder.tvIngamePlayerName.setBackgroundColor(Color.rgb(51, 116, 215))
        }
        else {
            /** Default colors */
            viewHolder.tvIngamePlayerName.setTextColor(Color.rgb(0, 0, 0))
            viewHolder.tvIngamePlayerName.setBackgroundColor(Color.rgb(255, 255, 255))
        }
    }

    override fun getItemCount() = players.size

}