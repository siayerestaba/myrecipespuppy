package com.iliaberlana.myrecipepuppy

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipesActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
class SearchRecipeActivityTest {
    private val mockWebServer = MockWebServer()

    @get:Rule
    val activityRule = ActivityTestRule(SearchRecipesActivity::class.java, true, false)

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        val httpUrl = mockWebServer.url("/")

        val testApp = getApplicationContext() as TestApplication
        testApp.setApiUrl(httpUrl.toString())
    }

    @Test
    fun showsFavoriteButton() {
        activityRule.launchActivity(null)

        Espresso.onView(withId(R.id.favorites))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun showsWelcomeMessage() {
        activityRule.launchActivity(null)

        Espresso.onView(withId(R.id.recipes_texterror))
            .check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(withText(R.string.welcome))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun showsSearchButton() {
        activityRule.launchActivity(null)

        Espresso.onView(withId(R.id.search))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun showsNoRecipeErrorWhenSearchSomeThatIsNotAnIngredient() {
        val response = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody(
                "{\n" +
                        "\t\"title\": \"Recipe Puppy\",\n" +
                        "\t\"version\": 0.1,\n" +
                        "\t\"href\": \"http:\\/\\/www.recipepuppy.com\\/\",\n" +
                        "\t\"results\": []\n" +
                        "}"
            )

        mockWebServer.enqueue(response)

        activityRule.launchActivity(null)

        Espresso.onView(withId(R.id.search)).perform(click())
        Espresso.onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("tree"), pressImeActionButton())

        Thread.sleep(1000)

        Espresso.onView(withId(R.id.recipes_texterror))
            .check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(withText(R.string.emptyList))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun showsNoInternetConectionErrorWhenSearchHadAnError() {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        activityRule.launchActivity(null)

        Espresso.onView(withId(R.id.search)).perform(click())
        Espresso.onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("eggs"), pressImeActionButton())

        Thread.sleep(1000)

        Espresso.onView(withId(R.id.recipes_texterror))
            .check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(withText(R.string.noInternetConectionError))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}