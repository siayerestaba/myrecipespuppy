package com.iliaberlana.myrecipepuppy

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnitRunner
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipesActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchRecipeActivityTest: AndroidJUnitRunner() {
    private val mockWebServer = MockWebServer()

    @get:Rule
    val activityRule = ActivityTestRule(SearchRecipesActivity::class.java, true, false)

    @Before
    fun setUp() {
        mockWebServer.start()

        val httpUrl = mockWebServer.url("/")

        val testApp = getApplicationContext() as RecipePuppyApp
        testApp.setApiUrl(httpUrl.toString())
    }

    @Test
    fun shows_ordenation_button() {
        activityRule.launchActivity(null)

        Espresso.onView(withId(R.id.favorites))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun dontShowAnyMessage() {
        activityRule.launchActivity(null)

        Espresso.onView(withId(R.id.recipes_texterror))
            .check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(withText(R.string.welcome))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    /*override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
       return super.newApplication(cl, TestApplication::class.java.name, context)
   }*/

}