package tick.nonprofit.gamesnewsfeed.presentation.subscribed_games

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.json.JSONObject
import tick.nonprofit.gamesnewsfeed.GamesNewsfeedApp
import tick.nonprofit.gamesnewsfeed.data.data_source.IgdbApi
import tick.nonprofit.gamesnewsfeed.data.data_source.TwitchApi
import tick.nonprofit.gamesnewsfeed.domain.use_case.GameUseCases
import javax.inject.Inject

@HiltViewModel
class SubscribedGamesViewModel @Inject constructor(
    private val gameUseCases: GameUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val twitchApi: TwitchApi,
    private val igdbApi: IgdbApi
): ViewModel() {
    private val GAME_LIST = "game_list"

    val state = savedStateHandle.getStateFlow(GAME_LIST, SubscribedGamesState())

    var getGamesJob: Job? = null

    init {
        getSubscribedGames()
        getAccessToken()
    }

    fun onEvent(event: SubscribedGamesEvent) {
        when(event){
            is SubscribedGamesEvent.GetGames -> {
                getSubscribedGames()
            }
            is SubscribedGamesEvent.DeleteGame -> {
                viewModelScope.launch {
                    gameUseCases.deleteGame(event.game)
                }
            }
        }
    }

    private fun getAccessToken(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = twitchApi.getAccessToken()
            GamesNewsfeedApp.accessToken.value = JSONObject(response.body().toString()).getString("access_token")
        }
    }

    private fun getSubscribedGames(){
        getGamesJob?.cancel()
        getGamesJob = gameUseCases.getGames()
            .onEach { games ->
                savedStateHandle[GAME_LIST] = games
            }.launchIn(viewModelScope)
    }
}