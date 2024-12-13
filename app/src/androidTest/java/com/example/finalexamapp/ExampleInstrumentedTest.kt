package com.example.finalexamapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testButtonsAreVisible() {
        onView(withId(R.id.buttonSnackbar))
            .check(matches(isDisplayed()))

        onView(withId(R.id.buttonNotification))
            .check(matches(isDisplayed()))

        onView(withId(R.id.buttonInsertData))
            .check(matches(isDisplayed()))

        onView(withId(R.id.buttonFetchData))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNameEditTextAcceptsInput() {
        onView(withId(R.id.nameEditText))
            .check(matches(isDisplayed()))

        onView(withId(R.id.nameEditText))
            .perform(typeText("Test User"), closeSoftKeyboard())

        onView(withId(R.id.nameEditText))
            .check(matches(withText("Test User")))
    }

    @Test
    fun testSnackbarFunctionality() {
        onView(withId(R.id.buttonSnackbar))
            .perform(click())

        onView(withText("Voici un exemple de Snackbar"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNotificationButtonClick() {
        onView(withId(R.id.buttonNotification))
            .perform(click())

        }
}
