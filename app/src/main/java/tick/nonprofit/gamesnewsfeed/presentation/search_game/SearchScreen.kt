package tick.nonprofit.gamesnewsfeed.presentation.search_game

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tick.nonprofit.gamesnewsfeed.GamesNewsfeedApp
import tick.nonprofit.gamesnewsfeed.presentation.MainViewModel

@Composable
fun SearchScreen(navController: NavController, viewModel: MainViewModel) {
    val isSubscribeSuccessful by viewModel.isSubscribeSuccessful.collectAsState()
    if (isSubscribeSuccessful) {
        navController.popBackStack(GamesNewsfeedApp.NavRoutes.GameList.name, inclusive = false)
    }

    viewModel.updateAppBar("Search for games")

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

        AutoCompleteSearchBar(viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.fetchGameByName(viewModel.selectedGameName.value)
        }) {
            Text(text = "Subscribe")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteSearchBar(viewModel: MainViewModel) {
    val listItems by viewModel.autoCompleteGameNames.collectAsState()
    val selectedName by viewModel.selectedGameName.collectAsState()
    val expanded by viewModel.isSearchBoxExpanded.collectAsState()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            viewModel.setSearchBoxExpandedState(!expanded)
        }
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedName,
            onValueChange = {
                viewModel.updateSelectedName(it)
                if (it.trim().isNotBlank()) {
                    viewModel.searchGamesIncludingString(it)
                    viewModel.setSearchBoxExpandedState(true)
                }
            },
            placeholder = { Text(text = "Enter a game name here") },
            trailingIcon = {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "Search for games"
                )
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(textColor = Color.Black)
        )

        if (listItems.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { viewModel.setSearchBoxExpandedState(false) }
            ) {
                listItems.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Text(text = selectionOption)
                        },
                        onClick = {
                            viewModel.updateSelectedName(selectionOption)
                            viewModel.setSearchBoxExpandedState(false)
                        }
                    )
                }
            }
        }
    }
}