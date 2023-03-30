package tick.nonprofit.gamesnewsfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import tick.nonprofit.gamesnewsfeed.ui.theme.GamesNewsfeedTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GamesNewsfeedTheme {
                GamesNewsfeedApp.Launch()
            }
        }
    }
}