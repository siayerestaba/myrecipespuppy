package com.iliaberlana.myrecipepuppy

import android.app.Application
import com.iliaberlana.myrecipepuppy.domain.data.RecipeRepository
import com.iliaberlana.myrecipepuppy.framework.RecipeRepositoryImpl
import com.iliaberlana.myrecipepuppy.framework.remote.NetworkFactory
import com.iliaberlana.myrecipepuppy.framework.remote.RecipeDataSource
import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class RecipePuppyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@RecipePuppyApp)
            modules(appModule)
        }
    }

    private val appModule = module {
        single { NetworkFactory() }
        single { RecipeDataSource() }
        single<RecipeRepository> { RecipeRepositoryImpl(get()) }
        single { SearchRecipes(get()) }

        /*scope(named<SearchRecipesActivity>()) {
            scoped { SearchRecipesPresenter() }
        }*/
    }
}