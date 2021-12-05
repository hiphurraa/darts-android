package com.example.darts.gamecreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.R
import com.example.darts.database.entities.Player

class PlayerRecyclerAdapter(private var players: List<Player>): RecyclerView.Adapter<PlayerRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlayerName: TextView

        init {
            tvPlayerName = view.findViewById(R.id.tvPlayerName)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_player_view, viewGroup, false)

        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvPlayerName.text = players[position].name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = players.size

}