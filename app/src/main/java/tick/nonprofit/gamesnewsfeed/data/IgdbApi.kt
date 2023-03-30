package tick.nonprofit.gamesnewsfeed.data

import com.google.gson.JsonArray
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface IgdbApi {

    @GET("games")
    suspend fun fetchSubscribedGames(@HeaderMap headers: Map<String, String>): Response<JsonArray>

    @POST("games")
    suspend fun fetchGameByName(@HeaderMap headers: Map<String, String>, @Body body: RequestBody): Response<JsonArray>

    companion object {
        val BASE_URL = "https://api.igdb.com/v4/"

        fun generateBody(body: String): RequestBody {
            return body.toRequestBody("text/plain".toMediaTypeOrNull())
        }
    }
}