package tick.nonprofit.gamesnewsfeed.presentation.subscribed_games

import tick.nonprofit.gamesnewsfeed.domain.model.Game

sealed class SubscribedGamesEvent {
    object GetGames: SubscribedGamesEvent()
    data class DeleteGame(val game: Game): SubscribedGamesEvent()
}