package tick.nonprofit.gamesnewsfeed.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Game(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("first_release_date")
    val expectedReleaseDate: Long,
)

class InvalidGameException(message: String): Exception(message)