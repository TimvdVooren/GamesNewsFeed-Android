package tick.nonprofit.gamesnewsfeed.presentation.detailed_game

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun DetailedGameScreen(navController: NavController) {
    val viewModel: DetailedGameViewModel = hiltViewModel()
    // navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}