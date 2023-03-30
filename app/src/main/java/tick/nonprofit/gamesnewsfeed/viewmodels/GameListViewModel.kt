package tick.nonprofit.gamesnewsfeed.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import tick.nonprofit.gamesnewsfeed.GamesNewsfeedApp
import tick.nonprofit.gamesnewsfeed.GamesNewsfeedApp.Companion.getHeaderMap
import tick.nonprofit.gamesnewsfeed.data.Game
import tick.nonprofit.gamesnewsfeed.data.IgdbApi
import tick.nonprofit.gamesnewsfeed.data.TwitchApi
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val twitchApi: TwitchApi,
    private val igdbApi: IgdbApi
): ViewModel() {
    private val _state = mutableStateOf(GameListState())
    private val _navigateToSearchScreen = mutableStateOf(false)
    val gameList = mutableListOf<Game>()
    val state: State<GameListState> = _state

    init {
        getAccessToken()

        //TODO: remove testvalues
        val testValue = Game("God of War", 1011999)
        for (i in 0..20)
            gameList.add(testValue)
    }

    fun fetchSubscribedGames() {
        viewModelScope.launch {
            try {
                // TODO: load subscribed games from local storage

                val response = igdbApi.fetchSubscribedGames(getHeaderMap())
                Log.d("RESPONSE", response.body().toString())

                // TODO: get subscribed games from API
//                _state.value = state.value.copy(isLoading = true)
//                _state.value = state.value.copy(
//                    gameList = emptyList(),
//                    isLoading = false
//                )
            } catch (e: Exception) {
                Log.e("MainViewModel", "getSubscribedGames: ", e)
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }

    fun onNewSubscriptionClick() {
        _navigateToSearchScreen.value = true
    }

    private fun getAccessToken(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = twitchApi.getAccessToken()
            GamesNewsfeedApp.accessToken.value = JSONObject(response.body().toString()).getString("access_token")

            fetchSubscribedGames()
        }
    }

    data class GameListState(
        val gameList: List<Game> = emptyList(),
        val isLoading: Boolean = false
    )
}