package tick.nonprofit.gamesnewsfeed.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tick.nonprofit.gamesnewsfeed.data.IgdbApi
import tick.nonprofit.gamesnewsfeed.data.TwitchApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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