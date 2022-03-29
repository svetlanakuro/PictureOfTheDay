package com.svetlanakuro.pictureoftheday.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.databinding.ActivityMainBinding
import com.svetlanakuro.pictureoftheday.domain.*
import com.svetlanakuro.pictureoftheday.ui.daily_image.DailyImageFragment
import com.svetlanakuro.pictureoftheday.ui.earth_photo.EarthPhotoFragment
import com.svetlanakuro.pictureoftheday.ui.gst.GstFragment
import com.svetlanakuro.pictureoftheday.ui.favorites.NotesActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val appThemePreference by lazy { AppThemePreferenceDelegate() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val savedTheme = appThemePreference.getSavedTheme(this)
        setTheme(appThemePreference.savedThemeToStyleId(savedTheme))
        setContentView(binding.root)

        if (savedInstanceState == null) {
            openScreen(Screens.DAILY_IMAGE)
        }
        initViewModel()
        initBottomNavigationView()
    }

    private fun initViewModel() {
        viewModel.openScreenLiveData.observe(this) { event ->
            event.getContentIfNotHandled()?.let { screen ->
                openScreen(screen)
            }
        }
    }

    private fun openScreen(screen: Screens) {
        val fragmentToOpen = when (screen) {
            Screens.DAILY_IMAGE -> DailyImageFragment()
            Screens.EARTH_PHOTO -> EarthPhotoFragment()
            Screens.GST_INFO -> GstFragment()
        }
        supportFragmentManager.beginTransaction().replace(binding.mainFragmentContainer.id, fragmentToOpen)
            .commit()
    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            viewModel.bottomNavigationViewItemSelected(menuItem)
            true
        }
        binding.bottomNavigationView.setOnItemReselectedListener { menuItem ->
            viewModel.bottomNavigationViewItemReselected(menuItem)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.notes -> {
                startActivity(Intent(this, NotesActivity::class.java))
                true
            }
            R.id.choose_theme -> {
                supportFragmentManager.commit {
                    replace<SettingsFragment>(R.id.main_fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack("SettingsFragment")
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}