package com.svetlanakuro.pictureoftheday

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import com.svetlanakuro.pictureoftheday.ui.*

class MainActivity : AppCompatActivity() {

    private val appThemePreference by lazy { AppThemePreferenceDelegate() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val savedTheme = appThemePreference.getSavedTheme(this)
        setTheme(appThemePreference.savedThemeToStyleId(savedTheme))
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<DailyImageFragment>(R.id.fragment_container)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.choose_theme -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<SettingsFragment>(R.id.fragment_container)
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}