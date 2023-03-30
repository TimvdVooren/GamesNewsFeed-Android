package tick.nonprofit.gamesnewsfeed.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tick.nonprofit.gamesnewsfeed.GamesNewsfeedApp.Companion.getHeaderMap
import tick.nonprofit.gamesnewsfeed.data.Game
import tick.nonprofit.gamesnewsfeed.data.IgdbApi
import tick.nonprofit.gamesnewsfeed.data.TwitchApi
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val twitchApi: TwitchApi,
    private val igdbApi: IgdbApi
): ViewModel() {

    fun fetchGameByName(name: String) {
        viewModelScope.launch {
            try {
                val bodyText = "fields *; where name = \"$name\";"
                val body = IgdbApi.generateBody(bodyText)

                val response = igdbApi.fetchGameByName(getHeaderMap(), body)

                //TODO add game to sharedpref
                val game = Gson().fromJson(response.body()?.get(0), Game::class.java)

                // TODO: update state
//                _state.value = state.value.copy(isLoading = true)
//                _state.value = state.value.copy(
//                    gameList = emptyList(),
//                    isLoading = false
//                )
            } catch (e: Exception) {
                Log.e("SearchViewModel", "getGameByName: ", e)
            }
        }
    }

}