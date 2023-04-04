package tick.nonprofit.gamesnewsfeed.presentation.subscribed_games

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tick.nonprofit.gamesnewsfeed.GamesNewsfeedApp
import tick.nonprofit.gamesnewsfeed.domain.model.Game
import tick.nonprofit.gamesnewsfeed.presentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreen(navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    val gameList by viewModel.subscribedGames.collectAsState()

    Scaffold(
        floatingActionButton = {
            NewSubscriptionButton(onClick = {
                navController.navigate(GamesNewsfeedApp.NavRoutes.Search.name)
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            if (gameList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(gameList.size) { i ->
                        ListItem(gameList[i])
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No games were found, subscribe to a new game")
                }
            }
        }
    }
}

@Composable
fun ListItem(item: Game) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary))
            .padding(8.dp)
    ) {
        Text(text = item.name, fontWeight = FontWeight.Bold)
        Text(text = item.expectedReleaseDate.toString(), fontStyle = FontStyle.Italic)
    }
}

@Composable
fun NewSubscriptionButton(onClick: () -> Unit, isExpanded: Boolean = true) {
    ExtendedFloatingActionButton(
        text = {
            Text(text = "New subscription", color = Color.White)
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "New subscription",
                tint = Color.White,
            )
        },
        onClick = onClick,
        expanded = isExpanded,
        containerColor = MaterialTheme.colorScheme.primary,
    )
}