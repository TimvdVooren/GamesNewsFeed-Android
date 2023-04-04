package tick.nonprofit.gamesnewsfeed.presentation.search_game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tick.nonprofit.gamesnewsfeed.domain.model.Game
import tick.nonprofit.gamesnewsfeed.data.data_source.IgdbApi
import tick.nonprofit.gamesnewsfeed.data.data_source.IgdbApi.Companion.getHeaderMap
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val igdbApi: IgdbApi
): ViewModel() {

    fun fetchGameByName(name: String) {
        viewModelScope.launch {
            try {
                val bodyText = "fields *; where name = \"$name\";"
                val body = IgdbApi.generateBody(bodyText)
                val response = igdbApi.fetchGameByName(getHeaderMap(), body)

                val game = Gson().fromJson(response.body()?.get(0), Game::class.java)

            } catch (e: Exception) {
                Log.e("SearchViewModel", "getGameByName: ", e)
            }
        }
    }

}