package com.iliaberlana.myrecipepuppy.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.TestApplication
import com.iliaberlana.myrecipepuppy.ui.detail.RecipeDetailActivity
import com.iliaberlana.myrecipepuppy.ui.favorites.FavoriteRecipesActivity
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipeViewHolder
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipesActivity
import com.iliaberlana.myrecipepuppy.util.RecyclerViewMatcher
import com.iliaberlana.myrecipepuppy.util.clickChildViewWithId
import com.iliaberlana.myrecipepuppy.util.waitUntilActivityVisible
import io.kotlintest.shouldBe
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.not
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

        onView(withId(R.id.favorites))
            .check(matches(isDisplayed()))
    }

    @Test
    fun showsWelcomeMessage() {
        activityRule.launchActivity(null)

        onView(withId(R.id.recipes_texterror))
            .check(matches(isDisplayed()))

        onView(withText(R.string.welcome))
            .check(matches(isDisplayed()))
    }

    @Test
    fun showsSearchButton() {
        activityRule.launchActivity(null)

        onView(withId(R.id.search))
            .check(matches(isDisplayed()))
    }

    @Test
    fun showsHasLactoseIfAnIngredientIsMilkOrCheese() {
        mockWebServer.enqueue(happypath())

        activityRule.launchActivity(null)

        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("eggs"), pressImeActionButton())

        Thread.sleep(2000)

        onView(
            RecyclerViewMatcher(R.id.recipes_recyclerview)
                .atPositionOnView(0, R.id.recipe_haslactose)
        )
            .check(matches(not(isDisplayed())))

        onView(
            RecyclerViewMatcher(R.id.recipes_recyclerview)
                .atPositionOnView(1, R.id.recipe_haslactose)
        )
            .check(matches(isDisplayed()))
    }

    @Test
    fun dontNeedPressImeActionButtonForDoTheSearch() {
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

        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("tree"))

        Thread.sleep(1000)

        onView(withId(R.id.recipes_texterror))
            .check(matches(isDisplayed()))

        onView(withText(R.string.emptyList))
            .check(matches(isDisplayed()))
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

        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("tree"), pressImeActionButton())

        Thread.sleep(1000)

        onView(withId(R.id.recipes_texterror))
            .check(matches(isDisplayed()))

        onView(withText(R.string.emptyList))
            .check(matches(isDisplayed()))
    }

    @Test
    fun showsListWhenSearchAnIngredient() {
        mockWebServer.enqueue(happypath())

        activityRule.launchActivity(null)

        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("eggs"))

        Thread.sleep(1000)

        onView(withId(R.id.recipes_recyclerview)).check(matches(isDisplayed()))

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.recipes_recyclerview)
        val itemCount = recyclerView.adapter?.itemCount
        itemCount.shouldBe(4)
    }

    @Test
    fun shouldCreateFavoriteIfClickInTheButtonAndHiddenButton() {
        mockWebServer.enqueue(happypath())

        activityRule.launchActivity(null)

        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("eggs"))

        Thread.sleep(1000)

        onView(withId(R.id.recipes_recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<SearchRecipeViewHolder>
                    (0, clickChildViewWithId(R.id.recipe_addfavorite))
            )


        onView(RecyclerViewMatcher(R.id.recipes_recyclerview).atPositionOnView(0,
            R.id.recipe_addfavorite
        ))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldOpenDetailActivityWhenClickAItem() {
        mockWebServer.enqueue(happypath())

        activityRule.launchActivity(null)

        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("eggs"))

        Thread.sleep(1000)

        onView(withId(R.id.recipes_recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<SearchRecipeViewHolder>(0, click()))

        waitUntilActivityVisible<RecipeDetailActivity>()
        onView(withId(R.id.detail_frame)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldOpenFavoriteRecipesActivityWhenClickActionMenuIcon() {
        mockWebServer.enqueue(happypath())

        activityRule.launchActivity(null)

        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("eggs"))

        Thread.sleep(1000)
        onView(withId(R.id.recipes_recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<SearchRecipeViewHolder>
                    (0, clickChildViewWithId(R.id.recipe_addfavorite))
            )

        onView(withId(R.id.favorites))
            .check(matches(isDisplayed()))
            .perform(click())

        waitUntilActivityVisible<FavoriteRecipesActivity>()
        onView(withId(R.id.recipes_recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldCallNextPageWhenScrollInBottom() { // TODO NO VA!
        mockWebServer.enqueue(happypath())

        activityRule.launchActivity(null)

        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("eggs"))

        Thread.sleep(1000)

        onView(withId(R.id.recipes_recyclerview)).check(matches(isDisplayed()))

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.recipes_recyclerview)
        val itemCount = recyclerView.adapter?.itemCount
        itemCount.shouldBe(4)

        recyclerView.layoutManager?.smoothScrollToPosition(recyclerView, null, itemCount!!.minus(1));
        onView(withId(R.id.recipes_recyclerview))
            .perform(RecyclerViewActions.scrollToPosition<SearchRecipeViewHolder>(itemCount!!.minus(1)))

        mockWebServer.enqueue(happypath())
        Thread.sleep(3000)

        val newItemCount = recyclerView.adapter?.itemCount
        newItemCount.shouldBe(8)
    }

    @Test
    fun showsNoInternetConectionErrorWhenSearchHadAnError() {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        activityRule.launchActivity(null)

        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("eggs"), pressImeActionButton())

        Thread.sleep(1000)

        onView(withId(R.id.recipes_texterror))
            .check(matches(isDisplayed()))

        onView(withText(R.string.noInternetConectionError))
            .check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun happypath(): MockResponse = MockResponse()
        .addHeader("Content-Type", "application/json; charset=utf-8")
        .addHeader("Cache-Control", "no-cache")
        .setBody(
            "{\n" +
                    "\t\"title\": \"Recipe Puppy\",\n" +
                    "\t\"version\": 0.1,\n" +
                    "\t\"href\": \"http:\\/\\/www.recipepuppy.com\\/\",\n" +
                    "\t\"results\": [{\n" +
                    "\t\t\"title\": \"Roasted Garlic Grilling Sauce \\r\\n\\t\\t\\r\\n\\t\\r\\n\\t\\t\\r\\n\\t\\r\\n\\t\\t\\r\\n\\t\\r\\n\\t\\r\\n\\r\\n\",\n" +
                    "\t\t\"href\": \"http:\\/\\/www.kraftfoods.com\\/kf\\/recipes\\/roasted-garlic-grilling-sauce-56344.aspx\",\n" +
                    "\t\t\"ingredients\": \"garlic, onions, hot sauce\",\n" +
                    "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/634118.jpg\"\n" +
                    "\t}, {\n" +
                    "\t\t\"title\": \"Steamed Mussels I\",\n" +
                    "\t\t\"href\": \"http:\\/\\/allrecipes.com\\/Recipe\\/Steamed-Mussels-I\\/Detail.aspx\",\n" +
                    "\t\t\"ingredients\": \"garlic, milk, mussels, onions\",\n" +
                    "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/29318.jpg\"\n" +
                    "\t}, {\n" +
                    "\t\t\"title\": \"Braised Beef and Onions\",\n" +
                    "\t\t\"href\": \"http:\\/\\/www.epicurious.com\\/recipes\\/food\\/views\\/Braised-Beef-and-Onions-232969\",\n" +
                    "\t\t\"ingredients\": \"allspice, garlic, onions\",\n" +
                    "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/103021.jpg\"\n" +
                    "\t}, {\n" +
                    "\t\t\"title\": \"\\nChile Con Queso (Spicy Cheese Dip) Recipe\\n\\n\",\n" +
                    "\t\t\"href\": \"http:\\/\\/cookeatshare.com\\/recipes\\/chile-con-queso-spicy-cheese-dip-2037\",\n" +
                    "\t\t\"ingredients\": \"salsa, cheese, onions, garlic\",\n" +
                    "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/823756.jpg\"\n" +
                    "\t}]\n" +
                    "}"
        )
}