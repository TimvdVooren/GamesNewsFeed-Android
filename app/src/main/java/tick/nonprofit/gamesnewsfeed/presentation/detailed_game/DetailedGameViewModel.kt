package tick.nonprofit.gamesnewsfeed.presentation.detailed_game

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tick.nonprofit.gamesnewsfeed.data.data_source.IgdbApi
import javax.inject.Inject

@HiltViewModel
class DetailedGameViewModel @Inject constructor(
    private val igdbApi: IgdbApi
): ViewModel() {

}