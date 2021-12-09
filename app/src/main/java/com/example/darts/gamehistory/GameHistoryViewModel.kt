package com.example.darts.gamehistory

import android.app.Application
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darts.database.DartsDatabase
import com.example.darts.database.GameDao
import com.example.darts.database.PlayerDao
import com.example.darts.database.TossDao
import com.example.darts.database.entities.Player
import com.example.darts.database.entities.Toss
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import com.example.darts.database.entities.Game as GameEntity

class GameHistoryViewModel(dataSource: DartsDatabase, application: Application): ViewModel() {
    private val database = dataSource
    private val gameDatabase = dataSource.gameDao()

    val gamesList = buildGamesInfoList()



    fun onInsert() {
        GlobalScope.launch {
            gameDatabase.insertGame(GameEntity(0, System.currentTimeMillis(), 501, "Torstai tikka"))
        }
    }

    fun onClear() {
        GlobalScope.launch {
            gameDatabase.clear()
        }
    }

    fun buildGamesInfoList(): LiveData<MutableList<GameInfo>> {
        val gameInfos: MutableList<GameInfo> = mutableListOf<GameInfo>()
        runBlocking {
            GlobalScope.launch {
                val games = database.gameDao().getAllSync()
                games.forEach {

                    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
                    val startTime = dateFormat.format(it.startTime)

                    val gameInfo = GameInfo(it.id, startTime, getFormattedPlayerString(it), it.startingPoints.toString())
                    gameInfos.add(gameInfo)
                }
            }
        }

        return MutableLiveData<MutableList<GameInfo>>(gameInfos)
    }

    fun getFormattedPlayerString(game: GameEntity): String {
        val playerDao: PlayerDao = database.playerDao()
        val gameDao: GameDao = database.gameDao()
        val tossDao: TossDao = database.tossDao()
        var returnString = ""
        runBlocking {
            val job = GlobalScope.launch {
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
                    returnString += "${playerMap[it]?.name} - ${game.startingPoints - playersPoints}\n"
                }
            }
            job.join()
        }
        return returnString
    }


}

/*



 */