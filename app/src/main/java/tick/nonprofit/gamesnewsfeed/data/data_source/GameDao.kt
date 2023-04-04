package tick.nonprofit.gamesnewsfeed.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tick.nonprofit.gamesnewsfeed.domain.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM game")
    fun getGames(): Flow<List<Game>>

    @Query("SELECT * FROM game WHERE id = :id")
    suspend fun getGameById(id: Int): Game?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)
}