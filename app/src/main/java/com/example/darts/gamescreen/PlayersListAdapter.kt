package com.example.darts.gamescreen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.R

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
        val player = players[position]
        var latestScore = 0
        var playerText = ""
        if (player.latestTurn != null){
            if (player.latestTurn!!.tosses[0] != null) {
                latestScore += (player.latestTurn!!.tosses[0]!!.points * player.latestTurn!!.tosses[0]!!.factor)
            }
            if (player.latestTurn!!.tosses[1] != null) {
                latestScore += (player.latestTurn!!.tosses[1]!!.points * player.latestTurn!!.tosses[1]!!.factor)
            }
            if (player.latestTurn!!.tosses[2] != null) {
                latestScore += (player.latestTurn!!.tosses[2]!!.points * player.latestTurn!!.tosses[2]!!.factor)
            }

            playerText = player.name + " - " + player.pointsLeft + " (viimeisin: " + latestScore + ")"
        }
        else {
            playerText = player.name + " - " + player.pointsLeft
        }

        viewHolder.tvIngamePlayerName.text = playerText

        if(players[position].isCurrentPlayer) {
            viewHolder.tvIngamePlayerName.setTextColor(Color.rgb(255, 255, 255))
            viewHolder.tvIngamePlayerName.setBackgroundColor(Color.rgb(51, 116, 215))
        }
        else {
            viewHolder.tvIngamePlayerName.setTextColor(Color.rgb(0, 0, 0))
            viewHolder.tvIngamePlayerName.setBackgroundColor(Color.rgb(255, 255, 255))
        }
    }

    override fun getItemCount() = players.size

}