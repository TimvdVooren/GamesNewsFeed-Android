package tick.nonprofit.gamesnewsfeed.presentation.detailed_game

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tick.nonprofit.gamesnewsfeed.GamesNewsfeedApp
import tick.nonprofit.gamesnewsfeed.presentation.MainViewModel

@Composable
fun DetailedGameScreen(navController: NavController, viewModel: MainViewModel) {
    val detailedGame by viewModel.detailedGame.collectAsState()

    detailedGame?.let { game ->

        viewModel.updateAppBar(game.name) {
            viewModel.unsubscribeToGame(game)
            navController.popBackStack(GamesNewsfeedApp.NavRoutes.GameList.name, inclusive = false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = game.name)
        }
    }
}