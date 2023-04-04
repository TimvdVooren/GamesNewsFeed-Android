package tick.nonprofit.gamesnewsfeed.data.repository

import kotlinx.coroutines.flow.Flow
import tick.nonprofit.gamesnewsfeed.data.data_source.GameDao
import tick.nonprofit.gamesnewsfeed.domain.model.Game
import tick.nonprofit.gamesnewsfeed.domain.repository.GameRepository

class GameRepositoryImpl(
    private val dao: GameDao
): GameRepository {

    override fun getGames(): Flow<List<Game>> {
        return dao.getGames()
    }

    override suspend fun getGameById(id: Int): Game? {
        return dao.getGameById(id)
    }

    override suspend fun insertGame(game: Game) {
        dao.insertGame(game)
    }

    override suspend fun deleteGame(game: Game) {
        dao.deleteGame(game)
    }

}