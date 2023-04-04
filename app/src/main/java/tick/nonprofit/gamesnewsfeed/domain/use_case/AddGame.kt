package tick.nonprofit.gamesnewsfeed.domain.use_case

import tick.nonprofit.gamesnewsfeed.domain.model.Game
import tick.nonprofit.gamesnewsfeed.domain.model.InvalidGameException
import tick.nonprofit.gamesnewsfeed.domain.repository.GameRepository

class AddGame(
    private val repository: GameRepository
) {

    @Throws(InvalidGameException::class)
    suspend operator fun invoke(game: Game) {
        if (game.name.isBlank()) {
            throw InvalidGameException("The title of the game can't be empty")
        }
        repository.insertGame(game)
    }
}