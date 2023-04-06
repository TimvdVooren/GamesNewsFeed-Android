package tick.nonprofit.gamesnewsfeed.presentation.subscribed_games

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
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
import tick.nonprofit.gamesnewsfeed.domain.util.LocalDateTimeConverter
import tick.nonprofit.gamesnewsfeed.presentation.MainViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreen(navController: NavController, viewModel: MainViewModel) {
    val gameList by viewModel.subscribedGames.collectAsState()

    viewModel.updateAppBar("Subscribed games")

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
                        ListItem(
                            gameList[i]
                        ) {
                            viewModel.updateDetailedGame(gameList[i]).let {
                                navController.navigate(GamesNewsfeedApp.NavRoutes.Detailed.name)
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                        text = "No games were found, subscribe to a new game")
                }
            }
        }
    }
}

@Composable
fun ListItem(item: Game, onClick: () -> Unit) {
    val isGameReleased =
        LocalDateTimeConverter.toDate(item.releaseDateTimeStamp).isBefore(LocalDateTime.now())
    val releaseStatusIcon = if (isGameReleased) Icons.Rounded.Check else Icons.Rounded.Clear
    val releaseIconColor = if (isGameReleased) Color.Green else Color.Red

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary))
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(0.8f)
                .padding(8.dp)
        ) {
            Text(text = item.name, fontWeight = FontWeight.Bold)

            val releaseDate = LocalDateTimeConverter.toDate(item.releaseDateTimeStamp)
            val customFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formattedString: String = releaseDate.format(customFormat)
            Text(
                text = formattedString,
                fontStyle = FontStyle.Italic
            )
        }

        Icon(
            imageVector = releaseStatusIcon,
            contentDescription = "Release status",
            tint = releaseIconColor,
            modifier = Modifier
                .weight(0.2f)
        )
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