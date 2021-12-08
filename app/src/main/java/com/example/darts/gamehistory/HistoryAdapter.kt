package com.example.darts.gamehistory

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.database.DartsDatabase
import com.example.darts.database.entities.Game
import com.example.darts.databinding.HistoryGameItemBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

class HistoryAdapter : ListAdapter<Game, HistoryAdapter.ViewHolder>(GameDiffCallback()) {

    lateinit var database: DartsDatabase

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: HistoryGameItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game) {

            val database = DartsDatabase.getInstance(binding.startingPointsText.context)
            val playerDatabase = database.playerDao()
            val tossDatabase = database.tossDao()
            val gameDatabase = database.gameDao()

            runBlocking {
                GlobalScope.launch {
                    var formatText: String = ""
                    playerDatabase.getPlayersInGame(item.id).forEach {
                        d("lauhyv", it)
                        formatText += it + "\n"
                    }

                    binding.playersPointsText.text = formatText
                }
            }

            binding.game = item

            binding.executePendingBindings()
        }



        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HistoryGameItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}