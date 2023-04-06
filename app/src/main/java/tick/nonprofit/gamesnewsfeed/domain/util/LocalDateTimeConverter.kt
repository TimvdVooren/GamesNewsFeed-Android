package tick.nonprofit.gamesnewsfeed.domain.util

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

object LocalDateTimeConverter {
    @TypeConverter
    fun toDate(unixTimeStamp: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(unixTimeStamp, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun toUnixTimeStamp(date: LocalDateTime): Long {
        return date.toEpochSecond(ZoneOffset.UTC)
    }
}