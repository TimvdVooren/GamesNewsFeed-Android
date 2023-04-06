package tick.nonprofit.gamesnewsfeed

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import tick.nonprofit.gamesnewsfeed.presentation.MainViewModel
import tick.nonprofit.gamesnewsfeed.presentation.detailed_game.DetailedGameScreen
import tick.nonprofit.gamesnewsfeed.presentation.search_game.SearchScreen
import tick.nonprofit.gamesnewsfeed.presentation.subscribed_games.GameListScreen

@HiltAndroidApp
class GamesNewsfeedApp : Application() {

    enum class NavRoutes(val title: String) {
        GameList(title = "Game list"),
        Search(title = "Search"),
        Detailed(title = "Detailed")
    }

    companion object {
        //TODO: save this in a safe place
        const val CLIENT_ID = "x7wa8tt4q593437m1gf0z4z4c6aox7"
        const val CLIENT_SECRET = "zwfarstqsc11nykhbmur9vxb25ubod"
        val accessToken = mutableStateOf<String?>(null)
        private var currentScreen = NavRoutes.GameList

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun Launch() {
            val viewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            currentScreen = NavRoutes.valueOf(
                backStackEntry?.destination?.route ?: NavRoutes.GameList.name
            )

            val appBarTitle by viewModel.currentScreenTitle.collectAsState()
            val appBarAction by viewModel.appBarAction.collectAsState()

            Scaffold(
                topBar = {
                    CustomAppBar(
                        title = appBarTitle,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        action = appBarAction
                    )
                },
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background,
            ) { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.GameList.name,
                    modifier = Modifier.padding(paddingValues = paddingValues)
                ) {
                    composable(route = NavRoutes.GameList.name) {
                        GameListScreen(navController, viewModel)
                    }
                    composable(route = NavRoutes.Search.name) {
                        SearchScreen(navController, viewModel)
                    }
                    composable(route = NavRoutes.Detailed.name) {
                        DetailedGameScreen(navController, viewModel)
                    }
                }
            }
        }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        private fun CustomAppBar(
            title: String,
            canNavigateBack: Boolean = false,
            navigateUp: () -> Unit = {},
            action: (() -> Unit)? = null,
        ) {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = navigateUp) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    if (action != null) {
                        IconButton(onClick = {
                            action()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Delete game",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    }
}