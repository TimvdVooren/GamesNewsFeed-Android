package tick.nonprofit.gamesnewsfeed.presentation.search_game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tick.nonprofit.gamesnewsfeed.GamesNewsfeedApp
import tick.nonprofit.gamesnewsfeed.presentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    val isSubscribeSuccessful = viewModel.isSubscribeSuccessful.collectAsState().value

    if (isSubscribeSuccessful) {
        navController.popBackStack(GamesNewsfeedApp.NavRoutes.GameList.name, inclusive = false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What game are you looking for?",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = "",
            onValueChange = {
                viewModel.searchGamesIncludingString(it)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            // TODO get name from textfield
            viewModel.fetchGameByName("The Witcher 3: Wild Hunt")
        }) {
            Text(text = "Subscribe")
        }
    }
}