package com.iliaberlana.myrecipepuppy

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.iliaberlana.myrecipepuppy.domain.data.FavoriteRecipeRepository
import com.iliaberlana.myrecipepuppy.domain.data.RecipeRepository
import com.iliaberlana.myrecipepuppy.framework.FavoriteRecipeRepositoryImpl
import com.iliaberlana.myrecipepuppy.framework.RecipeRepositoryImpl
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDatabase
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDb
import com.iliaberlana.myrecipepuppy.framework.remote.NetworkFactory
import com.iliaberlana.myrecipepuppy.framework.remote.RecipeRemoteDataSource
import com.iliaberlana.myrecipepuppy.ui.favorites.FavoriteRecipesActivity
import com.iliaberlana.myrecipepuppy.ui.favorites.FavoritesRecipesPresenter
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipesActivity
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipesPresenter
import com.iliaberlana.myrecipepuppy.usecases.SaveFavorite
import com.iliaberlana.myrecipepuppy.usecases.SearchRecipes
import com.iliaberlana.myrecipepuppy.usecases.ShowFavoriteRecipes
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

open class RecipePuppyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        RecipeDb.initializeDb(this)

        startKoin {
            androidLogger()
            androidContext(this@RecipePuppyApp)
            modules(appModule)

        }
    }

    private var apiUrlApp: String = "http://www.recipepuppy.com"
    open fun getApiUrl(): String = apiUrlApp
    open fun setApiUrl(apiUrl: String) {
        this.apiUrlApp = apiUrl
    }

    private val appModule = module {
        single {
            Room.databaseBuilder(androidApplication(), RecipeDatabase::class.java, "recipe-db").build()
        }
        single { NetworkFactory() }
        single { RecipeRemoteDataSource(get(), getApiUrl()) }

        single<RecipeRepository> { RecipeRepositoryImpl(get()) }
        single<FavoriteRecipeRepository> { FavoriteRecipeRepositoryImpl(get<RecipeDatabase>().recipeDao()) }

        single { SearchRecipes(get()) }
        single { ShowFavoriteRecipes(get()) }
        single { SaveFavorite(get()) }

        scope(named<SearchRecipesActivity>()) {
            scoped { SearchRecipesPresenter(get(), get()) }
        }

        scope(named<FavoriteRecipesActivity>()) {
            scoped { FavoritesRecipesPresenter(get()) }
        }
    }
}