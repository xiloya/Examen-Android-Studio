package com.example.finalexamapp

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PreferencesTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences("TestPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    @Test
    fun saveUserPreferences_isCorrect() {
        // Save a preference
        editor.putString("lastUser", "John Doe").apply()

        // Retrieve the preference
        val savedUser = sharedPreferences.getString("lastUser", null)

        // Assert the value is as expected
        assertEquals("John Doe", savedUser)
    }
}
