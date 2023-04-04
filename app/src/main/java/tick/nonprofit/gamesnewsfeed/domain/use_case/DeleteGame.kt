package tick.nonprofit.gamesnewsfeed.domain.use_case

import tick.nonprofit.gamesnewsfeed.domain.model.Game
import tick.nonprofit.gamesnewsfeed.domain.repository.GameRepository

class DeleteGame(
    private val repository: GameRepository
) {

    suspend operator fun invoke(game: Game) {
        repository.deleteGame(game)
    }
}