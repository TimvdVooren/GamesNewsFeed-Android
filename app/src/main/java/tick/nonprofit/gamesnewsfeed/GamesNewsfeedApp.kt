package tick.nonprofit.gamesnewsfeed

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import tick.nonprofit.gamesnewsfeed.presentation.subscribed_games.GameListScreen
import tick.nonprofit.gamesnewsfeed.presentation.views.SearchScreen

@HiltAndroidApp
class GamesNewsfeedApp : Application() {

    enum class NavRoutes(val title: String) {
        GameList(title = "Game list"),
        Search(title = "Search")
    }

    companion object {
        const val CLIENT_ID = "x7wa8tt4q593437m1gf0z4z4c6aox7"
        const val CLIENT_SECRET = "zwfarstqsc11nykhbmur9vxb25ubod"
        val accessToken = mutableStateOf<String?>(null)
        private var currentScreen = NavRoutes.GameList

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun Launch() {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            currentScreen = NavRoutes.valueOf(
                backStackEntry?.destination?.route ?: NavRoutes.GameList.name
            )

            Scaffold(
                topBar = {
                    CustomAppBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() }
                    )
                },
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.GameList.name,
                    modifier = Modifier.padding(paddingValues = it)
                ) {
                    composable(route = NavRoutes.GameList.name) {
                        GameListScreen(navController)
                    }
                    composable(route = NavRoutes.Search.name) {
                        SearchScreen(navController)
                    }
                }
            }
        }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        private fun CustomAppBar(
            currentScreen: NavRoutes,
            canNavigateBack: Boolean = false,
            navigateUp: () -> Unit = {},
        ) {
            TopAppBar(
                title = { Text(currentScreen.title) },
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
            )
        }

//        @Composable
//        private fun HandleLifecycleEvents() {
//            val lifecycleState = LocalLifecycleOwner.current.lifecycle.observeAsState().value
//            when(lifecycleState) {
//                Lifecycle.Event.ON_CREATE -> TODO()
//                Lifecycle.Event.ON_START -> TODO()
//                Lifecycle.Event.ON_RESUME -> {
//                    if (currentScreen == NavRoutes.GameList) {
//
//                    }
//                }
//                Lifecycle.Event.ON_PAUSE -> TODO()
//                Lifecycle.Event.ON_STOP -> TODO()
//                Lifecycle.Event.ON_DESTROY -> TODO()
//                Lifecycle.Event.ON_ANY -> TODO()
//            }
//        }
//
//        @Composable
//        fun Lifecycle.observeAsState(): State<Lifecycle.Event> {
//            val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
//            DisposableEffect(this) {
//                val observer = LifecycleEventObserver { _, event ->
//                    state.value = event
//                }
//                this@observeAsState.addObserver(observer)
//                onDispose {
//                    this@observeAsState.removeObserver(observer)
//                }
//            }
//            return state
//        }
    }
}