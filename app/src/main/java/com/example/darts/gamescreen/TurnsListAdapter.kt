package com.example.darts.gamescreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.R

class TurnsListAdapter(private var turns: List<Turn>, private var context: Context): RecyclerView.Adapter<TurnsListAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTurnInfo: TextView

        init {
            tvTurnInfo = view.findViewById(R.id.tvTurnInfo)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_ingame_turns_list_item, viewGroup, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val turn = turns[position]
        var tossesInfoText = turn.playerName

        /** Add tosses points and factors to info text */
        turn.tosses.forEachIndexed { index, it ->
            if (it != null) {
                when (index) {
                    0 -> {
                        when(it.factor){
                            1 -> tossesInfoText +=  " - (" + it.points + ")"
                            2 -> tossesInfoText +=  " - 2x(" + it.points + ")"
                            3 -> tossesInfoText +=  " - 3x(" + it.points + ")"
                        }
                    }
                    else -> {
                        when(it.factor){
                            1 -> tossesInfoText +=  ", (" + it.points + ")"
                            2 -> tossesInfoText +=  ", 2x(" + it.points + ")"
                            3 -> tossesInfoText +=  ", 3x(" + it.points + ")"
                        }
                    }
                }
            }
            else if (itemCount == 1){
                tossesInfoText = context.getString(R.string.gs_no_previous_turns)
            }
            else {
                tossesInfoText = ""
            }
        }

        viewHolder.tvTurnInfo.text = tossesInfoText
    }

    override fun getItemCount() = turns.size

}