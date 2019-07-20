package com.iliaberlana.myrecipepuppy

import android.app.Application
import com.iliaberlana.myrecipepuppy.domain.data.RecipeRepository
import com.iliaberlana.myrecipepuppy.framework.RecipeRepositoryImpl
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDatabase
import com.iliaberlana.myrecipepuppy.framework.FavoriteRecipeRepositoryImpl
import com.iliaberlana.myrecipepuppy.framework.remote.NetworkFactory
import com.iliaberlana.myrecipepuppy.framework.remote.RecipeRemoteDataSource
import com.iliaberlana.myrecipepuppy.ui.search.FavoriteRecipesActivity
import com.iliaberlana.myrecipepuppy.ui.search.FavoritesRecipesPresenter
import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class RecipePuppyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RecipePuppyApp)
            modules(appModule)

        }
    }

    private val appModule = module {
        single { NetworkFactory() }
        single { RecipeRemoteDataSource() }
        single<RecipeRepository> { RecipeRepositoryImpl(get()) }
        single { SearchRecipes(get()) }

        factory {
            android.arch.persistence.room.Room.databaseBuilder(
                androidApplication(),
                com.iliaberlana.myrecipepuppy.framework.local.RecipeDatabase::class.java,
                "recipe-db"
            )
                .build()
        }
        factory { get<RecipeDatabase>().recipeDao() }
        single { FavoriteRecipeRepositoryImpl(get()) }

        scope(named<FavoriteRecipesActivity>()) {
            scoped { FavoritesRecipesPresenter(get()) }
        }
    }
}