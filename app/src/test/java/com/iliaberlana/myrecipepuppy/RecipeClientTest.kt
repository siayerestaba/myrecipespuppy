package com.iliaberlana.myrecipepuppy

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.iliaberlana.myrecipepuppy.framework.remote.RecipeClient
import com.iliaberlana.myrecipepuppy.framework.remote.RecipeClientService
import org.junit.Before
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import io.kotlintest.matchers.collections.shouldBeEmpty
import kotlinx.coroutines.runBlocking


class RecipeClientTest {
    private val mockWebServer = MockWebServer()
    private var recipeClient: RecipeClient? = null
    private val recipeClientService = RecipeClientService()

    @Before
    fun setUp() {
        mockWebServer.start()

        val httpUrl = mockWebServer.url("/")
        recipeClient = recipeClientService.createApi(httpUrl.toString())
    }

    @Test
    fun `should be empty if results is empty`() = runBlocking {
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

        val actual = recipeClient!!.searchRecipes("onion,garlic", 1)

        actual.results.shouldBeEmpty()
    }

    @Test
    fun `should have title, version, href and results`() = runBlocking {
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
                        "\t\t\"title\": \"Dashi Basic Korean Kelp Stock Recipe\",\n" +
                        "\t\t\"href\": \"http:\\/\\/www.grouprecipes.com\\/508\\/dashi-basic-korean-kelp-stock.html\",\n" +
                        "\t\t\"ingredients\": \"anchovies, garlic, onions\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/351817.jpg\"\n" +
                        "\t}, {\n" +
                        "\t\t\"title\": \"Oven-Saut&#233;ed Onions and Garlic\",\n" +
                        "\t\t\"href\": \"http:\\/\\/find.myrecipes.com\\/recipes\\/recipefinder.dyn?action=displayRecipe&recipe_id=1860015\",\n" +
                        "\t\t\"ingredients\": \"onions, garlic, olive oil\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/527595.jpg\"\n" +
                        "\t}, {\n" +
                        "\t\t\"title\": \"Three-in-One Meals Meaty Tomato Sauce\",\n" +
                        "\t\t\"href\": \"http:\\/\\/find.myrecipes.com\\/recipes\\/recipefinder.dyn?action=displayRecipe&recipe_id=1857779\",\n" +
                        "\t\t\"ingredients\": \"ground beef, onions, garlic\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/555389.jpg\"\n" +
                        "\t}, {\n" +
                        "\t\t\"title\": \"\\nPerfect Roast Chicken Recipe\\n\\n\",\n" +
                        "\t\t\"href\": \"http:\\/\\/cookeatshare.com\\/recipes\\/perfect-roast-chicken-41630\",\n" +
                        "\t\t\"ingredients\": \"onions, garlic, salt\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/806946.jpg\"\n" +
                        "\t}, {\n" +
                        "\t\t\"title\": \"\\nMexican Rice Recipe\\n\\n\",\n" +
                        "\t\t\"href\": \"http:\\/\\/cookeatshare.com\\/recipes\\/mexican-rice-47559\",\n" +
                        "\t\t\"ingredients\": \"onions, garlic, crisco\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/816298.jpg\"\n" +
                        "\t}, {\n" +
                        "\t\t\"title\": \"\\nCreamed Zucchini Recipe\\n\\n\",\n" +
                        "\t\t\"href\": \"http:\\/\\/cookeatshare.com\\/recipes\\/creamed-zucchini-60366\",\n" +
                        "\t\t\"ingredients\": \"zucchini, onions, garlic\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/821781.jpg\"\n" +
                        "\t}, {\n" +
                        "\t\t\"title\": \"\\nChile Con Queso (Spicy Cheese Dip) Recipe\\n\\n\",\n" +
                        "\t\t\"href\": \"http:\\/\\/cookeatshare.com\\/recipes\\/chile-con-queso-spicy-cheese-dip-2037\",\n" +
                        "\t\t\"ingredients\": \"salsa, onions, garlic\",\n" +
                        "\t\t\"thumbnail\": \"http:\\/\\/img.recipepuppy.com\\/823756.jpg\"\n" +
                        "\t}]\n" +
                        "}"
            )

        mockWebServer.enqueue(response)

        val actual = recipeClient!!.searchRecipes("onion,garlic", 1)

        assertThat(actual.title).isEqualTo("Recipe Puppy")
        assertThat(actual.version).isEqualTo("0.1")
        assertThat(actual.href).isEqualTo("http://www.recipepuppy.com/")
        assert(actual.results.size == 10)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

}