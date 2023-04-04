package tick.nonprofit.gamesnewsfeed.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tick.nonprofit.gamesnewsfeed.data.data_source.GameDatabase
import tick.nonprofit.gamesnewsfeed.data.data_source.IgdbApi
import tick.nonprofit.gamesnewsfeed.data.data_source.TwitchApi
import tick.nonprofit.gamesnewsfeed.data.repository.GameRepositoryImpl
import tick.nonprofit.gamesnewsfeed.domain.repository.GameRepository
import tick.nonprofit.gamesnewsfeed.domain.use_case.AddGame
import tick.nonprofit.gamesnewsfeed.domain.use_case.DeleteGame
import tick.nonprofit.gamesnewsfeed.domain.use_case.GameUseCases
import tick.nonprofit.gamesnewsfeed.domain.use_case.GetGames
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGameDatabase(app: Application): GameDatabase {
        return Room.databaseBuilder(
            app,
            GameDatabase::class.java,
            GameDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideGameRepository(db: GameDatabase): GameRepository {
        return GameRepositoryImpl(db.gameDao)
    }

    @Provides
    @Singleton
    fun provideGameUseCases(repository: GameRepository): GameUseCases {
        return GameUseCases(
            GetGames(repository),
            DeleteGame(repository),
            AddGame(repository)
        )
    }

    @Provides
    @Singleton
    fun provideTwitchApi(): TwitchApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TwitchApi.BASE_URL)
            .build()
            .create(TwitchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideIgdbApi(): IgdbApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(IgdbApi.BASE_URL)
            .build()
            .create(IgdbApi::class.java)
    }
}