package com.example.finalexamapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testUserDataFlow() {
        val testName = "Test User"
        onView(withId(R.id.nameEditText))
            .perform(typeText(testName), closeSoftKeyboard())
        onView(withId(R.id.nameEditText))
            .check(matches(withText(testName)))

        onView(withId(R.id.buttonInsertData))
            .perform(click())

        onView(withId(R.id.buttonFetchData))
            .perform(click())

        Thread.sleep(2000)
        onView(withId(R.id.recyclerView))
            .check(matches(hasDescendant(withText(testName))))
    }
}
