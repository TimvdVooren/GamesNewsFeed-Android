package tick.nonprofit.gamesnewsfeed.domain.use_case

data class GameUseCases(
    val getGames: GetGames,
    val deleteGame: DeleteGame,
    val addGame: AddGame
)