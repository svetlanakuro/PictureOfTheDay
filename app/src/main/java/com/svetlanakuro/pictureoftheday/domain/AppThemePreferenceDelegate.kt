package com.svetlanakuro.pictureoftheday.domain

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.svetlanakuro.pictureoftheday.R

private const val NAME_SHARED_PREFERENCE = "APP_THEME"
private const val APP_THEME_KEY = "APP_THEME_KEY"
private const val DEFAULT_THEME = 0
private const val MOON_THEME = 1
private const val SUN_THEME = 2

class AppThemePreferenceDelegate {

    fun savedThemeToStyleId(savedThemeNumber: Int): Int {
        return when (savedThemeNumber) {
            MOON_THEME -> R.style.Theme_PictureOfTheDay_Moon
            SUN_THEME -> R.style.Theme_PictureOfTheDay_Sun
            else -> R.style.Theme_PictureOfTheDay
        }
    }

    fun getSavedTheme(context: Context): Int {
        val preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCE, AppCompatActivity.MODE_PRIVATE)
        return preferences.getInt(APP_THEME_KEY, DEFAULT_THEME)
    }

    fun setMoonTheme(context: Context) {
        setSavedTheme(context, MOON_THEME)
    }

    fun setSunTheme(context: Context) {
        setSavedTheme(context, SUN_THEME)
    }

    private fun setSavedTheme(context: Context, themeNumber: Int) {
        val preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        preferences.edit().putInt(APP_THEME_KEY, themeNumber).apply()
    }
}