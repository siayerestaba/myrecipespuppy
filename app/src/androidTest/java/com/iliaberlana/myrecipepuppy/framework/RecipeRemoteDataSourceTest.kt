package com.iliaberlana.myrecipepuppy.framework

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.iliaberlana.myrecipepuppy.TestApplication
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.framework.remote.NetworkFactory
import com.iliaberlana.myrecipepuppy.framework.remote.RecipeRemoteDataSource
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.collections.shouldNotBeEmpty
import io.kotlintest.shouldBe
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class RecipeRemoteDataSourceTest {
    private val mockWebServer = MockWebServer()
    private lateinit var recipeRemoteDataSource: RecipeRemoteDataSource

    @Before
    fun setUp() {
        mockWebServer.start(8080)

        val httpUrl = mockWebServer.url("/")
        val testApp = getApplicationContext() as TestApplication
        testApp.setApiUrl(httpUrl.toString())

        recipeRemoteDataSource = RecipeRemoteDataSource(NetworkFactory(), httpUrl.toString())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun should_be_empty_if_results_is_empty() = runBlocking {
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

        val actual = recipeRemoteDataSource.searchRecipes("onion,garlic", 1)

        actual.shouldBeEmpty()
    }

    @Test
    fun should_return_throw_when_response_is_not_200ok() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))
        var domainError: DomainError = DomainError.UnknownException

        try {
            recipeRemoteDataSource.searchRecipes("onion,garlic", 1)
        } catch (error: DomainError) {
            domainError = error
        }

        domainError.shouldBe(DomainError.NoInternetConnectionException)

        Unit
    }

    @Test
    fun shouldReturnListWith10Recipes() = runBlocking {
        val response = MockResponse()
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
                        "\t\t\"ingredients\": \"garlic, mussels, onions\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/29318.jpg\"\n" +
                        "\t}, {\n" +
                        "\t\t\"title\": \"Braised Beef and Onions\",\n" +
                        "\t\t\"href\": \"http:\\/\\/www.epicurious.com\\/recipes\\/food\\/views\\/Braised-Beef-and-Onions-232969\",\n" +
                        "\t\t\"ingredients\": \"allspice, garlic, onions\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/103021.jpg\"\n" +
                        "\t}, {\n" +
                        "\t\t\"title\": \"\\nChile Con Queso (Spicy Cheese Dip) Recipe\\n\\n\",\n" +
                        "\t\t\"href\": \"http:\\/\\/cookeatshare.com\\/recipes\\/chile-con-queso-spicy-cheese-dip-2037\",\n" +
                        "\t\t\"ingredients\": \"salsa, onions, garlic\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/823756.jpg\"\n" +
                        "\t}]\n" +
                        "}"
            )

        mockWebServer.enqueue(response)

        val actual = recipeRemoteDataSource.searchRecipes("onion,garlic", 1)

        actual.shouldNotBeEmpty()
        actual.shouldHaveSize(4)
    }
}