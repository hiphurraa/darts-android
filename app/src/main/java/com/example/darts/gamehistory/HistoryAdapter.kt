package com.example.darts.gamehistory

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.darts.database.DartsDatabase
import com.example.darts.database.GameDao
import com.example.darts.database.PlayerDao
import com.example.darts.database.TossDao
import com.example.darts.database.entities.Game
import com.example.darts.database.entities.Player
import com.example.darts.database.entities.Toss
import com.example.darts.databinding.HistoryGameItemBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat

class HistoryAdapter : ListAdapter<GameInfo, HistoryAdapter.ViewHolder>(GameInfoDiffCallback()) {

    lateinit var database: DartsDatabase

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: HistoryGameItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameInfo) {



            binding.gameInfo = item
            binding.startingPointsText.text = item.startingPoints
            binding.playersPointsText.text = item.playerString
            binding.historyItemDetailDateText.text = item.dateString

            binding.executePendingBindings()
        }



        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HistoryGameItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun printDb(db: DartsDatabase) {
            GlobalScope.launch {
                var tosses: LiveData<List<Toss>>
                runBlocking {
                    tosses = db.tossDao().getAll()
                }
                tosses.value?.forEach {
                    d("lauhyv", it.points.toString())
                }
            }
        }

        fun getFormattedPlayerString(game: Game, playerDao: PlayerDao, gameDao: GameDao, tossDao: TossDao): String {
            var returnString = ""
            runBlocking {
                GlobalScope.launch {
                    val playerIds: List<Long> = playerDao.getPlayersInGame(game.id)
                    val players: List<Player> = playerDao.getAll()

                    var playerMap: MutableMap<Long, Player> = HashMap<Long, Player>()
                    players.forEach {
                        playerMap[it.id] = it
                    }

                    playerIds.forEach {
                        val tosses = tossDao.getTossesFromPlayerInGame(it, game.id)
                        var playersPoints: Int = 0
                        tosses.forEach {
                            if(it.points != null) {
                                playersPoints += it.points!!
                            }
                        }
                        returnString += "${playerMap[it]} - ${playersPoints}\n"
                    }
                }
            }
            return returnString
        }

        fun getFormattedDate(game: Game): String {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            return dateFormat.format(game.startTime)
        }
    }

}

class GameInfoDiffCallback : DiffUtil.ItemCallback<GameInfo>() {
    override fun areItemsTheSame(oldItem: GameInfo, newItem: GameInfo): Boolean {
        return oldItem.gameId == newItem.gameId
    }

    override fun areContentsTheSame(oldItem: GameInfo, newItem: GameInfo): Boolean {
        return oldItem.equals(newItem)
    }
}