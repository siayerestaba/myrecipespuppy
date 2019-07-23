package com.iliaberlana.myrecipepuppy.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.detail.RecipeDetailActivity
import org.junit.Rule
import org.junit.Test

class RecipeDetailActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(RecipeDetailActivity::class.java, true, false)

    @Test
    fun showsWebview() {
        activityRule.launchActivity(null)

        onView(withId(R.id.webPage))
            .check(matches(isDisplayed()))
    }
}