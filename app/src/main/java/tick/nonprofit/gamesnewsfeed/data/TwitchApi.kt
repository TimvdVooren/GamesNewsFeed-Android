package tick.nonprofit.gamesnewsfeed.data

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.POST
import tick.nonprofit.gamesnewsfeed.GamesNewsfeedApp

interface TwitchApi {

    @POST("oauth2/token?client_id=${GamesNewsfeedApp.CLIENT_ID}&client_secret=${GamesNewsfeedApp.CLIENT_SECRET}&grant_type=client_credentials")
    suspend fun getAccessToken(): Response<JsonObject>

    companion object {
        val BASE_URL = "https://id.twitch.tv/"
    }
}