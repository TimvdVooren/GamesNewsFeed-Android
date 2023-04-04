package tick.nonprofit.gamesnewsfeed.domain.use_case

import kotlinx.coroutines.flow.Flow
import tick.nonprofit.gamesnewsfeed.domain.model.Game
import tick.nonprofit.gamesnewsfeed.domain.repository.GameRepository

class GetGames(
    private val repository: GameRepository
) {
    operator fun invoke(): Flow<List<Game>> {
        return repository.getGames()
    }
}