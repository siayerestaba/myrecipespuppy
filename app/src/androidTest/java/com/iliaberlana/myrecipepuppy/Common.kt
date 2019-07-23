package com.iliaberlana.myrecipepuppy

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import org.hamcrest.Matcher


inline fun <reified T : Activity> waitUntilActivityVisible() {
    val startTime = System.currentTimeMillis()
    while (!isVisible<T>()) {
        Thread.sleep(CONDITION_CHECK_INTERVAL)
        if (System.currentTimeMillis() - startTime >= TIMEOUT) {
            throw AssertionError("Activity ${T::class.java.simpleName} not visible after $TIMEOUT milliseconds")
        }
    }
}

inline fun <reified T : Activity> isVisible() : Boolean {
    val am = ApplicationProvider.getApplicationContext<Context>().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val visibleActivityName = am.appTasks[0].taskInfo.topActivity.className
    println("ACTIVITY: $visibleActivityName")
    return visibleActivityName == T::class.java.name
}

fun clickChildViewWithId(id: Int): ViewAction = object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Click on a child view with specified id."
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById<View>(id)
            v.performClick()
        }
    }

fun withItemContent(expectedText: String): Matcher<Any> {
    return withItemContent(expectedText)
}

const val TIMEOUT = 5000L
const val CONDITION_CHECK_INTERVAL = 100L
