package tick.nonprofit.gamesnewsfeed.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
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
import tick.nonprofit.gamesnewsfeed.domain.model.Game
import tick.nonprofit.gamesnewsfeed.domain.use_case.GameUseCases
import tick.nonprofit.gamesnewsfeed.presentation.subscribed_games.SubscribedGamesEvent
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gameUseCases: GameUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val twitchApi: TwitchApi,
    private val igdbApi: IgdbApi
) : ViewModel() {
    private val GAME_LIST = "game_list"
    private val AUTO_COMPLETE_GAME_NAMES = "auto_complete_game_names"
    private val SELECTED_GAME_NAME = "selected_game_name"
    private val SEARCH_BOX_EXPANDED = "search_box_expanded"
    private val SUBSCRIBE_SUCCESS = "subscribe_success"

    val subscribedGames = savedStateHandle.getStateFlow(GAME_LIST, emptyList<Game>())
    val autoCompleteGameNames = savedStateHandle.getStateFlow(AUTO_COMPLETE_GAME_NAMES, emptyList<String>())
    val selectedGameName = savedStateHandle.getStateFlow(SELECTED_GAME_NAME, "")
    val isSearchBoxExpanded = savedStateHandle.getStateFlow(SEARCH_BOX_EXPANDED, false)
    val isSubscribeSuccessful = savedStateHandle.getStateFlow(SUBSCRIBE_SUCCESS, false)
    var getGamesJob: Job? = null

    init {
        getSubscribedGames()
        getAccessToken()
    }

    fun onEvent(event: SubscribedGamesEvent) {
        when (event) {
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

    private fun getAccessToken() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = twitchApi.getAccessToken()
            GamesNewsfeedApp.accessToken.value =
                JSONObject(response.body().toString()).getString("access_token")
        }
    }

    private fun getSubscribedGames() {
        getGamesJob?.cancel()
        getGamesJob = gameUseCases.getGames()
            .onEach { games ->
                savedStateHandle[GAME_LIST] = games
            }.launchIn(viewModelScope)
    }

    fun searchGamesIncludingString(partialName: String) {
        viewModelScope.launch {
            try {
                val bodyText = "search \"$partialName\"; fields name;"
                val body = IgdbApi.generateBody(bodyText)
                val response = igdbApi.fetchGameByName(IgdbApi.getHeaderMap(), body)

                val gameNames = emptyList<String>().toMutableList()
                response.body()?.asJsonArray?.forEach { json ->
                    val gameName = json.asJsonObject.get("name").asString
                    gameNames.add(gameName)
                }
                savedStateHandle[AUTO_COMPLETE_GAME_NAMES] = gameNames
            } catch (e: Exception) {
                Log.e("MainViewModel", "searchGamesIncludingString: ", e)
            }
        }
    }

    fun fetchGameByName(name: String) {
        viewModelScope.launch {
            try {
                val bodyText = "fields *; where name = \"$name\";"
                val body = IgdbApi.generateBody(bodyText)
                val response = igdbApi.fetchGameByName(IgdbApi.getHeaderMap(), body)

                val game = Gson().fromJson(response.body()?.get(0), Game::class.java)
                subscribeToGame(game)
                savedStateHandle[SUBSCRIBE_SUCCESS] = true
            } catch (e: Exception) {
                Log.e("MainViewModel", "fetchGameByName: ", e)
            }
        }
    }

    private fun subscribeToGame(game: Game) {
        viewModelScope.launch {
            gameUseCases.addGame(game)
        }
    }

    fun updateSelectedName(selectedGameName: String) {
        viewModelScope.launch {
            savedStateHandle[SELECTED_GAME_NAME] = selectedGameName
        }
    }

    fun setSearchBoxExpandedState(isExpanded: Boolean) {
        viewModelScope.launch {
            savedStateHandle[SEARCH_BOX_EXPANDED] = isExpanded
        }
    }
}