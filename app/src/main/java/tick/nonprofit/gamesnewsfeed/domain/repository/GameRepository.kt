package tick.nonprofit.gamesnewsfeed.domain.repository

import kotlinx.coroutines.flow.Flow
import tick.nonprofit.gamesnewsfeed.domain.model.Game

interface GameRepository {

    fun getGames(): Flow<List<Game>>

    suspend fun getGameById(id: Int): Game?

    suspend fun insertGame(game: Game)

    suspend fun deleteGame(game: Game)
}