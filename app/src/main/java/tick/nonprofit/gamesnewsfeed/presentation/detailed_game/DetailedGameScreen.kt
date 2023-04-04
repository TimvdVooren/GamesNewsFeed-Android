package tick.nonprofit.gamesnewsfeed.presentation.detailed_game

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tick.nonprofit.gamesnewsfeed.presentation.MainViewModel

@Composable
fun DetailedGameScreen(navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    // navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}