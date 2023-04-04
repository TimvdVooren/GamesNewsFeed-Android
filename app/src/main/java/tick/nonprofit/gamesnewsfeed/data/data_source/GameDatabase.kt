package tick.nonprofit.gamesnewsfeed.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import tick.nonprofit.gamesnewsfeed.domain.model.Game

@Database(
    entities = [Game::class],
    version = 1
)
abstract class GameDatabase: RoomDatabase() {

    abstract val gameDao: GameDao

    companion object {
        const val DATABASE_NAME = "games_db"
    }
}