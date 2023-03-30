package tick.nonprofit.gamesnewsfeed.data

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("name")
    val name: String,
    @SerializedName("first_release_date")
    val expectedReleaseDate: Long,
)