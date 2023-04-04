package tick.nonprofit.gamesnewsfeed.presentation.subscribed_games

import tick.nonprofit.gamesnewsfeed.domain.model.Game

data class SubscribedGamesState(
    val subscribedGames: List<Game> = emptyList()
)